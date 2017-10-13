package com.serioussoft.galex.testapp.internals.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.serioussoft.galex.testapp.internals.api.ApiResponse;
import com.serioussoft.galex.testapp.internals.api.ApiService;
import com.serioussoft.galex.testapp.internals.api.MovieDto;
import com.serioussoft.galex.testapp.internals.api.Resource;
import com.serioussoft.galex.testapp.internals.util.AppExecutors;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.internal.Utils;


@Singleton
public class MoviesRepository {

    private static final String TAG = "TilesRepository";

    private static final String DISK_CACHE_TAG = "tiles";

    private final Context context;
    private final ApiService api;
    private final AppExecutors appExecutors;
//    private final DiskCache diskCache;
    private LiveData<Resource<List<MovieDto>>> memCachedData;

    @Inject
    MoviesRepository(Context context, AppExecutors appExecutors, ApiService api/*, DiskCache diskCache*/) {
        this.context = context;
        this.appExecutors = appExecutors;
        this.api = api;
//        this.diskCache = diskCache;
    }

    public LiveData<Resource<List<MovieDto>>> loadTiles() {
        return new NetworkBoundResource<List<MovieDto>, List<MovieDto>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<MovieDto> response) {
//                diskCache.saveDataToCache(DISK_CACHE_TAG, response);
                memCachedData = null;
            }

            @Override
            protected boolean shouldFetch(@Nullable Resource<List<MovieDto>> resData) {

//                boolean isDataSavingFeatureOn = Utils.isOnDataSavingMode();
//                boolean isOnMobileData = ConnectionDetector.isConnectedToMobile(context);
//
//                int lifetimeTimeoutHours;
//
//                if (isDataSavingFeatureOn && isOnMobileData) {
//                    // Data saving feature
//                    lifetimeTimeoutHours = AirfindConfigurationSdk.getIntegerParameter(
//                            Constants.PARAM_DS_FEATURED_AD_TILE_REFRESH_FREQUENCY,
//                            Constants.PARAM_DS_FEATURED_AD_TILE_REFRESH_FREQUENCY_DEFAULT);
//                } else {
//                    lifetimeTimeoutHours = AirfindConfigurationSdk.getIntegerParameter(
//                            Constants.PARAM_FEATURED_AD_TILE_REFRESH_FREQUENCY,
//                            Constants.PARAM_FEATURED_AD_TILE_REFRESH_FREQUENCY_DEFAULT);
//                }
//
//                // Skip fetching if data is not too old
//                if (resData != null && resData.timeStamp != null){
//                    long passedHours = Utils.milliSecToHours(System.currentTimeMillis() - resData.timeStamp);
//                    if (passedHours < lifetimeTimeoutHours){
//                        return false;
//                    }
//                }

                return true;
            }

            @NonNull
            @Override
            protected LiveData<Resource<List<MovieDto>>> loadFromDb() {
//                Resource<TilesResponse> cachedResponse = null;
//                if (memCachedTiles == null) {
//                    try {
//                        cachedResponse = diskCache.loadDataFromCache(DISK_CACHE_TAG, TilesResponse.class);
//                    } catch (Exception e) {
//                        Logger.e(TAG, e);
//                    }
//                }
//                else{
//                    cachedResponse = memCachedTiles.getValue();
//                }
//
//                MutableLiveData<Resource<TilesResponse>> result = (new MutableLiveData<>());
//                result.setValue(Resource.success(cachedResponse));
//                memCachedTiles = result;
//                return result;
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<MovieDto>>> createCall() {
//                String apiKey = AirfindConfigurationSdk.getParameter(Constants.PARAM_API_KEY, Constants.PARAM_API_KEY_DEFAULT);
//                String affiliateId = AirfindConfigurationSdk.getParameter(Constants.PARAM_AFFILIATED_ID);
//                String subAff = AirfindConfigurationSdk.getParameter(Constants.PARAM_SUB_AFFILIATE_ID);
//                int limit = AirfindConfigurationSdk.getIntegerParameter(Constants.PARAM_AD_TILE_LIMIT, Constants.PARAM_AD_TILE_LIMIT_DEFAULT);
//
//                return airfindApi.getTiles(apiKey, affiliateId, subAff, limit);
                return null;
            }
        }.asLiveData();
    }
}
