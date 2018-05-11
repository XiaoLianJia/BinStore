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

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
                //mButtonGetSecurityCode.setEnabled(false);
                //mButtonGetSecurityCode.setText(R.string.refresh_security_code);

                try {

                    //设置超时时间-可自行调整
                    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                    System.setProperty("sun.net.client.defaultReadTimeout", "10000");

                    //初始化ascClient需要的几个参数
                    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
                    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
                    //替换成你的AK
                    final String accessKeyId = "LTAIjC7ModC5dM8Z";//你的accessKeyId,参考本文档步骤2
                    final String accessKeySecret = "gUBx6qhUx09FgxnEFbUYFA5ET48Z6h";//你的accessKeySecret，参考本文档步骤2

                    //初始化ascClient,暂时不支持多region（请勿修改）
                    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                            accessKeySecret);
                    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
                    IAcsClient acsClient = new DefaultAcsClient(profile);

                    //组装请求对象
                    SendSmsRequest request = new SendSmsRequest();
                    //使用post提交
                    request.setMethod(MethodType.POST);
                    //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
                    request.setPhoneNumbers("15606905778");
                    //必填:短信签名-可在短信控制台中找到
                    request.setSignName("BinStore");
                    //必填:短信模板-可在短信控制台中找到
                    request.setTemplateCode("SMS_134805017");
                    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
                    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
                    request.setTemplateParam("{\"code\":\"123\"}");
                    //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
                    //request.setSmsUpExtendCode("90997");

                    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
                    request.setOutId("yourOutId");

                    //请求失败这里会抛ClientException异常
                    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
                    String code = sendSmsResponse.getCode();
                    if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                        //请求成功
                        System.out.print("OK");
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                //mButtonGetSecurityCode.setEnabled(true);
            }
        }).start();
    }


    private void register(String account, String password, String securityCode) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //mButtonRegister.setEnabled(false);


                //mButtonRegister.setEnabled(true);
            }
        }).start();
    }

}
