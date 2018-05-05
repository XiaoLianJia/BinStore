package com.bincontrol.binstore.entity;

import android.support.annotation.StringRes;

public class CategoryGridItemEntity {

    private @StringRes int mGridItemIcon;
    private String mGridItemTitle;

    public CategoryGridItemEntity(int gridItemIcon, String gridItemTitle) {
        mGridItemIcon = gridItemIcon;
        mGridItemTitle = gridItemTitle;
    }

    public int getGridItemIcon() {
        return mGridItemIcon;
    }

    public void setGridItemIcon(int gridItemIcon) {
        this.mGridItemIcon = gridItemIcon;
    }

    public String getGridItemTitle() {
        return mGridItemTitle;
    }

    public void setGridItemTitle(String gridItemTitle) {
        this.mGridItemTitle = gridItemTitle;
    }

}
