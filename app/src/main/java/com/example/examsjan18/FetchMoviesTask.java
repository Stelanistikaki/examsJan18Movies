package com.example.examsjan18;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMoviesTask extends AsyncTask<String,Void, ArrayList<Movie>> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private MovieAdapter movieAdapter;

    public FetchMoviesTask(MovieAdapter movieAdapter){
        this.movieAdapter = movieAdapter;

    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String articleJsonString;

        try {

            Uri builtUri = Uri.parse("https://api.themoviedb.org/3/discover/movie?with_genres=28&primary_release_year=2017&language=el&api_key=1c90b8a964d83f02d811adb3b4218bb3");


            URL url = new URL(builtUri.toString());

            Log.i("TAG", url.toString());


            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            articleJsonString = buffer.toString();
            Log.i("TAG", "drg");

            return  getMoviesFromJSON(articleJsonString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attempting to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if(movies.size() > 0){
            this.movieAdapter.clear();
            for(Movie movie : movies) {
                movieAdapter.add(movie);
            }
        }
    }



    private ArrayList<Movie> getMoviesFromJSON(String responseJsonStr) throws JSONException {

        ArrayList<Movie> movies = new ArrayList<>();

        try{
            JSONObject response = new JSONObject(responseJsonStr);

            JSONArray resultsArray =  response.getJSONArray("results");

            Log.i("TAG", "getArticlesFromJSON: ");

            for(int i=0; i< resultsArray.length(); i++){
                JSONObject articleJSON = resultsArray.getJSONObject(i);
                Log.i("TAG", String.valueOf(articleJSON));

                Movie movie = new Movie();
                movie.setTitle(articleJSON.getString("title"));
                movie.setOriginalTitle(articleJSON.getString("original_title"));
                movie.setOverview(articleJSON.getString("overview"));
                movie.setReleaseDate(articleJSON.getString("release_date"));
                movie.setVote(articleJSON.getInt("vote_average"));
                movie.setPosterPath(articleJSON.getString("poster_path"));

                movies.add(movie);

            }

            Log.d(LOG_TAG, "Movies Fetching Complete. " + movies.size() + "articles inserted");

            return  movies; //actually here should return articles

        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return  movies;
        }

    }
}