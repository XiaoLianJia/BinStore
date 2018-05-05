package com.bincontrol.binstore.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bincontrol.binstore.R;

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    /**
     * 开启一个Activity，带动画效果
     * @param mClass
     * @param bundle
     */
    protected void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
            Log.d(TAG, "openActivity with bundle: " + mClass.getSimpleName());
        } else {
            Log.d(TAG, "openActivity without bundle: " + mClass.getSimpleName());
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_push_left_in,R.anim.anim_push_left_out);
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
        Intent intent = new Intent(getActivity(), mClass);
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
        getActivity().overridePendingTransition(R.anim.anim_push_left_in,R.anim.anim_push_left_out);
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
