package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bincontrol.binstore.R;
import com.bincontrol.binstore.callback.TradeCallback;
import com.bincontrol.binstore.common.ServerErrorCode;
import com.bincontrol.binstore.entity.CommodityEntity;
import com.bincontrol.binstore.ui.adapter.CommodityViewAdapter;
import com.bincontrol.binstore.util.HttpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.commontitlebar.CustomTitleBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static com.bincontrol.binstore.BinStoreApplication.alibcShowParams;
import static com.bincontrol.binstore.BinStoreApplication.alibcTaokeParams;
import static com.bincontrol.binstore.common.AppConstant.REFRESH_DELAY;
import static com.bincontrol.binstore.common.AppConstant.REQUEST_PARAM_CATEGORY;
import static com.bincontrol.binstore.common.AppConstant.SERVER_URL_COMMODITY_CATEGORY;

public class CommodityActivity extends BaseActivity {

    private static final String TAG = CommodityActivity.class.getName();

    // 请求商品数据完成的消息句柄
    private static final int HANDLER_MSG_GET_COMMODITY_FINISH = 0;

    // 选项栏
    private static final int OPTION_ORDER_BY_DEFAULT = 0;
    private static final int OPTION_ORDER_BY_PRICE = 1;
    private static final int OPTION_ORDER_BY_SALES = 2;
    private static final int OPTION_LAYOUT_TYPE = 3;

    // 商品类目
    private String mCategoryTitle = null;
    // 商品列表布局
    private ListView mListView;
    // 商品网格布局
    private GridView mGridView;
    // 是否采用网格布局
    private boolean mShowGridView = false;
    // 商品布局适配器
    private CommodityViewAdapter mViewAdapter;
    // 商品数据
    private ArrayList<CommodityEntity> mCommodities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCategoryTitle = bundle.getString("Title");
        }

        initTitleBar(mCategoryTitle);
        initTabLayout();
        initRefreshLayout();
    }


    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    /**
     * 初始化标题栏
     * @param title title
     */
    private void initTitleBar(String title) {

        CustomTitleBar customTitleBar = findViewById(R.id.custom_title_bar);
        customTitleBar.setTitle(title == null ? getString(R.string.title_default) : title);
        customTitleBar.addLeftImageButton(R.drawable.ic_arrow_left, R.id.title_bar_image_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 初始化排序选项栏布局
     */
    private void initTabLayout() {

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        addTabItem(tabLayout, getString(R.string.show_type_default), OPTION_ORDER_BY_DEFAULT);
        addTabItem(tabLayout, getString(R.string.show_type_price), OPTION_ORDER_BY_PRICE);
        addTabItem(tabLayout, getString(R.string.show_type_sales), OPTION_ORDER_BY_SALES);
        addTabItem(tabLayout, null, OPTION_LAYOUT_TYPE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabClick(tab);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabClick(tab);
            }
        });
    }


    /**
     * 添加一个tab选项到tabLayout
     * @param tabLayout tabLayout
     * @param title title
     * @param tabIndex tab index
     */
    private void addTabItem(TabLayout tabLayout, String title, int tabIndex) {

        View view = null;
        switch (tabIndex) {
            case OPTION_ORDER_BY_DEFAULT:
                view = LayoutInflater.from(this).inflate(R.layout.widget_tab_item_default, tabLayout, false);
                TextView textViewDefault = view.findViewById(R.id.text_view_default);
                textViewDefault.setText(title);
                break;
            case OPTION_ORDER_BY_PRICE:
            case OPTION_ORDER_BY_SALES:
                view = LayoutInflater.from(this).inflate(R.layout.widget_tab_item_sort, tabLayout, false);
                TextView textViewSort = view.findViewById(R.id.text_view_sort);
                textViewSort.setText(title);
                break;
            case OPTION_LAYOUT_TYPE:
                view = LayoutInflater.from(this).inflate(R.layout.widget_tab_item_view, tabLayout, false);
                break;
        }

        if (view != null) {
            TabLayout.Tab tab = tabLayout.newTab().setCustomView(view);
            tabLayout.addTab(tab);
        }
    }


    /**
     * tab点击事件
     * @param tab tab
     */
    private void onTabClick(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case OPTION_ORDER_BY_DEFAULT:
                break;
            case OPTION_ORDER_BY_PRICE:
                break;
            case OPTION_ORDER_BY_SALES:
                break;
            case OPTION_LAYOUT_TYPE:
                mShowGridView = !mShowGridView;
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.image_view_list).setVisibility(mShowGridView ? GONE : View.VISIBLE);
                    tab.getCustomView().findViewById(R.id.image_view_grid).setVisibility(mShowGridView ? View.VISIBLE : GONE);
                }
                initCommodityView(mShowGridView);
                break;
        }
    }


    /**
     * 初始化界面刷新布局
     */
    private void initRefreshLayout() {

        RefreshLayout refreshLayout = findViewById(R.id.smart_refresh_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.autoRefresh(0, 0, 2L);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCommoditiesByCategory(mCategoryTitle);
                refreshLayout.finishRefresh(REFRESH_DELAY);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(REFRESH_DELAY);
            }
        });
    }


    /**
     * 初始化商品布局
     * @param showGridView show
     */
    private void initCommodityView(boolean showGridView) {

        mListView = findViewById(R.id.list_view);
        mGridView = findViewById(R.id.grid_view);

        if (showGridView) {
            mListView.setVisibility(GONE);
            mGridView.setVisibility(View.VISIBLE);
            initGridView();
        } else {
            mListView.setVisibility(View.VISIBLE);
            mGridView.setVisibility(GONE);
            initListView();
        }
    }


    /**
     * 初始化商品布局-ListView方式排列
     */
    private void initListView() {

        mViewAdapter = new CommodityViewAdapter(this, mCommodities);
        mListView.setAdapter(mViewAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commodityId = mCommodities.get(position).getCommodityId();
                showCommodityById(commodityId);
            }
        });
    }


    /**
     * 初始化商品布局-GridView方式排列
     */
    private void initGridView() {

        mViewAdapter = new CommodityViewAdapter(this, mCommodities);
        mViewAdapter.showGridView();
        mGridView.setAdapter(mViewAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commodityId = mCommodities.get(position).getCommodityId();
                showCommodityById(commodityId);
            }
        });
    }


    /**
     * 请求单类目所有商品数据
     * @param category category
     */
    private void getCommoditiesByCategory(final String category) {

        new Thread(new Runnable(){
            @Override
            public void run() {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair(REQUEST_PARAM_CATEGORY, category));
                JSONObject jsonReturn = HttpUtils.get(SERVER_URL_COMMODITY_CATEGORY, params);

                if (jsonReturn != null && jsonReturn.getInteger("status") == ServerErrorCode.BIN_OK.getCode()) {
                    Log.d(TAG, "请求[" + category + "]商品成功");

                    JSONArray jsonArray = jsonReturn.getJSONArray("data");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        CommodityEntity commodity = new CommodityEntity();
                        commodity.setCommodityId(jsonObject.getString("commodity_id"));
                        commodity.setPictureUrl(jsonObject.getString("picture"));
                        commodity.setCommodityTitle(jsonObject.getString("title"));
                        commodity.setFinalPrice(Float.parseFloat(jsonObject.getString("price")));
                        commodity.setVolume(jsonObject.getString("volume"));
                        mCommodities.add(commodity);
                    }

                    Message msg = new Message();
                    msg.what = HANDLER_MSG_GET_COMMODITY_FINISH;
                    mHandler.sendMessage(msg);

                } else {
                    Log.d(TAG, "请求[" + category + "]商品失败");
                    Log.d(TAG, jsonReturn == null ? "ERROR: EMPTY RETURN" : jsonReturn.getString("msg"));

                }

            }
        }).start();
    }


    /**
     * 通过链接打开商品页面
     */
    public void showCommodityByUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            Toast.makeText(CommodityActivity.this, "URL为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        AlibcTrade.show(this, new AlibcPage(url), alibcShowParams, alibcTaokeParams, exParams, new TradeCallback());
    }


    /**
     * 显示商品ID打开商品页面
     */
    public void showCommodityById(String id) {

        if (TextUtils.isEmpty(id)) {
            Toast.makeText(CommodityActivity.this, "商品id为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new TradeCallback());
    }


    private final MyHandler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {

        private final WeakReference<CommodityActivity> mActivity;

        private MyHandler(CommodityActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (mActivity.get() == null) {
                return;
            }
            CommodityActivity activity = mActivity.get();
            switch (msg.what) {
                case HANDLER_MSG_GET_COMMODITY_FINISH:
                    activity.initCommodityView(activity.mShowGridView);
                    break;
                default:
                    break;
            }
        }
    }

}
