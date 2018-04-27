package com.kerwin.paysdk.pay;

/**
 * Created by zhangke on 2018/4/16.
 */

class PayFactory {
    /**
     * 支付宝
     */
    public static final int ALIPAY = 1;
    /**
     * 微信
     */
    public static final int WXPAY = 2;

    public static IPay createPay(int payType) {

        IPay iPay = null;
        if (payType == ALIPAY) {
            // 支付宝支付
            iPay = new AliPay();
        } else if (payType == WXPAY) {
            // 微信支付
            iPay = WxPay.getInstace();
        }
        return iPay;
    }
}
