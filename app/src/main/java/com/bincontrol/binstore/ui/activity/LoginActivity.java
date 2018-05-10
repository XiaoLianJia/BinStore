package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.util.Base64Utils;
import com.bincontrol.binstore.util.SharedPreferencesUtils;

import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_ACCOUNT;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_PASSWORD;

public class LoginActivity extends BaseActivity {

    private EditText mEditTextAccount;
    private EditText mEditTextPassword;
    private Button mButtonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        initEditView();
        initImageView();
        initButton();
    }


    private void initEditView() {

        mEditTextAccount = findViewById(R.id.edit_text_account);
        mEditTextAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginParams();
            }
        });

        mEditTextPassword = findViewById(R.id.edit_text_password);
        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginParams();
            }
        });
    }


    private void initImageView() {

        ImageView imageViewPeek = findViewById(R.id.image_view_peek);
        imageViewPeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    v.setSelected(false);
                    mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    v.setSelected(true);
                    mEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
    }


    private void initButton() {

        mButtonLogin = findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = mEditTextAccount.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();

                if (!account.isEmpty() && !account.equals(getString(R.string.edit_hint_account))) {
                    SharedPreferencesUtils.putString(LoginActivity.this, SHARE_PREFERENCE_PARAM_USER_ACCOUNT, account);
                }
                if (!password.isEmpty() && !password.equals(getString(R.string.edit_hint_password))) {
                    password = Base64Utils.encryptBASE64(password);
                    SharedPreferencesUtils.putString(LoginActivity.this, SHARE_PREFERENCE_PARAM_USER_PASSWORD, password);
                }

                login(account, password);
            }
        });
    }


    private void checkLoginParams() {

        String account = mEditTextAccount.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        if (account.length() == 11 && password.length() >= 6 && password.length() <= 32) {
            mButtonLogin.setEnabled(true);
        } else {
            mButtonLogin.setEnabled(false);
        }
    }


    private void login(String account, String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                mButtonLogin.setEnabled(false);

                mButtonLogin.setEnabled(true);
            }
        }).start();
    }

}
