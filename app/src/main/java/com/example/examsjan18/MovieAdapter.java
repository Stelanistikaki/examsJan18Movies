package com.example.examsjan18;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, ArrayList<Movie> objects) {
        super(context, 0, objects);
        this.movies = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

       Movie movie = movies.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.list_item_movie, null);

        TextView title = rowView.findViewById(R.id.titleTxt);
        TextView original_title = rowView.findViewById(R.id.originalTitleTxt);
        TextView overview = rowView.findViewById(R.id.overviewTxt);
        TextView release_date = rowView.findViewById(R.id.releaseDateTxt);
        TextView vote = rowView.findViewById(R.id.voteTxt);
        ImageView poster = rowView.findViewById(R.id.posterImg);

        title.setText(movie.getTitle());
        original_title.setText(movie.getOriginalTitle());
        overview.setText(movie.getOverview());
        release_date.setText(movie.getReleaseDate());
        vote.setText(movie.getVote());

        Picasso.get().load("https://image.tmdb.org/t/p/w200/"+ movie.getPosterPath()).into(poster);

        return  rowView;
    }


}
