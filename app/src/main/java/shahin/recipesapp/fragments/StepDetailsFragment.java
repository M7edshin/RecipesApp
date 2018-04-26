package shahin.recipesapp.fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import shahin.recipesapp.R;
import shahin.recipesapp.models.Step;

import static shahin.recipesapp.utilities.Constants.PLAYER_CURRENT_POSITION_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.PLAYER_STATE_KEY;
import static shahin.recipesapp.utilities.Constants.STEP_DETAILS_PARC_KEY;

public class StepDetailsFragment extends Fragment{

    @BindView(R.id.tv_short_description) TextView tv_short_description;
    @BindView(R.id.tv_description) TextView tv_description;
    @BindView(R.id.exo_player_view) SimpleExoPlayerView exo_player_view;
    @BindView(R.id.iv_thumbnail) ImageView iv_thumbnail;

    private Step step;

    SimpleExoPlayer exoPlayer;

    private String videoUrl;

    private long exoPlayerCurrentPosition;
    private boolean exoPlayerCurrentState;

    public StepDetailsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());

        if(getArguments().containsKey(STEP_DETAILS_PARC_KEY)){
            step = getArguments().getParcelable(STEP_DETAILS_PARC_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState!=null){
            exoPlayerCurrentPosition = savedInstanceState.getLong(PLAYER_CURRENT_POSITION_STATE_KEY);
            exoPlayerCurrentState = savedInstanceState.getBoolean(PLAYER_STATE_KEY);
        }


        tv_short_description.setText(step.getShortDescription());
        tv_description.setText(step.getDescription());

        if(!step.getVideoURL().isEmpty()&&step.getVideoURL()!=null){
            iv_thumbnail.setVisibility(View.GONE);
            videoUrl = step.getVideoURL();
            playMedia();
        }else{
            exo_player_view.setVisibility(View.GONE);
            if(step.getThumbnailURL().isEmpty()||step.getThumbnailURL()==null){
                iv_thumbnail.setImageResource(R.mipmap.ic_launcher);
            }else{
                Glide.with(getActivity())
                        .load(step.getThumbnailURL())
                        .error(R.mipmap.ic_recipe)
                        .into(iv_thumbnail);
            }
        }



        return rootView;
    }

    private void playMedia(){

        exo_player_view.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.no_vide_available));

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(null);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        exoPlayer.seekTo(exoPlayerCurrentPosition);

        exo_player_view.requestFocus();
        exo_player_view.setUseController(true);
        exo_player_view.setPlayer(exoPlayer);
        exo_player_view.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        String userAgent = Util.getUserAgent(getActivity(), "ExampleExoPlayer");
        DataSource.Factory dataSource = new DefaultDataSourceFactory(getActivity(), userAgent);

        Uri file = Uri.parse(videoUrl);

        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource(file, dataSource,
                new DefaultExtractorsFactory() ,null, null);
        exoPlayer.prepare(extractorMediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        exoPlayerCurrentPosition = exoPlayer.getCurrentPosition();
        outState.putLong(PLAYER_CURRENT_POSITION_STATE_KEY, exoPlayerCurrentPosition);

        exoPlayerCurrentState = exoPlayer.getPlayWhenReady();
        outState.putBoolean(PLAYER_STATE_KEY,exoPlayerCurrentState);

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
