package com.bincontrol.binstore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommodityEntity {

    // 佣金比率
    private float mCommissionRate;
    // 商品领券链接
    private String mCouponClickUrl;
    // 优惠券有效期起始时间
    private Date mCouponStartTime;
    // 优惠券有效期截止时间
    private Date mCouponEndTime;
    // 优惠详情
    private String mCouponInfo;
    // 优惠券余量
    private int mCouponRemainCount;
    // 优惠券总量
    private int mCouponTotalCount;
    // 商品说明
    private String mCommodityDescription;
    // 商品链接
    private String mCommodityUrl;
    // 商品ID
    private String mCommodityId;
    // 商品图片地址
    private String mPictureUrl;
    // 卖家昵称
    private String mSellerNick;
    // 卖家ID
    private String mSellerId;
    // 店铺名称
    private String mShopTitle;
    // 卖家类型，0表示集市，1表示商城
    private int mSellerType;
    // 小图片地址列表
    private List<String> mSmallImagesUrl = new ArrayList<>();
    // 商品标题
    private String mCommodityTitle;
    // 月销量
    private String mVolume;
    // 券后价
    private float mFinalPrice;


    /**
     * 佣金比率
     */
    public float getCommissionRate() {
        return mCommissionRate;
    }

    public void setCommissionRate(float mCommissionRate) {
        this.mCommissionRate = mCommissionRate;
    }


    /**
     * 商品领券链接
     */
    public String getCouponClickUrl() {
        return mCouponClickUrl;
    }

    public void setCouponClickUrl(String mCouponClickUrl) {
        this.mCouponClickUrl = mCouponClickUrl;
    }


    /**
     * 优惠券有效期起始时间
     */
    public Date getCouponStartTime() {
        return mCouponStartTime;
    }

    public void setCouponStartTime(Date mCouponStartTime) {
        this.mCouponStartTime = mCouponStartTime;
    }


    /**
     * 优惠券有效期截止时间
     */
    public Date getCouponEndTime() {
        return mCouponEndTime;
    }

    public void setCouponEndTime(Date mCouponEndTime) {
        this.mCouponEndTime = mCouponEndTime;
    }


    /**
     * 优惠详情
     */
    public String getCouponInfo() {
        return mCouponInfo;
    }

    public void setCouponInfo(String mCouponInfo) {
        this.mCouponInfo = mCouponInfo;
    }


    /**
     * 优惠券余量
     */
    public int getCouponRemainCount() {
        return mCouponRemainCount;
    }

    public void setCouponRemainCount(int mCouponRemainCount) {
        this.mCouponRemainCount = mCouponRemainCount;
    }


    /**
     * 优惠券总量
     */
    public int getCouponTotalCount() {
        return mCouponTotalCount;
    }

    public void setCouponTotalCount(int mCouponTotalCount) {
        this.mCouponTotalCount = mCouponTotalCount;
    }


    /**
     * 商品说明
     */
    public String getCommodityDescription() {
        return mCommodityDescription;
    }

    public void setCommodityDescription(String mCommodityDescription) {
        this.mCommodityDescription = mCommodityDescription;
    }


    /**
     * 商品链接
     */
    public String getCommodityUrl() {
        return mCommodityUrl;
    }

    public void setCommodityUrl(String mCommodityUrl) {
        this.mCommodityUrl = mCommodityUrl;
    }


    /**
     * 商品ID
     */
    public String getCommodityId() {
        return mCommodityId;
    }

    public void setCommodityId(String mCommodityId) {
        this.mCommodityId = mCommodityId;
    }


    /**
     * 商品图片地址
     */
    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String mPictureUrl) {
        this.mPictureUrl = mPictureUrl;
    }


    /**
     * 卖家昵称
     */
    public String getSellerNick() {
        return mSellerNick;
    }

    public void setSellerNick(String mSellerNick) {
        this.mSellerNick = mSellerNick;
    }


    /**
     * 卖家ID
     */
    public String getSellerId() {
        return mSellerId;
    }

    public void setSellerId(String mSellerId) {
        this.mSellerId = mSellerId;
    }


    /**
     * 店铺名称
     */
    public String getShopTitle() {
        return mShopTitle;
    }

    public void setShopTitle(String mShopTitle) {
        this.mShopTitle = mShopTitle;
    }


    /**
     * 卖家类型，0表示集市，1表示商城
     */
    public int getSellerType() {
        return mSellerType;
    }

    public void setSellerType(int mSellerType) {
        this.mSellerType = mSellerType;
    }


    /**
     * 小图片地址列表
     */
    public List<String> getSmallImagesUrl() {
        return mSmallImagesUrl;
    }

    public void setSmallImagesUrl(List<String> mSmallImagesUrl) {
        this.mSmallImagesUrl = mSmallImagesUrl;
    }


    /**
     * 商品标题
     */
    public String getCommodityTitle() {
        return mCommodityTitle;
    }

    public void setCommodityTitle(String mCommodityTitle) {
        this.mCommodityTitle = mCommodityTitle;
    }


    /**
     * 月销量
     */
    public String getVolume() {
        return mVolume;
    }

    public void setVolume(String mVolume) {
        this.mVolume = mVolume;
    }


    /**
     * 券后价
     */
    public float getFinalPrice() {
        return mFinalPrice;
    }

    public void setFinalPrice(float mFinalPrice) {
        this.mFinalPrice = mFinalPrice;
    }

}
