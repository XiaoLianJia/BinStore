package com.bincontrol.binstore.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.ui.activity.LoginActivity;
import com.bincontrol.binstore.ui.widget.CompoundOptionBar;

public class MineFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(this);

        initOptionBars(view);
        return view;
    }


    /**
     * 初始化复合选项条
     * @param view view
     */
    private void initOptionBars(View view) {

        CompoundOptionBar optionBarAnnouncement = view.findViewById(R.id.compound_option_bar_announcement);
        optionBarAnnouncement.init(R.mipmap.ic_launcher, getString(R.string.option_announcement), false,"", R.drawable.ic_chevron_right);

        CompoundOptionBar optionBarFeedback = view.findViewById(R.id.compound_option_bar_feedback);
        optionBarFeedback.init(R.mipmap.ic_launcher, getString(R.string.option_feedback), false, "", R.drawable.ic_chevron_right);

        CompoundOptionBar optionBarAbout = view.findViewById(R.id.compound_option_bar_about);
        optionBarAbout.init(R.mipmap.ic_launcher, getString(R.string.option_about), false, "", R.drawable.ic_chevron_right);
    }


    /**
     * 点击事件
     * @param v v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                openActivity(LoginActivity.class);
                break;
        }
    }

}
