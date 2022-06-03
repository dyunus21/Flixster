package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.modules.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    String videoURL;
    String TAG = "MovieTrailer";
    String videoId = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        Log.i(TAG, movie.getMovieId().toString());
        //videoURL = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US", movie.getMovieId());
        videoURL = "https://api.themoviedb.org/3/movie/" + movie.getMovieId() +"/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
        Log.d(TAG,videoURL);
        //Log.i(TAG, movie.getMovieId().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(videoURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: " + results.toString());
                    if(results.length() > 0) {
                        videoId = results.getJSONObject(0).getString("key");
                        Log.d(TAG,"Key: "+ videoId);

                        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

                        // initialize with API key stored in secrets.xml
                        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer youTubePlayer, boolean b) {
                                // do any work here to cue video, play video, etc.

                                youTubePlayer.cueVideo(videoId);
                                Log.d(TAG,videoId);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {
                                // log the error
                                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                            }
                        });
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception");
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });

        // temporary test video id -- TODO replace with movie trailer video id
        //final String videoId = "tKodtNFpzBA";
//        final String videoId = (String) Parcels.unwrap(getIntent().getParcelableExtra("video_id"));
//        Log.d("Trailer" ,videoId + "key: " + getString(R.string.youtube_api_key));

        // resolve the player view from the layout
/*        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.

                youTubePlayer.cueVideo(videoId);
                Log.d(TAG,videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });*/
    }
}