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

/**
 * 支付接口
 *
 * @author zhangke
 */
interface IPay {

    /**
     * 启动支付
     *
     * @param activity
     * @param orderInfo
     * @param listener
     */
    void onPay(Activity activity, String orderInfo, OnPayResultListener listener);

}
