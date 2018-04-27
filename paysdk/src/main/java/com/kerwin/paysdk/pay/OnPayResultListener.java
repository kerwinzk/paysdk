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

/**
 * 支付结果处理
 *
 * @author zhangke
 */
public interface OnPayResultListener {
    /**
     * 支付成功
     *
     * @param resultCode
     * @param response
     */
    void onPaySuccess(int resultCode, String response);

    /**
     * 支付异常或失败
     *
     * @param errCode -1：失败    -2：取消
     * @param errMsg
     */
    void onPayError(int errCode, String errMsg);
}