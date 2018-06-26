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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.kerwin.paysdk.WxConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付
 *
 * @author zhangke
 */
class WxPay extends AbstractPay<BaseResp> {

    /**
     * 启动微信支付
     *
     * @param context
     * @param orderinfo
     */
    @Override
    public void onPay(Activity context, String orderinfo, OnPayResultListener listener) {
        this.mOnPayResultListener = listener;

        PayReq req = getPayReq(orderinfo);
        IWXAPI api = registerApp(context, req.appId);
        if (!checkWechatSupport(api)) {
            return;
        }

        api.sendReq(req);
    }

    /**
     * 注册app
     *
     * @param context
     * @param appId
     * @return
     */
    private IWXAPI registerApp(Activity context, String appId) {
        // 缓存appid，支付回调时需要使用到该appid
        WxConfig.setWxAppid(appId);

        //在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
        api.registerApp(appId);

        return api;
    }

    /**
     * 检查是否可以微信支付
     *
     * @param api
     * @return
     */
    public boolean checkWechatSupport(IWXAPI api) {
        if (api == null) {
            onResult(PAY_RESULT_ERROR, "微信注册失败，请检查appid");
            return false;
        }

        if (!api.isWXAppInstalled()) {
            onResult(PAY_RESULT_ERROR, "没有安装微信");
            return false;
        }

        if (!api.isWXAppSupportAPI()) {
            onResult(PAY_RESULT_ERROR, "该版本微信不支持微信支付");
            return false;
        }

        return true;
    }

    /**
     * 创建微信支付请求体
     *
     * @param orderinfo
     * @return
     * @throws JSONException
     */
    private PayReq getPayReq(String orderinfo) {
        PayReq req = new PayReq();
        try {
            JSONObject json = new JSONObject(orderinfo);
            req.appId = json.getString("appid");
            req.partnerId = json.getString("partnerid");
            req.prepayId = json.getString("prepayid");
            req.nonceStr = json.getString("noncestr");
            req.timeStamp = json.getString("timestamp");
            req.packageValue = json.getString("package");
            req.sign = json.getString("sign");
            // req.extData = "";
            return req;
        } catch (Exception e) {

        }
        return req;
    }

    /**
     * 处理支付结果
     *
     * @param result
     */
    @Override
    public void onResult(BaseResp result) {
        String response = JSON.toJSONString(result);
        int errCode = result.errCode;

        int code;
        if (errCode == 0) {
            // 成功
            code = PAY_RESULT_SUCCESS;
        } else if (errCode == -2) {
            // 取消
            code = PAY_RESULT_CANCEL;
        } else {
            // 异常
            code = PAY_RESULT_ERROR;
        }

        onResult(code, response);
        instance = null;
    }

    private static WxPay instance;

    private WxPay() {

    }

    public static WxPay getInstace() {
        if (instance == null) {
            instance = new WxPay();
        }
        return instance;
    }
}
