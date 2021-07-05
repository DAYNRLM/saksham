package com.shaksham.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class MyLanguage extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setLocaleFa(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    public MyLanguage getLanguage() {

        return new MyLanguage();
    }
}
