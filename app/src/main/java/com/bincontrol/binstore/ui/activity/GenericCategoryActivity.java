package com.bincontrol.binstore.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.bincontrol.binstore.entity.CommodityEntity;
import com.bincontrol.binstore.ui.adapter.CommodityViewAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.commontitlebar.CustomTitleBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static com.bincontrol.binstore.BinStoreApplication.alibcShowParams;
import static com.bincontrol.binstore.BinStoreApplication.alibcTaokeParams;
import static com.bincontrol.binstore.common.AppConstant.REFRESH_DELAY;
import static com.bincontrol.binstore.common.AppConstant.SERVER_URL_LOCAL;

public class GenericCategoryActivity extends BaseActivity {

    private static final int SHOW_ORDER_DEFAULT = 0;
    private static final int SHOW_ORDER_PRICE = 1;
    private static final int SHOW_ORDER_SALES = 2;
    private static final int SHOW_LAYOUT_TYPE = 3;

    private static final int HANDLER_MSG_GET_COMMODITY_FINISH = 0;

    private String mCategoryTitle = null;
    private ListView mListView;
    private GridView mGridView;
    private boolean mShowGridView = false;
    private CommodityViewAdapter mViewAdapter;
    private ArrayList<CommodityEntity> mCommodities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_category);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCategoryTitle = bundle.getString("Title");
        }

        initTitleBar(mCategoryTitle);
        initTabLayout();
        initRefreshLayout();
        getCommodity(mCategoryTitle);
    }


    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    /**
     * 初始化标题栏
     */
    private void initTitleBar(String title) {

        CustomTitleBar customTitleBar = findViewById(R.id.custom_title_bar);
        customTitleBar.setTitle(title == null ? "商品" : title);
        customTitleBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
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
        addTabItem(tabLayout, "综合", SHOW_ORDER_DEFAULT);
        addTabItem(tabLayout, "价格", SHOW_ORDER_PRICE);
        addTabItem(tabLayout, "销量", SHOW_ORDER_SALES);
        addTabItem(tabLayout, null, SHOW_LAYOUT_TYPE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case SHOW_ORDER_DEFAULT:
                        break;
                    case SHOW_ORDER_PRICE:
                        break;
                    case SHOW_ORDER_SALES:
                        break;
                    case SHOW_LAYOUT_TYPE:
                        mShowGridView = !mShowGridView;
                        initCommodityView(mShowGridView);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case SHOW_ORDER_DEFAULT:
                        break;
                    case SHOW_ORDER_PRICE:
                        break;
                    case SHOW_ORDER_SALES:
                        break;
                    case SHOW_LAYOUT_TYPE:
                        mShowGridView = !mShowGridView;
                        initCommodityView(mShowGridView);
                        break;
                }
            }
        });
    }

    private void addTabItem(TabLayout tabLayout, String title, int type) {

        View view = LayoutInflater.from(this).inflate(R.layout.widget_tab_item, tabLayout, false);
        TextView textView = view.findViewById(R.id.text_view);
        ImageView imageView1 = view.findViewById(R.id.image_view_1);
        ImageView imageView2 = view.findViewById(R.id.image_view_2);
        textView.setText(title);

        switch (type) {
            case 0:
                imageView1.setImageResource(R.drawable.ic_arrow_drop_up);
                imageView1.setVisibility(GONE);
                imageView2.setImageResource(R.drawable.ic_arrow_drop_down);
                break;
            case 1:
            case 2:
                imageView1.setImageResource(R.drawable.ic_arrow_drop_up);
                imageView2.setImageResource(R.drawable.ic_arrow_drop_down);
                break;
            case 3:
                textView.setVisibility(GONE);
                imageView1.setImageResource(R.drawable.ic_tab_list);
                imageView2.setImageResource(R.drawable.ic_tab_grid);
                imageView2.setVisibility(GONE);
                break;
        }

        TabLayout.Tab tab = tabLayout.newTab().setCustomView(view);
        tabLayout.addTab(tab);
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
                getCommodity(mCategoryTitle);
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
     * 获取商品
     */
    private void getCommodity(final String category) {

        new Thread(new Runnable(){
            @Override
            public void run() {

                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder url = new StringBuilder();
                url.append(SERVER_URL_LOCAL).append(category);
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url.toString());
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

                    String buffer;
                    while ((buffer = bufferedReader.readLine()) != null) {
                        stringBuilder.append(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = JSON.parseObject(stringBuilder.toString()).getJSONObject("tbk_dg_item_coupon_get_response").getJSONObject("results");
                JSONArray jsonArray = jsonObject.getJSONArray("tbk_coupon");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jo = (JSONObject) jsonArray.get(i);

                    CommodityEntity commodity = new CommodityEntity();
                    commodity.setCommissionRate(jo.getFloat("commission_rate"));
                    commodity.setCouponClickUrl(jo.getString("coupon_click_url"));
                    commodity.setCouponEndTime(jo.getDate("coupon_end_time"));
                    commodity.setCouponInfo(jo.getString("coupon_info"));
                    commodity.setCouponRemainCount(jo.getInteger("coupon_remain_count"));
                    commodity.setCouponStartTime(jo.getDate("coupon_start_time"));
                    commodity.setCouponTotalCount(jo.getInteger("coupon_total_count"));
                    commodity.setCommodityDescription(jo.getString("item_description"));
                    commodity.setCommodityUrl(jo.getString("item_url"));
                    commodity.setSellerNick(jo.getString("nick"));
                    commodity.setCommodityId(jo.getString("num_iid"));
                    commodity.setPictureUrl(jo.getString("pict_url"));
                    commodity.setSellerId(jo.getString("seller_id"));
                    commodity.setShopTitle(jo.getString("shop_title"));
                    commodity.setCommodityTitle(jo.getString("title"));
                    commodity.setSellerType(jo.getInteger("user_type"));
                    commodity.setVolume(jo.getString("volume"));
                    commodity.setFinalPrice(jo.getFloat("zk_final_price"));

                    JSONArray ja = jo.getJSONObject("small_images").getJSONArray("string");
                    List<String> images = new ArrayList<>();
                    for (int j = 0; j < ja.size(); j++) {
                        images.add(ja.get(j).toString());
                    }
                    commodity.setSmallImagesUrl(images);
                    mCommodities.add(commodity);
                }

                Message msg = new Message();
                msg.what = HANDLER_MSG_GET_COMMODITY_FINISH;
                mHandler.sendMessage(msg);

            }
        }).start();
    }


    /**
     * 通过链接打开商品页面
     */
    public void showCommodityByUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            Toast.makeText(GenericCategoryActivity.this, "URL为空", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(GenericCategoryActivity.this, "商品id为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        Map<String, String> exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new TradeCallback());
    }


    private final MyHandler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {

        private final WeakReference<GenericCategoryActivity> mActivity;

        private MyHandler(GenericCategoryActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (mActivity.get() == null) {
                return;
            }
            GenericCategoryActivity activity = mActivity.get();
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
