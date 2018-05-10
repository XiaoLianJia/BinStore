package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.util.SharedPreferencesUtils;

import static com.bincontrol.binstore.common.AppConstant.SHARE_PREFERENCE_PARAM_FIRST_TIME_RUN;
import static com.bincontrol.binstore.common.AppConstant.SPLASH_DISPLAY;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isAppFirstTimeRun = SharedPreferencesUtils.getBoolean(SplashActivity.this, SHARE_PREFERENCE_PARAM_FIRST_TIME_RUN,true);
                if (isAppFirstTimeRun) {
                    SharedPreferencesUtils.putBoolean(SplashActivity.this, SHARE_PREFERENCE_PARAM_FIRST_TIME_RUN, false);
                }
                openActivityWithoutAnim(MainActivity.class);
                finish();
            }
        }, SPLASH_DISPLAY);
    }

}
