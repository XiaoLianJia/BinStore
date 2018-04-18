package com.bincontrol.binstore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BinOneLineView extends LinearLayout {

    // 最外层容器
    private LinearLayout linearLayoutContainer;

    // 上下分割线，默认隐藏上分割线
    private View viewDividerLineTop;
    private View viewDividerLineBottom;

    // 最左边的Icon
    private ImageView imageViewIconLeft;

    // 中间的文字内容
    private TextView textViewTextMiddle;

    // 中间的输入框
    private EditText editTextInput;

    // 右边的文字
    private TextView textViewTextRight;

    // 右边的icon，通常是箭头
    private ImageView imageViewIconRight;

    // 整行点击事件
    public static interface OnContainerClickListener {
        void onContainerClick(View view);
    }

    // 右边箭头的点击事件
    public static interface OnArrowClickListener {
        void onArrowClick(View view);
    }

    public BinOneLineView(Context context) {
        super(context);
    }

    public BinOneLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化各个控件
     */
    public BinOneLineView initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_onelineview, this, true);
        linearLayoutContainer = (LinearLayout) findViewById(R.id.container);
        viewDividerLineTop = findViewById(R.id.divider_line_top);
        viewDividerLineBottom = findViewById(R.id.divider_line_bottom);
        imageViewIconLeft = (ImageView) findViewById(R.id.icon_left);
        textViewTextMiddle = (TextView) findViewById(R.id.text_middle);
        editTextInput = (EditText) findViewById(R.id.edit_input);
        textViewTextRight = (TextView) findViewById(R.id.text_right);
        imageViewIconRight = (ImageView) findViewById(R.id.icon_right);
        return this;
    }

    /**
     * 文字 + 箭头
     */
    public BinOneLineView init(String content) {
        initView();
        showLeftIcon(false);
        setTextContent(content);
        showEdit(false);
        setRightText("");
        return this;
    }

    /**
     * icon + 文字 + 右箭头 + 下分割线
     */
    public BinOneLineView init(int iconRes, String content) {
        initView();
        showDividerLine(false, true);
        setLeftIcon(iconRes);
        setTextContent(content);
        showEdit(false);
        setRightText("");
        showArrow(true);
        return this;
    }

    /**
     * icon + 文字 + 右箭头（显示/不显示）+ 右箭头左边的文字（显示/不显示）+ 下分割线
     */
    public BinOneLineView initMine(int iconRes, String textContent, String textRight, boolean showArrow) {
        init(iconRes, textContent);
        setRightText(textRight);
        showArrow(showArrow);
        return this;
    }


    /**
     * icon + 文字 + edit + 下分割线
     */
    public BinOneLineView initItemWithEdit(int iconRes, String textContent, String editHint) {
        init(iconRes, textContent);
        showEdit(true);
        setEditHint(editHint);
        showArrow(false);
        return this;
    }


    /**
     * 设置 container 的 paddingTop 与 paddingBottom ，从而控制整体的行高
     * paddingLeft 与 paddingRight 默认 20dp
     */
    public BinOneLineView setContainerPaddingTopBottom(int paddingTop, int paddingBottom) {
        linearLayoutContainer.setPadding(
                DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddingTop),
                DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddingBottom));
        return this;
    }

    /**
     * 设置 container 的 paddingLeft 与 paddingRight ，从而控制整体的行高
     * paddingTop 与 paddingBottom 默认 15dp
     */
    public BinOneLineView setContainerPaddingLeftRight(int paddingLeft, int paddingRight) {
        linearLayoutContainer.setPadding(
                DensityUtils.dp2px(getContext(), paddingLeft),
                DensityUtils.dp2px(getContext(), 15),
                DensityUtils.dp2px(getContext(), paddingRight),
                DensityUtils.dp2px(getContext(), 15));
        return this;
    }

    /**
     * 设置上下分割线的显示情况
     */
    public BinOneLineView showDividerLine(Boolean showTop, Boolean showBottom) {
        viewDividerLineTop.setVisibility(showTop ? VISIBLE : GONE);
        viewDividerLineBottom.setVisibility(showBottom ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置上分割线的颜色
     */
    public BinOneLineView setDividerLineTopColor(int colorRes) {
        viewDividerLineTop.setBackgroundColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置下分割线的颜色
     */
    public BinOneLineView setDividerLineBottomColor(int colorRes) {
        viewDividerLineBottom.setBackgroundColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     */
    public BinOneLineView setDividerLineTopHigiht(int higihtDp) {
        ViewGroup.LayoutParams layoutParams = viewDividerLineTop.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), higihtDp);
        viewDividerLineTop.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置下分割线的高度
     */
    public BinOneLineView setDividerLineBottomHigiht(int higihtDp) {
        ViewGroup.LayoutParams layoutParams = viewDividerLineBottom.getLayoutParams();
        layoutParams.height = DensityUtils.dp2px(getContext(), higihtDp);
        viewDividerLineBottom.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置左边Icon
     */
    public BinOneLineView setLeftIcon(int iconRes) {
        imageViewIconLeft.setImageResource(iconRes);
        return this;
    }

    /**
     * 设置左边Icon宽高
     */
    public BinOneLineView setLeftIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = imageViewIconLeft.getLayoutParams();
        layoutParams.width = DensityUtils.dp2px(getContext(), widthDp);
        layoutParams.height = DensityUtils.dp2px(getContext(), heightDp);
        imageViewIconLeft.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置左边Icon显示与否
     */
    public BinOneLineView showLeftIcon(boolean show) {
        imageViewIconLeft.setVisibility(show ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置中间文字的内容
     */
    public BinOneLineView setTextContent(String text) {
        textViewTextMiddle.setText(text);
        return this;
    }

    /**
     * 设置中间文字的颜色
     */
    public BinOneLineView setTextContentColor(int colorRes) {
        textViewTextMiddle.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间文字的大小
     */
    public BinOneLineView setTextContentSize(int sizeSp) {
        textViewTextMiddle.setTextSize(sizeSp);
        return this;
    }

    /**
     * 设置右边文字的内容
     */
    public BinOneLineView setRightText(String text) {
        textViewTextRight.setText(text);
        return this;
    }

    /**
     * 设置右边文字的颜色
     */
    public BinOneLineView setRightTextColor(int colorRes) {
        textViewTextRight.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置右边文字的大小
     */
    public BinOneLineView setRightTextSize(int sizeSp) {
        textViewTextRight.setTextSize(sizeSp);
        return this;
    }

    /**
     * 设置右边Icon
     */
    public BinOneLineView setRightIcon(int iconRes) {
        imageViewIconRight.setImageResource(iconRes);
        return this;
    }

    /**
     * 获取右边icon
     */
    public ImageView getRightIcon() {
        return imageViewIconRight;
    }

    /**
     * 设置右边Icon显示与否
     */
    public BinOneLineView showArrow(boolean show) {
        imageViewIconRight.setVisibility(show ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置中间输入框的hint内容
     */
    public BinOneLineView setEditHint(String hint) {
        editTextInput.setHint(hint);
        return this;
    }

    /**
     * 设置中间输入框的内容
     */
    public BinOneLineView setEditContent(String content) {
        editTextInput.setText(content);
        return this;
    }

    /**
     * 设置中间输入框显示与否
     */
    public BinOneLineView showEdit(boolean show) {
        editTextInput.setVisibility(show ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置中间输入框是否可输入
     */
    public BinOneLineView setEditFocusable(boolean focusable) {
        editTextInput.setFocusable(focusable);
        return this;
    }

    /**
     * 获取中间输入框输入的内容
     */
    public String getEditContent() {
        return String.valueOf(editTextInput.getText());
    }

    /**
     * 设置中间输入框的颜色
     */
    public BinOneLineView setEditColor(int colorRes) {
        editTextInput.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间输入框字体大小
     */
    public BinOneLineView setEditSize(int sizeSp) {
        editTextInput.setTextSize(sizeSp);
        return this;
    }


    public BinOneLineView setOnContainerClickListener(final OnContainerClickListener onContainerClickListener, final int tag) {
        linearLayoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutContainer.setTag(tag);
                onContainerClickListener.onContainerClick(linearLayoutContainer);
            }
        });
        return this;
    }

    public BinOneLineView setOnArrowClickListener(final OnArrowClickListener onArrowClickListener, final int tag) {

        imageViewIconRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewIconRight.setTag(tag);
                onArrowClickListener.onArrowClick(imageViewIconLeft);
            }
        });
        return this;
    }

}