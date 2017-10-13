package com.serioussoft.galex.testapp.dagger;

import android.content.Context;
import android.support.annotation.NonNull;


import com.serioussoft.galex.testapp.BuildConfig;
import com.serioussoft.galex.testapp.Constants;
import com.serioussoft.galex.testapp.internals.util.LoggingInterceptor;

import java.util.concurrent.TimeUnit;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {
        ViewModelModule.class,
        ActivityModule.class})

class AppModule {

    private Context context;

    AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context.getApplicationContext();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.readTimeout(Constants.REST_API_TIMEOUT_SEC, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(Constants.REST_API_TIMEOUT_SEC, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(Constants.REST_API_TIMEOUT_SEC, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(new LoggingInterceptor());
        }

        return clientBuilder.build();
    }

//    @Singleton
//    @Provides
//    AirfindApiService provideAirfindApiService(OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Constants.BACKEND_BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
//                .build()
//                .create(AirfindApiService.class);
//    }
}
