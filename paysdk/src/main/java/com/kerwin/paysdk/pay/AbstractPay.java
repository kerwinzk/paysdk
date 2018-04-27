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
 * 支付接口
 *
 * @author zhangke
 */
abstract class AbstractPay<T> implements IPay {
    /**
     * 支付成功
     */
    public static final int PAY_RESULT_SUCCESS = 0;
    /**
     * 支付错误
     */
    public static final int PAY_RESULT_ERROR = -1;
    /**
     * 支付取消
     */
    public static final int PAY_RESULT_CANCEL = -2;

    /**
     * 客户端回调方法
     */
    OnPayResultListener mOnPayResultListener;

    /**
     * 支付回调结果统一
     *
     * @param result
     */
    abstract void onResult(T result);

    /**
     * 支付回调统一处理
     *
     * @param code
     * @param msg
     */
    void onResult(int code, String msg) {
        if (mOnPayResultListener == null) {
            return;
        }

        if (code == PAY_RESULT_SUCCESS) {
            mOnPayResultListener.onPaySuccess(code, msg);
        } else {
            mOnPayResultListener.onPayError(code, msg);
        }
    }

}
