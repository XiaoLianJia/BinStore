package com.bincontrol.binstore.ui.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.entity.CategoryGridItemEntity;
import com.bincontrol.binstore.loader.GlideImageLoader;
import com.bincontrol.binstore.ui.activity.GenericCategoryActivity;
import com.bincontrol.binstore.ui.adapter.CategoryGridViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bincontrol.binstore.common.AppConstant.BANNER_DELAY;
import static com.bincontrol.binstore.common.AppConstant.REFRESH_DELAY;

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initRefreshLayout(view);
        initBanner(view);
        initGridView(view);
        return view;
    }


    /**
     * 初始化界面刷新布局
     * @param v v
     */
    private void initRefreshLayout(View v) {

        RefreshLayout refreshLayout = v.findViewById(R.id.smart_refresh_layout);
        if (this.getActivity() != null) {
            refreshLayout.setRefreshHeader(new ClassicsHeader(this.getActivity()));
            refreshLayout.setRefreshFooter(new ClassicsFooter(this.getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(REFRESH_DELAY);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(REFRESH_DELAY);
            }
        });
    }


    /**
     * 初始化图片轮播展位
     * @param view view
     * */
    private void initBanner(View view) {

        String[] urls = getResources().getStringArray(R.array.banner_url);
        List<?> images = new ArrayList<>(Arrays.asList(urls));

        Banner banner = view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.isAutoPlay(true);
        banner.setDelayTime(BANNER_DELAY);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImages(images);
        banner.start();
    }


    /**
     * 初始化商品类别视图
     * @param view view
     */
    private void initGridView(View view) {

        final String[] gridTitles = getResources().getStringArray(R.array.categories_titles);
        TypedArray gridIcons = getResources().obtainTypedArray(R.array.categories_icons);

        final List<CategoryGridItemEntity> gridItemEntities = new ArrayList<>();
        for (int i = 0; i < gridTitles.length; i++) {
            gridItemEntities.add(new CategoryGridItemEntity(gridIcons.getResourceId(i, 0), gridTitles[i]));
        }
        gridIcons.recycle();

        GridView gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(new CategoryGridViewAdapter(getActivity(), gridItemEntities));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Grid Item 点击事件
                Bundle bundle = new Bundle();
                bundle.putString("Title", gridItemEntities.get(position).getGridItemTitle());
                openActivity(GenericCategoryActivity.class, bundle);
            }
        });
    }

}
