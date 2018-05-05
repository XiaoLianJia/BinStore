package com.bincontrol.binstore.common;

import android.app.Activity;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {}

    public static AppManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     * @return
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束指定Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     * @param activityClass
     */
    public void finishActivity(Class<?> activityClass) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(activityClass)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
    }

}
