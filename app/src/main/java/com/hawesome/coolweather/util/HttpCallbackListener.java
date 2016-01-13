package com.hawesome.coolweather.util;

/**
 * Created by haisheng on 2016/1/13.
 */
public interface HttpCallbackListener{
    void onFinish(String response);
    void onError(Exception e);
}
