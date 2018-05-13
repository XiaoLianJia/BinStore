package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstore.R;
import com.bincontrol.binstore.common.ServerErrorCode;
import com.bincontrol.binstore.util.Base64Utils;
import com.bincontrol.binstore.util.HttpUtils;
import com.bincontrol.binstore.util.SharedPreferencesUtils;

import static com.bincontrol.binstore.common.AppConstant.SERVER_URL_USER_LOGIN;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_ACCOUNT;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_PASSWORD;

public class LoginActivity extends UserActivity {

    private static String TAG = LoginActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.login));
        mButtonRegisterLogin.setText(R.string.login);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String account = SharedPreferencesUtils.getString(LoginActivity.this, SHARE_PREFERENCE_PARAM_USER_ACCOUNT, "");
        mEditTextAccount.setText(account);
    }


    /**
     * 初始化标题栏
     * @param title title
     */
    @Override
    protected void initTitleBar(String title) {
        mCustomTitleBar.setTitle(title);
        mCustomTitleBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCustomTitleBar.addRightTextButton(getString(R.string.register), R.id.title_bar_text_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(RegisterActivity.class);
            }
        });
    }


    /**
     * 登录
     * @param account account
     * @param password password
     */
    @Override
    protected void login(final String account, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("account", account);
                jsonObject.put("password", Base64Utils.encryptBASE64(password));
                JSONObject jsonReturn = HttpUtils.post(SERVER_URL_USER_LOGIN, jsonObject);

                if (jsonReturn != null && jsonReturn.getInteger("status") == ServerErrorCode.BIN_OK.getCode()) {
                    Log.d(TAG, "登录成功, [" + account + "]");
                    SharedPreferencesUtils.putString(LoginActivity.this, SHARE_PREFERENCE_PARAM_USER_ACCOUNT, account);
                    SharedPreferencesUtils.putString(LoginActivity.this, SHARE_PREFERENCE_PARAM_USER_PASSWORD, Base64Utils.encryptBASE64(password));
                    finish();

                } else {
                    Log.d(TAG, "登录失败, [" + account + "]");
                    Log.d(TAG, jsonReturn == null ? "ERROR: EMPTY RETURN" : jsonReturn.getString("msg"));

                }
            }
        }).start();
    }

}
