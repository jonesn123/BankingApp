package com.hyunyong.myapplication.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.databinding.FragmentViewRecipeBinding;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ViewRecipeFragment extends Fragment {
    public static final String ARG_DESCRIPTION = "description";
    public static final String ARG_VIDEO_URL = "videoURL";
    public static final String THUMBNAIL_URL = "thumbnailURL";

    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;


    public ViewRecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDescription = getArguments().getString(ARG_DESCRIPTION);
            mVideoUrl = getArguments().getString(ARG_VIDEO_URL);
            mThumbnailUrl = getArguments().getString(THUMBNAIL_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentViewRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_recipe, container, false);
        binding.setDescription(mDescription);
        binding.setVideoUrl(mVideoUrl);
        binding.setThumbnailUrl(mThumbnailUrl);
        return binding.getRoot();
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if(imageUrl == null || "".equals(imageUrl)) return;
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).load(imageUrl).into(imageView);
    }

    @BindingAdapter("videoUrl")
    public static void loadVideo(PlayerView playerView, String videoUrl) {
        if (videoUrl == null || "".equals(videoUrl)) {
            playerView.setVisibility(View.GONE);
            return;
        }

        Context context = playerView.getContext();
        if (context == null) {
            playerView.setVisibility(View.GONE);
            return;
        }
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "BankingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
        player.prepare(videoSource);
        playerView.setPlayer(player);
    }
    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

}
