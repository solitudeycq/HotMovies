package com.solitudeycq.hotmovies.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.bean.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends Activity {
    private TextView mMovieName;
    private ImageView mMoviePic;
    private TextView mReleaseYear;
    private TextView mMovieRating;
    private TextView mMovieOverview;
    private TextView mMovieOriginalTitle;
    private TextView mVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie_detail);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        Intent i = getIntent();
        if(i!=null){
            Movie m = (Movie) i.getExtras().get("movie");
            if(m!=null){
                mMovieName.setText(m.getName());
                mReleaseYear.setText(((m.getReleaseDate()).split("-"))[0]);
                mMovieRating.setText(m.getRating()+"/10");
                if(m.getIntroduction()!=null&&m.getIntroduction().length()!=0){
                    mMovieOverview.setText(m.getIntroduction());
                }else{
                    Toast.makeText(MovieDetailActivity.this,getString(R.string.toast_no_introduction),Toast.LENGTH_SHORT).show();
                    mMovieOverview.setText(getString(R.string.default_movie_overview));
                }
                mMovieOriginalTitle.setText(m.getOriginalTitle());
                if(m.getVoteCount()!=null&&m.getVoteCount().length()!=0){
                    mVoteCount.setText(m.getVoteCount());
                }
                loadPic(m);
            }
        }
    }
    private void initView(){
        mMovieName = (TextView) findViewById(R.id.movie_name_txt);
        mMoviePic = (ImageView) findViewById(R.id.movie_pic);
        mReleaseYear = (TextView) findViewById(R.id.releaseyear_txt);
        mMovieRating = (TextView) findViewById(R.id.rating_txt);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieOverview.setMovementMethod(ScrollingMovementMethod.getInstance());
        mMovieOriginalTitle = (TextView) findViewById(R.id.detail_original_title);
        mVoteCount = (TextView) findViewById(R.id.detail_vote_count);
    }
    private void loadPic(Movie m){
        Picasso.with(this)
                .load(m.getPicture())
                .placeholder(R.drawable.default_pic)
                .into(mMoviePic, new Callback() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onError() {
                        Toast.makeText(MovieDetailActivity.this,getString(R.string.picasso_error_loadingPic),Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
