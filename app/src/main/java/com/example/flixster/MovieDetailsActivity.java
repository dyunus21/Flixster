package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.modules.GlideApp;
import com.example.flixster.modules.Movie;

import org.json.JSONArray;
import org.parceler.Parcels;
import org.w3c.dom.Text;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));


        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);

        // set poster
        String imageUrl;
        int placeholder;
        // if phone is in landscape --> backdrop else poster
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
            placeholder = R.drawable.flicks_movie_placeholder;
        }
        else {
            imageUrl = movie.getPosterPath();
            placeholder = R.drawable.flicks_backdrop_placeholder;
        }
        GlideApp.with(this)
                .load(imageUrl)
                .placeholder(placeholder)
                .transform(new RoundedCorners(10))
                .into(binding.ivPoster);

        // Trailer backdrop setup
        GlideApp.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(binding.vTrailer);
    }

    // Return to home screen when Return_To_Home button is clicked
    public void onReturn(View view) {
        finish();
    }

    public void onVideoClick(View view) {
        String videoId = (String) movie.getVideoId();
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        intent.putExtra("movie", Parcels.wrap(movie));
        startActivity(intent);
    }
}