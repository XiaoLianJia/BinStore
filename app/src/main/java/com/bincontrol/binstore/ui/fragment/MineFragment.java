package com.bincontrol.binstore.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.ui.widget.CompoundOptionBar;

public class MineFragment extends BaseFragment {

    private View view;

    // 登录
    private CompoundOptionBar lineViewLogin;

    // 订单
    private CompoundOptionBar lineViewOrder;

    // 设置
    private CompoundOptionBar lineViewSetting;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, container, false);

        lineViewLogin = view.findViewById(R.id.line_view_login);
        lineViewLogin.initMine(R.mipmap.ic_launcher, "登录", "", true);

        lineViewOrder = view.findViewById(R.id.line_view_order);
        lineViewOrder.initMine(R.mipmap.ic_launcher, "订单", "", true);

        lineViewSetting = view.findViewById(R.id.line_view_setting);
        lineViewSetting.initMine(R.mipmap.ic_launcher, "设置", "", true);

        return view;
    }

}
