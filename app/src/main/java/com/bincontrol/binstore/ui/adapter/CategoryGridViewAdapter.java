package com.bincontrol.binstore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bincontrol.binstore.R;
import com.bincontrol.binstore.entity.CategoryGridItemEntity;

import java.util.List;

public class CategoryGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryGridItemEntity> mGridItems;

    private static class CategoryGridViewHolder {
        ImageView imageView;
        TextView textView;
    }


    public CategoryGridViewAdapter(Context context, List<CategoryGridItemEntity> gridItems) {
        mContext = context;
        mGridItems = gridItems;
    }

    @Override
    public int getCount() {
        return mGridItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mGridItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategoryGridViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.widget_category_item_grid, parent, false);
            viewHolder = new CategoryGridViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryGridViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(mGridItems.get(position).getGridItemIcon());
        viewHolder.textView.setText(mGridItems.get(position).getGridItemTitle());
        return convertView;
    }

}
