package com.serioussoft.galex.testapp.internals.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alexander Gavrikov.
 */

//a generic class that describes a data with a status
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    @Nullable
    public final Long timeStamp;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timeStamp = null;
    }

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable Long timeStamp) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    private Resource(@NonNull Status status, @Nullable Resource<T> resData) {
        this.status = status;
        T data = null;
        String message = null;
        Long timeStamp = null;

        if (resData != null){
            data = resData.data;
            timeStamp = resData.timeStamp;
            message = resData.message;
        }

        this.data = data;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> success(@NonNull T data, @NonNull Long timeStamp) {
        return new Resource<>(Status.SUCCESS, data, null, timeStamp);
    }

    public static <T> Resource<T> success(Resource<T> resData) {
        return new Resource<>(Status.SUCCESS, resData);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> error(String msg, Resource<T> resData) {
        return new Resource<>(Status.ERROR, resData);
    }

    public static <T> Resource<T> error() {
        return new Resource<>(Status.ERROR, null);
    }

    public static <T> Resource<T> loading(Resource<T> resData) {
        return new Resource<>(Status.LOADING, resData);
    }

    public boolean isFinished(){
        return status != Status.LOADING;
    }

    public boolean isSuccessful(){
        return status == Status.SUCCESS;
    }

    public boolean isReady(){
        return status == Status.SUCCESS && data != null;
    }
}