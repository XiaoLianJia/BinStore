package com.bincontrol.binstore.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.common.AppManager;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + this.getClass().getSimpleName());
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: " + this.getClass().getSimpleName());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: " + this.getClass().getSimpleName());
        overridePendingTransition(R.anim.anim_push_right_in, R.anim.anim_push_right_out);
    }


    /**
     * 开启一个Activity，带动画效果
     * @param mClass
     * @param bundle
     */
    protected void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
            Log.d(TAG, "openActivity with bundle: " + mClass.getSimpleName());
        } else {
            Log.d(TAG, "openActivity without bundle: " + mClass.getSimpleName());
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_push_left_in, R.anim.anim_push_left_out);
    }

    protected void openActivity(Class<?> mClass) {
        openActivity(mClass, null);
    }


    /**
     * 开启一个Activity，无动画效果
     * @param mClass
     * @param bundle
     */
    protected void openActivityWithoutAnim(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
            Log.d(TAG, "openActivity with bundle: " + mClass.getSimpleName());
        } else {
            Log.d(TAG, "openActivity without bundle: " + mClass.getSimpleName());
        }
        startActivity(intent);
    }

    protected void openActivityWithoutAnim(Class<?> mClass) {
        openActivityWithoutAnim(mClass, null);
    }


    /**
     * 开启一个Activity，带动画效果
     * @param action
     * @param bundle
     */
    protected void openActivity(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
            Log.d(TAG, "openActivity with bundle, by action: " + action);
        } else {
            Log.d(TAG, "openActivity without bundle, by action: " + action);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_push_left_in, R.anim.anim_push_left_out);
    }

    protected void openActivity(String action) {
        openActivity(action, null);
    }


    /**
     * 开启一个Activity，无动画效果
     * @param action
     * @param bundle
     */
    protected void openActivityWithoutAnim(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
            Log.d(TAG, "openActivity with bundlem, by action: " + action);
        } else {
            Log.d(TAG, "openActivity without bundle, by action: " + action);
        }
        startActivity(intent);
    }

    protected void openActivityWithoutAnim(String action) {
        openActivityWithoutAnim(action, null);
    }

}
