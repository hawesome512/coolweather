package com.hawesome.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.hawesome.coolweather.db.CoolweatherDB;
import com.hawesome.coolweather.model.City;
import com.hawesome.coolweather.model.Country;
import com.hawesome.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by haisheng on 2016/1/13.
 */
public class Utility {
    public synchronized static boolean handleProvinceResponse(CoolweatherDB coolweatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[]provinces=response.split(",");
            if(provinces!=null&&provinces.length>0){
                for(String province:provinces){
                    String[] infos=province.split("\\|");
                    Province p=new Province();
                    p.setProvinceCode(infos[0]);
                    p.setProvinceName(infos[1]);
                    coolweatherDB.saveProvince(p);
                }
                return true;
            }
        }
        return  false;
    }

    public synchronized static boolean handleCityResponse(CoolweatherDB coolweatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] cities=response.split(",");
            if(cities!=null&&cities.length>0){
                for(String city:cities){
                    String[] infos=city.split("\\|");
                    City c=new City();
                    c.setCityCode(infos[0]);
                    c.setCityName(infos[1]);
                    c.setProvinceId(provinceId);
                    coolweatherDB.saveCity(c);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountryResponse(CoolweatherDB coolweatherDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] countries=response.split(",");
            if(countries!=null&&countries.length>0){
                for(String country:countries){
                    String[] infos=country.split("\\|");
                    Country c=new Country();
                    c.setCountryCode(infos[0]);
                    c.setCountryName(infos[1]);
                    c.setCityId(cityId);
                    coolweatherDB.saveCountry(c);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) throws JSONException {
        JSONObject jsonObject=new JSONObject(response);
        JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
        String cityName=weatherInfo.getString("city");
        String cityId=weatherInfo.getString("cityid");
        String temp1=weatherInfo.getString("temp1");
        String temp2=weatherInfo.getString("temp2");
        String weatherDesp=weatherInfo.getString("weather");
        String publishTime=weatherInfo.getString("ptime");
        saveWeatherInfo(context,cityName,cityId,temp1,temp2,weatherDesp,publishTime);
    }

    private static void saveWeatherInfo(Context context, String cityName, String cityId, String temp1, String temp2, String weatherDesp, String publishTime) {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",cityId);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
