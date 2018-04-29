package shahin.recipesapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import shahin.recipesapp.R;
import shahin.recipesapp.models.Step;

import static shahin.recipesapp.utilities.Constants.PLAYER_CURRENT_WINDOW_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.PLAYER_PLAYBACK_POSITION_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.PLAYER_WHEN_READY_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.STEP_DETAILS_PARC_KEY;

public class StepDetailsFragment extends Fragment{

    @BindView(R.id.tv_short_description) TextView tv_short_description;
    @BindView(R.id.tv_description) TextView tv_description;
    @BindView(R.id.exo_player_view) SimpleExoPlayerView exo_player_view;
    @BindView(R.id.iv_thumbnail) ImageView iv_thumbnail;

    private Step step;

    private SimpleExoPlayer exoPlayer;

    private String videoUrl = "";

    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public StepDetailsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());

        Log.v("Tag", "MyLogs: OnCreate Method is launched");

        if(getArguments().containsKey(STEP_DETAILS_PARC_KEY)){
            step = getArguments().getParcelable(STEP_DETAILS_PARC_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        Log.v("Tag", "MyLogs: OnCreateView Method is launched");

        tv_short_description.setText(step.getShortDescription());
        tv_description.setText(step.getDescription());

        if(!TextUtils.isEmpty(step.getVideoURL())&&step.getVideoURL()!=null){
            iv_thumbnail.setVisibility(View.GONE);
            videoUrl = step.getVideoURL();
        }else{
            exo_player_view.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(step.getThumbnailURL())||step.getThumbnailURL()==null){
                iv_thumbnail.setImageResource(R.mipmap.ic_recipe);
            }else{
                Glide.with(getActivity())
                        .load(step.getThumbnailURL())
                        .error(R.mipmap.ic_recipe)
                        .into(iv_thumbnail);
            }
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("Tag", "MyLogs: onViewCreated Method is launched");
        if(savedInstanceState!=null){
            playbackPosition = savedInstanceState.getLong(PLAYER_PLAYBACK_POSITION_STATE_KEY);
            playWhenReady = savedInstanceState.getBoolean(PLAYER_WHEN_READY_STATE_KEY);
            currentWindow = savedInstanceState.getInt(PLAYER_CURRENT_WINDOW_STATE_KEY);
        }
    }


    private void initializePlayer() {
        if(exoPlayer==null){
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            exo_player_view.setPlayer(exoPlayer);

            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
        }

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        exoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("StepDetailsFragment")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("Tag", "MyLogs: OnStart Method is launched");

        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v("Tag", "MyLogs: OnResume Method is launched");

        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            Log.e("Tag", "MyLogs: OnPause Method is launched");
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.v("Tag", "MyLogs: OnStop Method is launched");

        if (Util.SDK_INT > 23) {
            releasePlayer();
            Log.v("Tag", "MyLogs: ExoPlayer is released");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.v("Tag", "MyLogs: onSaveInstanceState Method is launched");

        playbackPosition = exoPlayer.getCurrentPosition();
        playWhenReady = exoPlayer.getPlayWhenReady();
        currentWindow = exoPlayer.getCurrentWindowIndex();

        outState.putLong(PLAYER_PLAYBACK_POSITION_STATE_KEY, playbackPosition);
        outState.putBoolean(PLAYER_WHEN_READY_STATE_KEY,playWhenReady);
        outState.putInt(PLAYER_CURRENT_WINDOW_STATE_KEY,currentWindow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
        }
        return true;
    }
}

