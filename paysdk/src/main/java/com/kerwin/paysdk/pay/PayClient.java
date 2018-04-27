/*--------------------------------------------------
 * Copyright (C) 2015 The Android Y-CarPlus Project
 *                http://www.yesway.cn/
 * 创建时间：2017年3月29日
 * 内容说明：
 * 
 * 编号                日期                     担当者             内容                  
 * -------------------------------------------------
 *
 * -------------------------------------------------- */
package com.kerwin.paysdk.pay;

import android.app.Activity;
import android.text.TextUtils;

/**
 * 处理支付回调
 *
 * @author zhangke
 */
public class PayClient {

    private static PayClient instance;

    private PayClient() {
    }

    public static PayClient getInstance() {
        if (null == instance) {
            synchronized (PayClient.class) {
                if (null == instance) {
                    instance = new PayClient();
                }
            }
        }
        return instance;
    }

    /**
     * 开始支付
     *
     * @param activity  上下文
     * @param paymethod 1：支付宝支付 2：微信支付
     * @param orderinfo 订单信息：1、支付宝订单信息未服务器加签字符串 2、微信订单信息为json串
     * @param listener  支付回调接口
     */
    public void pay(Activity activity, int paymethod, String orderinfo, OnPayResultListener listener) {
        if ((paymethod != 1 && paymethod != 2) || TextUtils.isEmpty(orderinfo)) {
            throw new IllegalArgumentException("illegal argument：current paymethod type is "
                    + paymethod + "; orderinfo is " + orderinfo);
        }

        PayFactory.createPay(paymethod).onPay(activity, orderinfo, listener);
    }

}
