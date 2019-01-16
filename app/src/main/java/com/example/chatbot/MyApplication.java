package com.example.chatbot;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import org.litepal.LitePal;

/**
 * Created by Ryan on 2018/6/13.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        LitePal.initialize(this);
    }
}
