package com.serioussoft.galex.testapp.dagger;


import android.app.Application;
import android.content.Context;

import com.serioussoft.galex.testapp.TestApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    Context context();

    void inject(TestApplication app);

}
