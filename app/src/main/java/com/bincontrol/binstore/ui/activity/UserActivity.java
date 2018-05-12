package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bincontrol.binstore.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class UserActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = UserActivity.class.getName();

    private static final int EDIT_TYPE_ACCOUNT = 1;
    private static final int EDIT_TYPE_PASSWORD = 2;
    private static final int EDIT_TYPE_SECURITY_CODE = 3;

    private EditText mEditTextAccount;
    private EditText mEditTextPassword;
    private EditText mEditTextSecurityCode;
    private Button mButtonGetSecurityCode;
    protected Button mButtonRegisterLogin;

    private boolean mIsValidAccount = false;
    private boolean mIsValidPassword = false;
    private boolean mIsValidSecurityCode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mEditTextAccount = findViewById(R.id.edit_text_account);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mEditTextSecurityCode = findViewById(R.id.edit_text_security_code);

        mButtonRegisterLogin = findViewById(R.id.button_register_login);
        mButtonRegisterLogin.setOnClickListener(this);

        mButtonGetSecurityCode = findViewById(R.id.button_get_security_code);
        mButtonGetSecurityCode.setOnClickListener(this);

        ImageView imageViewPeek = findViewById(R.id.image_view_peek);
        imageViewPeek.setOnClickListener(this);

        setTextWatcher();
    }


    /**
     * 设置文本输入框监听
     */
    private void setTextWatcher() {

        mEditTextAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                onParamChange(EDIT_TYPE_ACCOUNT);
            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                onParamChange(EDIT_TYPE_PASSWORD);
            }
        });

        mEditTextSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                onParamChange(EDIT_TYPE_SECURITY_CODE);
            }
        });
    }


    /**
     * 响应文本输入框参数变化
     * @param editType edit type
     */
    private void onParamChange(int editType) {

        switch (editType) {
            case EDIT_TYPE_ACCOUNT:
                String account = mEditTextAccount.getText().toString().trim();
                mIsValidAccount = account.length() == 11;
                mButtonGetSecurityCode.setEnabled(account.length() == 11);
                break;
            case EDIT_TYPE_PASSWORD:
                String password = mEditTextPassword.getText().toString().trim();
                mIsValidPassword = password.length() >= 6 && password.length() <= 32;
                break;
            case EDIT_TYPE_SECURITY_CODE:
                String securityCode = mEditTextSecurityCode.getText().toString().trim();
                mIsValidSecurityCode = !securityCode.isEmpty();
                break;
        }

        if (mButtonRegisterLogin.getText().toString().trim().equals(getString(R.string.register))) {
            mButtonRegisterLogin.setEnabled(mIsValidAccount && mIsValidPassword && mIsValidSecurityCode);
        } else {
            mButtonRegisterLogin.setEnabled(mIsValidAccount && mIsValidPassword);
        }
    }


    /**
     * 点击事件
     * @param v v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_view_peek:
                if (v.isSelected()) {
                    v.setSelected(false);
                    mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    v.setSelected(true);
                    mEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.button_get_security_code:
                sendCode(mEditTextAccount.getText().toString().trim());
                break;
            case R.id.button_register_login:
                String buttonText = mButtonRegisterLogin.getText().toString().trim();
                if (buttonText.equals(getString(R.string.register))) {
                    submitCode(mEditTextAccount.getText().toString().trim(), mEditTextSecurityCode.getText().toString().trim());
                } else {
                    login();
                }
                break;
        }
    }


    /**
     * 注册
     */
    protected void register(String account, String password) {}

    private void register() {
        register(mEditTextAccount.getText().toString().trim(), mEditTextPassword.getText().toString().trim());
    }


    /**
     * 登录
     */
    protected void login(String account, String password) {}

    private void login() {
        login(mEditTextAccount.getText().toString().trim(), mEditTextPassword.getText().toString().trim());
    }


    /**
     * 发送验证码
     * @param phoneNumber phone number
     */
    private void sendCode(final String phoneNumber) {

        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.d(TAG, "验证码发送成功，[" + phoneNumber + "]");

                } else{
                    Log.d(TAG, "验证码发送失败，[" + phoneNumber + "]");
                }
            }
        });

        SMSSDK.getVerificationCode("86", phoneNumber);
    }


    /**
     * 提交验证码
     * @param phoneNumber phone number
     * @param code code
     */
    private void submitCode(final String phoneNumber, String code) {

        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.d(TAG, "验证码校验成功，[" + phoneNumber + "]");
                    register();

                } else{
                    Log.d(TAG, "验证码校验失败，[" + phoneNumber + "]");
                }
            }
        });

        SMSSDK.submitVerificationCode("86", phoneNumber, code);
    }


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
