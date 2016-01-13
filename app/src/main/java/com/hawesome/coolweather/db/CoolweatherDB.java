package com.hawesome.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hawesome.coolweather.model.City;
import com.hawesome.coolweather.model.Country;
import com.hawesome.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haisheng on 2016/1/13.
 */
public class CoolweatherDB {
    public static final String DB_NAME="cool_weather";
    public static final int VERSION=1;
    private static CoolweatherDB coolweatherDB;
    private SQLiteDatabase sqLiteDatabase;

    private CoolweatherDB(Context context) {
        CoolweatherOpenHelper coolweatherOpenHelper=new CoolweatherOpenHelper(context,DB_NAME,null,VERSION);
        sqLiteDatabase=coolweatherOpenHelper.getWritableDatabase();
    }
    public synchronized static CoolweatherDB getInstance(Context context){
        if(coolweatherDB==null){
            coolweatherDB=new CoolweatherDB(context);
        }
        return coolweatherDB;
    }

    public void saveProvince(Province province){
        if(province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            sqLiteDatabase.insert("Province",null,values);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> provinceList=new ArrayList<Province>();
        Cursor cursor=sqLiteDatabase.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
               Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceList.add(province);
            }while (cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }
        return provinceList;
    }

    public void saveCity(City city){
        if(city!=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            sqLiteDatabase.insert("City",null,values);
        }
    }

    public List<City> loadCities(int provinceId){
        List<City> cityList=new ArrayList<City>();
        Cursor cursor=sqLiteDatabase.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                cityList.add(city);
            }while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return cityList;
    }

    public void saveCountry(Country country){
        if(country!=null){
            ContentValues values=new ContentValues();
            values.put("country_name",country.getCountryName());
            values.put("country_code",country.getCountryCode());
            values.put("city_id",country.getCityId());
            sqLiteDatabase.insert("Country",null,values);
        }
    }

    public List<Country> loadCountries(int cityId){
        List<Country> countryList=new ArrayList<Country>();
        Cursor cursor=sqLiteDatabase.query("Country",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Country country=new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                countryList.add(country);
            }while (cursor.moveToNext());
        }
        return countryList;
    }
}
