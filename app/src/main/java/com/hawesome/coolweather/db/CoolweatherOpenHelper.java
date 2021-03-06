package com.hawesome.coolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haisheng on 2016/1/13.
 */
public class CoolweatherOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_PROVINCE="create table Province(id integer primary key autoincrement,province_name text,province_code text)";
    public static final String CREATE_CITY="create table City(id integer primary key autoincrement,city_name text,city_code text,province_id integer)";
    public static final String CREATE_COUNTRY="create table Country(id integer primary key autoincrement,country_name,country_code,city_id integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public CoolweatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
