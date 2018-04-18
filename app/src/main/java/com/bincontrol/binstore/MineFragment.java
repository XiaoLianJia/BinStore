package com.bincontrol.binstore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MineFragment extends Fragment {

    private View view;

    // 登录
    private BinOneLineView lineViewLogin;

    // 订单
    private BinOneLineView lineViewOrder;

    // 设置
    private BinOneLineView lineViewSetting;


    public MineFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, container, false);

        lineViewLogin = (BinOneLineView) view.findViewById(R.id.line_view_login);
        lineViewLogin.initMine(R.mipmap.ic_launcher, "登录", "", true);

        lineViewOrder = (BinOneLineView) view.findViewById(R.id.line_view_order);
        lineViewOrder.initMine(R.mipmap.ic_launcher, "订单", "", true);

        lineViewSetting = (BinOneLineView) view.findViewById(R.id.line_view_setting);
        lineViewSetting.initMine(R.mipmap.ic_launcher, "设置", "", true);

        return view;
    }

}
