package com.example.flixster.modules;

import android.content.Intent;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel // indicates class is Parcelable
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;
    Integer movieId;
//    String videoURL;
    String videoId;
    public static String TAG = "Movie";

    // constructors
    // no-arg, empty constructor required for Parceler
    public Movie() {}

    public Integer getMovieId() {
        return movieId;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");


//        videoURL = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US",movieId);
//        Log.d(TAG,videoURL);
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(videoURL, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG,"onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONArray results = jsonObject.getJSONArray("results");
//                    Log.i(TAG,"Results: " + results.toString());
//                    if(results.length() > 0) {
//                        videoId = results.getJSONObject(0).getString("key");
//                        Log.d(TAG,"Key: "+ videoId);
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "Hit JSON exception");
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG,"onFailure");
//            }
//        });

    }

    // Gets list of movies
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i<movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }


    // Gets video
//    public static Integer getVideo() {
//
//    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

//    public String getVideoURL() {
//        return videoURL;
//    }

    public String getVideoId() {
        return videoId;
    }

//    public JSONArray getGenre() {
//        return genre;
//    }

//    public String getLanguage() { return language; }
}
