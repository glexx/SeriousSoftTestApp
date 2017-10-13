package com.serioussoft.galex.testapp;

import android.app.Application;

import com.serioussoft.galex.testapp.dagger.AppInjector;

/**
 * Created by Alexander Gavrikov.
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init(this);
    }
}
