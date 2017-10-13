package com.serioussoft.galex.testapp.internals.api;

import android.arch.lifecycle.LiveData;
import java.util.List;
import retrofit2.http.GET;

public interface ApiService {
    @GET("json/movies.json")
    LiveData<ApiResponse<List<MovieDto>>> getMovies();
}
