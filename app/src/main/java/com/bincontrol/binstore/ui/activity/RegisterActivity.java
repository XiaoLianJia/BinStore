package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.util.Base64Utils;

public class RegisterActivity extends BaseActivity {

    private EditText mEditTextAccount;
    private EditText mEditTextPassword;
    private EditText mEditTextSecurityCode;
    private Button mButtonRegister;
    private Button mButtonGetSecurityCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        RelativeLayout relativeLayout = findViewById(R.id.relative_layout_security_code);
        relativeLayout.setVisibility(View.VISIBLE);

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
                checkRegisterParams();
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
                checkRegisterParams();
            }
        });

        mEditTextSecurityCode = findViewById(R.id.edit_text_security_code);
        mEditTextSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterParams();
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

        mButtonRegister = findViewById(R.id.button_login);
        mButtonRegister.setText(R.string.register);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = mEditTextAccount.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                String securityCode = mEditTextSecurityCode.getText().toString().trim();

                password = Base64Utils.encryptBASE64(password);
                register(account, password, securityCode);
            }
        });

        mButtonGetSecurityCode = findViewById(R.id.button_get_security_code);
        mButtonGetSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSecurityCode();
            }
        });

    }


    private void checkRegisterParams() {

        String account = mEditTextAccount.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        String securityCode = mEditTextSecurityCode.getText().toString().trim();

        if (account.length() == 11 && password.length() >= 6 && password.length() <= 32 && securityCode.length() == 6) {
            mButtonRegister.setEnabled(true);
        } else {
            mButtonRegister.setEnabled(false);
        }
    }


    private void getSecurityCode() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mButtonGetSecurityCode.setEnabled(false);
                mButtonGetSecurityCode.setText(R.string.refresh_security_code);



                mButtonGetSecurityCode.setEnabled(true);
            }
        }).start();
    }


    private void register(String account, String password, String securityCode) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                mButtonRegister.setEnabled(false);


                mButtonRegister.setEnabled(true);
            }
        }).start();
    }

}
