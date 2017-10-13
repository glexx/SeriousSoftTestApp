package com.serioussoft.galex.testapp.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.serioussoft.galex.testapp.internals.api.MovieDto;
import com.serioussoft.galex.testapp.internals.api.Resource;
import com.serioussoft.galex.testapp.internals.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MoviesViewModel extends ViewModel {

    private final MoviesRepository repository;

    @Inject
    MoviesViewModel(MoviesRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<MovieDto>>> getMovies() {
        return repository.loadTiles();
    }
}
