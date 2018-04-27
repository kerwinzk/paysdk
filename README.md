mobilepay

一个简单的Android移动支付集成库，主要支持微信支付和支付宝支付。
通过mobilepay可以减少繁琐的集成步骤，仅仅通过一句简单的代码就可以启动第三方支付。

---------------------------------------


paysdk使用步骤:
------------------------------------
1.初始化微信支付appid。（建议在项目主Application中初始化）

```
    // appid在微信开发者平台注册生成
    WxConfig.setWxAppid("appid12345");
```

2.添加支付注解
在主项目任意一个类中添加注解@PayMark。(建议在主Application中添加)

```
@PayMark
public class MyApplication extends Application {
    ...
}
```


3.启动支付

```

 PayClient.getInstance().pay(activity, 1, orderinfo, OnPayResultListener);

```


paysdk集成步骤
-------------------------------------
1.在主项目build.gradle中添加如下依赖

```
dependencies {
    annotationProcessor 'com.yesway.sdk:paycompiler:0.0.5'
    compile 'com.yesway.sdk:paysdk:0.0.5@aar'
}
```


2.在主项目build.gradle添加编译时参数

```
android {

    defaultConfig {

        // 省略......

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [applicationId: applicationId]
            }
        }

    }
}
```



