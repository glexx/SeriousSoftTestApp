package com.serioussoft.galex.testapp.dagger;


import com.serioussoft.galex.testapp.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
