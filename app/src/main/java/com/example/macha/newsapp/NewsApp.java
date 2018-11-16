package com.example.macha.newsapp;

import android.app.Application;
import android.content.Context;

public class NewsApp extends Application {
    private static Context context;

    public static Context getContext(){
        return  NewsApp.context;
    }

    public  void onCreate(){
        super.onCreate();
        NewsApp.context = getContext();
    }

}
