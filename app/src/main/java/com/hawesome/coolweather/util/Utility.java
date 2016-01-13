package com.hawesome.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.hawesome.coolweather.db.CoolweatherDB;
import com.hawesome.coolweather.model.City;
import com.hawesome.coolweather.model.Country;
import com.hawesome.coolweather.model.Province;

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
}
