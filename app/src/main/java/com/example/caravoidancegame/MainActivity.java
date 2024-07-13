package com.example.caravoidancegame;

import android.app.Application;

import com.example.caravoidancegame.Util.MySP;

public class MainActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySP.init(this);
    }
}
