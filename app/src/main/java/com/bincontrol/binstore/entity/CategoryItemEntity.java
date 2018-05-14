package com.bincontrol.binstore.entity;

import android.support.annotation.StringRes;

public class CategoryItemEntity {

    private @StringRes int mIcon;
    private String mTitle;


    public CategoryItemEntity(int icon, String title) {
        mIcon = icon;
        mTitle = title;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

}
