package com.serioussoft.galex.testapp.internals.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.serioussoft.galex.testapp.internals.api.ApiResponse;
import com.serioussoft.galex.testapp.internals.api.Resource;
import com.serioussoft.galex.testapp.internals.util.AppExecutors;


/*
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide
 * ResultType: Type for the Resource data
 * RequestType: Type for the API response
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<Resource<ResultType>> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<Resource<ResultType>> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        // We re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource,
                newData -> result.setValue(Resource.loading(newData)));

        result.addSource(apiResponse, response -> {

            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            //noinspection ConstantConditions
            if (response.isSuccessful()) {

                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            // We specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(),
                                    newData -> result.setValue(Resource.success(newData)))
                    );

                });

            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType response);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable Resource<ResultType> resData);

    @NonNull
    @MainThread
    protected abstract LiveData<Resource<ResultType>> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
