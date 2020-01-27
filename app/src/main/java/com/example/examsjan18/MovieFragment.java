package com.example.examsjan18;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class MovieFragment extends Fragment {
    MovieAdapter movieAdapter;

    public MovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchArticles();
    }

    private void fetchArticles(){
        FetchMoviesTask fetchMovieTask = new FetchMoviesTask(movieAdapter);
        fetchMovieTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getContext(), new ArrayList<Movie>());
        ListView moviesListView = (ListView)rootView.findViewById(R.id.listview_movies);

        moviesListView.setAdapter(movieAdapter);

        //Extra bonus to whoever manages to open the url of the article with the webviewer

        return rootView;
    }
}
