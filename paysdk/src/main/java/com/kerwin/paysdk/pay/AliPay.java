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
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝支付
 *
 * @author zhangke
 */
class AliPay extends AbstractPay<AliPayResult> {


    private Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);

                    onResult(payResult);


                    break;

                default:
                    break;
            }

        }

    };

    /**
     * 启动支付宝支付
     *
     * @param activity
     * @param orderInfo 订单信息
     */
    @Override
    public void onPay(final Activity activity, final String orderInfo, OnPayResultListener listener) {
        this.mOnPayResultListener = listener;


        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                /**
                 * payV2第二个参数表示是否显示调起支付前加载页面
                 */
                Map<String, String> result = alipay.payV2(orderInfo, false);

                Message msg = new Message();
                msg.what = 100;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 处理支付结果
     *
     * @param result
     */
    @Override
    public void onResult(AliPayResult result) {
        String resultStatus = result.getResultStatus();
        String response = JSON.toJSONString(result);

        int code;
        /*
         * resultStatus为9000则代表支付成功 该笔订单真实的支付结果，需要依赖服务端的异步通知。
		 */
        if (TextUtils.equals(resultStatus, "9000")) {
            // 成功
            code = PAY_RESULT_SUCCESS;
        } else if (TextUtils.equals(resultStatus, "6001")) {
            // 取消
            code = PAY_RESULT_CANCEL;
        } else {
            // 异常
            code = PAY_RESULT_ERROR;
        }
        onResult(code, response);
    }

}
