package com.hawesome.coolweather.util;

import org.json.JSONException;

/**
 * Created by haisheng on 2016/1/13.
 */
public interface HttpCallbackListener{
    void onFinish(String response) throws JSONException;
    void onError(Exception e);
}
