package com.bincontrol.binstore.common;

public class AppConstant {

    // SharePreference存储路径
    public static final String SHARE_PREFERENCE_PATH = "BinStore";
    // SharePreference参数：是否初次启动软件
    public static final String SHARE_PREFERENCE_PARAM_FIRST_TIME_RUN = "FirstTimeRun";
    // SharePreference参数：用户帐号
    public static final String SHARE_PREFERENCE_PARAM_USER_ACCOUNT = "UserAccount";
    // SharePreference参数：用户密码
    public static final String SHARE_PREFERENCE_PARAM_USER_PASSWORD = "UserPassword";
    // SharePreference参数：用户专属推广位
    public static final String SHARE_PREFERENCE_PARAM_USER_ADZONEID = "UserAdZoneId";


    // 启动延时
    public static final int SPLASH_DISPLAY = 2000;

    // 界面刷新延时
    public static final int REFRESH_DELAY = 2000;

    // 图片展播间隔时间
    public static final int BANNER_DELAY = 3000;

    // 服务器地址
    public static String SERVER_URL_USER_REGISTER = "http://10.0.2.2:8080/user/register";
    public static String SERVER_URL_USER_LOGIN = "http://10.0.2.2:8080/user/login";
    public static String SERVER_URL_COMMODITY_CATEGORY = "http://10.0.2.2:8080/commodity/category";

    // 请求参数
    public static String REQUEST_PARAM_ACCOUNT = "account";
    public static String REQUEST_PARAM_PASSWORD = "password";
    public static String REQUEST_PARAM_ADZONENAME = "adzonename";
    public static String REQUEST_PARAM_ADZONEID = "adzoneid";
    public static String REQUEST_PARAM_INVITECODE = "invitecode";
    public static String REQUEST_PARAM_CATEGORY = "category";

    // 淘宝客客户端SDK参数
    public static String ALI_CLIENT_APP_KEY = "24857356";
    public static String ALI_CLIENT_PID = "mm_128206999_44364607_";
    public static String ALI_CLIENT_SUB_PID = "mm_128206999_44364607_";
    public static String ALI_CLIENT_ADZONE_ID = "453770120";

    // 阿里云短信服务SDK参数
    public static String ALI_SERVER_ACCESS_KEY_ID = "";//LTAIjC7ModC5dM8Z
    public static String ALI_SERVER_ACCESS_KEY_SECRET = "";//gUBx6qhUx09FgxnEFbUYFA5ET48Z6h
    public static String ALI_SERVER_SMS_SIGN = "BinStore";
    public static String ALI_SERVER_SMS_TEMPLATE_CODE = "SMS_134805017";
}