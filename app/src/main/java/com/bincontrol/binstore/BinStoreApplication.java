package com.bincontrol.binstore;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.AlibcTradeBiz;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.common.AlibcTradeCommon;

import java.util.HashMap;

import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_ADZONE_ID;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_APP_KEY;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_PID;
import static com.bincontrol.binstore.common.AppConstant.ALI_CLIENT_SUB_PID;


public class BinStoreApplication extends Application {

    public static BinStoreApplication application = null;
    public static AlibcTaokeParams alibcTaokeParams = null;
    public static AlibcShowParams alibcShowParams = null;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        AlibcTradeCommon.turnOnDebug();
        AlibcTradeBiz.turnOnDebug();

        // 初始化淘客参数
        initAlibcTaokeParams();
        // 初始化商品展示参数
        initAlibcShowParams();
        // 电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(BinStoreApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
                AlibcTradeSDK.setTaokeParams(alibcTaokeParams);
            }

            @Override
            public void onFailure(int code, String msg) {
                String errorText = "初始化失败，错误码：" + code + "；错误信息：" + msg;
                Toast.makeText(BinStoreApplication.this,  errorText, Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 初始化淘客参数
     */
    private void initAlibcTaokeParams() {
        alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.adzoneid = ALI_CLIENT_ADZONE_ID;
        alibcTaokeParams.pid = ALI_CLIENT_PID;
        alibcTaokeParams.subPid = ALI_CLIENT_SUB_PID;
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", ALI_CLIENT_APP_KEY);
    }


    /**
     * 初始化商品展示参数
     */
    private void initAlibcShowParams() {
        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        alibcShowParams.setClientType("taobao_scheme");
    }


    public static BinStoreApplication getInstance() {
        if (application == null) {
            application = new BinStoreApplication();
        }
        return application;
    }
    
}
