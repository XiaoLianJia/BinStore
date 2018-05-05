package com.bincontrol.binstore.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bincontrol.binstore.R;

public class AgencyFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agency, container, false);
        return view;
    }

}
