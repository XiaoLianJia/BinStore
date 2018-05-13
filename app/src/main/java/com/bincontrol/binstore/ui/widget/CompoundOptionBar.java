package com.bincontrol.binstore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bincontrol.binstore.util.DensityUtils;
import com.bincontrol.binstore.R;

public class CompoundOptionBar extends LinearLayout {

    // 最外层容器
    private LinearLayout mLinearLayoutContainer;

    // 上下分割线，默认隐藏上分割线
    private View mViewDividerLineTop;
    private View mViewDividerLineBottom;

    // 左侧图标
    private ImageView mImageViewLeft;

    // 左侧的文字
    private TextView mTextViewLeft;

    // 中间的输入框
    private EditText mEditText;

    // 右侧的文字
    private TextView mTextViewRight;

    // 右侧图标，通常是箭头
    private ImageView mImageViewRight;


    /**
     * 构造器
     */
    public CompoundOptionBar(Context context) {
        super(context);
    }

    public CompoundOptionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundOptionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 初始化各个控件
     */
    public void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.widget_compound_option_bar, this, true);
        mLinearLayoutContainer = findViewById(R.id.container);
        mViewDividerLineTop = findViewById(R.id.view_divider_line_top);
        mViewDividerLineBottom = findViewById(R.id.view_divider_line_bottom);
        mImageViewLeft = findViewById(R.id.image_view_left);
        mTextViewLeft = findViewById(R.id.text_view_left);
        mEditText = findViewById(R.id.edit_text);
        mTextViewRight = findViewById(R.id.text_view_right);
        mImageViewRight = findViewById(R.id.image_view_right);
    }


    /**
     * 初始化
     */
    public void init(int iconLeft, String textLeft, boolean showEditText,
                     String textRight, int iconRight) {
        initView();
        setLeftIcon(iconLeft);
        setLeftTextContent(textLeft);
        showEditText(showEditText);
        setRightTextContent(textRight);
        setRightIcon(iconRight);
    }


    /**
     * 设置container的padding
     */
    public void setContainerPadding(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
        mLinearLayoutContainer.setPadding(
                DensityUtils.dp2px(getContext(), paddingLeft),
                DensityUtils.dp2px(getContext(), paddingTop),
                DensityUtils.dp2px(getContext(), paddingRight),
                DensityUtils.dp2px(getContext(), paddingBottom));
    }


    /**
     * 设置上下分割线的显示
     */
    public void showDividerLine(Boolean showTop, Boolean showBottom) {
        mViewDividerLineTop.setVisibility(showTop ? VISIBLE : GONE);
        mViewDividerLineBottom.setVisibility(showBottom ? VISIBLE : GONE);
    }

    /**
     * 设置上分割线的颜色
     */
    public void setDividerLineTopColor(int colorRes) {
        mViewDividerLineTop.setBackgroundColor(getResources().getColor(colorRes));
    }

    /**
     * 设置下分割线的颜色
     */
    public void setDividerLineBottomColor(int colorRes) {
        mViewDividerLineBottom.setBackgroundColor(getResources().getColor(colorRes));
    }

    /**
     * 设置上分割线的高度
     */
    public void setDividerLineTopHigiht(int higihtDp) {
        ViewGroup.LayoutParams layoutParams = mViewDividerLineTop.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), higihtDp);
        mViewDividerLineTop.setLayoutParams(layoutParams);
    }

    /**
     * 设置下分割线的高度
     */
    public void setDividerLineBottomHigiht(int higihtDp) {
        ViewGroup.LayoutParams layoutParams = mViewDividerLineBottom.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), higihtDp);
        mViewDividerLineBottom.setLayoutParams(layoutParams);
    }


    /**
     * 设置左侧图标
     */
    public void setLeftIcon(int iconRes) {
        mImageViewLeft.setImageResource(iconRes);
    }

    /**
     * 设置左侧图标尺寸
     */
    public void setLeftIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = mImageViewLeft.getLayoutParams();
        layoutParams.width = DensityUtils.dp2px(getContext(), widthDp);
        layoutParams.height = DensityUtils.dp2px(getContext(), heightDp);
        mImageViewLeft.setLayoutParams(layoutParams);
    }

    /**
     * 设置左侧图标显示与否
     */
    public void showLeftIcon(boolean show) {
        mImageViewLeft.setVisibility(show ? VISIBLE : GONE);
    }


    /**
     * 设置左侧文字的内容
     */
    public void setLeftTextContent(String text) {
        mTextViewLeft.setText(text);
    }

    /**
     * 设置左侧文字的颜色
     */
    public void setLeftTextColor(int colorRes) {
        mTextViewLeft.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置左侧文字的大小
     */
    public void setLeftTextSize(int sizeSp) {
        mTextViewLeft.setTextSize(sizeSp);
    }


    /**
     * 设置右侧文字的内容
     */
    public void setRightTextContent(String text) {
        mTextViewRight.setText(text);
    }

    /**
     * 设置右侧文字的颜色
     */
    public void setRightTextColor(int colorRes) {
        mTextViewRight.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置右侧文字的大小
     */
    public void setRightTextSize(int sizeSp) {
        mTextViewRight.setTextSize(sizeSp);
    }

    /**
     * 设置右侧图标
     */
    public void setRightIcon(int iconRes) {
        mImageViewRight.setImageResource(iconRes);
    }

    /**
     * 获取右侧图标
     */
    public ImageView getRightIcon() {
        return mImageViewRight;
    }

    /**
     * 设置右侧图标显示与否
     */
    public void showRightIcon(boolean show) {
        mImageViewRight.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * 设置中间输入框的hint内容
     */
    public void setEditTextHint(String hint) {
        mEditText.setHint(hint);
    }

    /**
     * 设置中间输入框的内容
     */
    public void setEditTextContent(String content) {
        mEditText.setText(content);
    }

    /**
     * 设置中间输入框显示与否
     */
    public void showEditText(boolean show) {
        mEditText.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * 设置中间输入框是否可输入
     */
    public void setEditTextFocusable(boolean focusable) {
        mEditText.setFocusable(focusable);
    }

    /**
     * 获取中间输入框输入的内容
     */
    public String getEditTextContent() {
        return String.valueOf(mEditText.getText());
    }

    /**
     * 设置中间输入框的颜色
     */
    public void setEditTextColor(int colorRes) {
        mEditText.setTextColor(getResources().getColor(colorRes));
    }

    /**
     * 设置中间输入框字体大小
     */
    public void setEditTextSize(int sizeSp) {
        mEditText.setTextSize(sizeSp);
    }


    /**
     * 整体点击事件接口
     * @param onContainerClickListener onContainerClickListener
     */
    public void setOnContainerClickListener(final OnContainerClickListener onContainerClickListener) {
        mLinearLayoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onContainerClickListener.onContainerClick(mLinearLayoutContainer);
            }
        });
    }

    public interface OnContainerClickListener {
        void onContainerClick(View view);
    }


    /**
     * 右侧图标（箭头）点击事件接口
     * @param onArrowClickListener onArrowClickListener
     */
    public void setOnArrowClickListener(final OnArrowClickListener onArrowClickListener) {

        mImageViewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onArrowClickListener.onArrowClick(mImageViewRight);
            }
        });
    }

    public interface OnArrowClickListener {
        void onArrowClick(View view);
    }

}