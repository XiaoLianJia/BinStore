package com.bincontrol.binstore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.entity.CommodityEntity;
import com.bincontrol.binstore.util.BitmapUtils;

import java.util.ArrayList;


public class CommodityViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CommodityEntity> mCommodityEntities;
    private boolean mShowGridView = false;

    private static class CommodityViewHolder {
        ImageView ivPicture;
        TextView tvTitle;
        TextView tvOriginalPrice;
        TextView tvCouponPrice;
        TextView tvSalesVolume;
    }


    public CommodityViewAdapter(Context context, ArrayList<CommodityEntity> commodityEntities) {
        mContext = context;
        mCommodityEntities = commodityEntities;
    }

    public void showGridView() {
        mShowGridView = true;
    }

    @Override
    public int getCount() {
        return mCommodityEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommodityEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StringBuilder stringBuilder;
        CommodityViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(mShowGridView ? R.layout.widget_commodity_item_grid
                    : R.layout.widget_commodity_item_list, parent, false);

            viewHolder = new CommodityViewHolder();
            viewHolder.ivPicture = convertView.findViewById(R.id.image_view_picture);
            viewHolder.tvTitle = convertView.findViewById(R.id.text_view_title);
            viewHolder.tvOriginalPrice = convertView.findViewById(R.id.text_view_original_price);
            viewHolder.tvCouponPrice = convertView.findViewById(R.id.text_view_coupon_price);
            viewHolder.tvSalesVolume = convertView.findViewById(R.id.text_view_volume);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommodityViewHolder) convertView.getTag();
        }

        // 商品图片
        BitmapUtils.getInstance().loadImage(mCommodityEntities.get(position).getPictureUrl(), viewHolder.ivPicture, mContext);

        // 商品标题
        viewHolder.tvTitle.setText(mCommodityEntities.get(position).getCommodityTitle());

        // 商品原价
        stringBuilder = new StringBuilder("原价 ￥ ");
        stringBuilder.append(String.valueOf(mCommodityEntities.get(position).getFinalPrice()));
        viewHolder.tvOriginalPrice.setText(stringBuilder.toString());

        // 商品券后价
        stringBuilder = new StringBuilder("券后价 ￥ ");
        stringBuilder.append(String.valueOf(mCommodityEntities.get(position).getFinalPrice()));
        viewHolder.tvCouponPrice.setText(stringBuilder.toString());

        // 商品月销量
        stringBuilder = new StringBuilder("月销量 ");
        stringBuilder.append(mCommodityEntities.get(position).getVolume());
        viewHolder.tvSalesVolume.setText(stringBuilder.toString());
        return convertView;
    }

}
