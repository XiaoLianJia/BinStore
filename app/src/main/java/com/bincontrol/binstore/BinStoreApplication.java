package com.bincontrol.binstore;

import android.app.Application;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.AlibcTradeBiz;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.common.AlibcTradeCommon;
import com.bincontrol.binstore.util.SharedPreferencesUtils;
import com.mob.MobSDK;

import java.util.HashMap;

import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_ADZONE_ID;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_APP_KEY;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_PID;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_SUB_PID;
import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_USER_ADZONEID;


public class BinStoreApplication extends Application {

    private static String TAG = BinStoreApplication.class.getName();

    public static BinStoreApplication application = null;
    public static AlibcTaokeParams alibcTaokeParams = null;
    public static AlibcShowParams alibcShowParams = null;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        MobSDK.init(this);

        AlibcTradeCommon.turnOnDebug();
        AlibcTradeBiz.turnOnDebug();

        // 初始化淘客参数
        initAlibcTaokeParams();
        // 初始化商品展示参数
        initAlibcShowParams();
        // 电商SDK初始化
        initAlibcTradeSDK();
    }


    /**
     * 初始化淘客参数
     */
    private void initAlibcTaokeParams() {
        alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.adzoneid = SharedPreferencesUtils.getString(this, SHARE_PREFERENCE_PARAM_USER_ADZONEID, ALI_CLIENT_ADZONE_ID);
        alibcTaokeParams.pid = ALI_CLIENT_PID + alibcTaokeParams.adzoneid;
        alibcTaokeParams.subPid = ALI_CLIENT_SUB_PID + alibcTaokeParams.adzoneid;
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", ALI_CLIENT_APP_KEY);
        Log.i(TAG, "淘客参数初始化完成，AdZoneId[" + alibcTaokeParams.adzoneid + "]");
    }


    /**
     * 初始化商品展示参数
     */
    private void initAlibcShowParams() {
        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");
        Log.i(TAG, "商品展示参数初始化完成，ClientType[" + alibcShowParams.getClientType() + "]");
    }


    /**
     * 初始化电商SDK
     */
    private void initAlibcTradeSDK() {

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "阿里电商SDK初始化成功");
                AlibcTradeSDK.setTaokeParams(alibcTaokeParams);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.e(TAG, "阿里电商SDK初始化失败");
                Log.e(TAG, "错误码[" + code + "]，错误信息：" + msg);
            }
        });
    }

}
