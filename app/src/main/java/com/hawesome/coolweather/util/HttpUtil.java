package com.hawesome.coolweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener httpCallbackListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream= connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    if(httpCallbackListener!=null){
                        httpCallbackListener.onFinish(stringBuilder.toString());
                    }
                }catch (Exception e){
                    if(httpCallbackListener!=null) {
                        httpCallbackListener.onError(e);
                    }
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
