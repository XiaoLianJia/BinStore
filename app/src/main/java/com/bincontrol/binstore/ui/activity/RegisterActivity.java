package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstore.R;
import com.bincontrol.binstore.common.ServerErrorCode;
import com.bincontrol.binstore.util.Base64Utils;
import com.bincontrol.binstore.util.HttpUtils;
import com.bincontrol.binstore.util.SharedPreferencesUtils;

import static com.bincontrol.binstore.common.AppConstant.REQUEST_PARAM_ACCOUNT;
import static com.bincontrol.binstore.common.AppConstant.REQUEST_PARAM_PASSWORD;
import static com.bincontrol.binstore.common.AppConstant.SERVER_URL_USER_REGISTER;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_ACCOUNT;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_PASSWORD;

public class RegisterActivity extends UserActivity {

    private static String TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relativeLayout = findViewById(R.id.relative_layout_security_code);
        relativeLayout.setVisibility(View.VISIBLE);
        initTitleBar(getString(R.string.register));
        mButtonRegisterLogin.setText(R.string.register);
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
    }


    /**
     * 注册
     * @param account account
     * @param password password
     */
    @Override
    protected void register(final String account, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(REQUEST_PARAM_ACCOUNT, account);
                jsonObject.put(REQUEST_PARAM_PASSWORD, Base64Utils.encryptBASE64(password));
                JSONObject jsonReturn = HttpUtils.post(SERVER_URL_USER_REGISTER, jsonObject);

                if (jsonReturn != null && jsonReturn.getInteger("status") == ServerErrorCode.BIN_OK.getCode()) {
                    Log.d(TAG, "注册成功, [" + account + "]");

                    SharedPreferencesUtils.putString(RegisterActivity.this, SHARE_PREFERENCE_PARAM_USER_ACCOUNT, account);
                    SharedPreferencesUtils.putString(RegisterActivity.this, SHARE_PREFERENCE_PARAM_USER_PASSWORD, Base64Utils.encryptBASE64(password));
                    finish();

                } else {
                    Log.d(TAG, "注册失败, [" + account + "]");
                    Log.d(TAG, jsonReturn == null ? "ERROR: EMPTY RETURN" : jsonReturn.getString("msg"));

                }
            }
        }).start();
    }

}
