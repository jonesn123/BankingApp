package com.hyunyong.myapplication.view;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.databinding.FragmentViewRecipeBinding;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.dao.StepDao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import static androidx.navigation.fragment.NavHostFragment.findNavController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ViewRecipeFragment extends Fragment {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "video_url";
    public static final String THUMBNAIL_URL = "thumbnail_url";

    private int mID;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;
    private StepDao mStepDao;
    private PlayerView mPlayerView;

    public ViewRecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mID = getArguments().getInt(ID);
            mDescription = getArguments().getString(DESCRIPTION);
            mVideoUrl = getArguments().getString(VIDEO_URL);
            mThumbnailUrl = getArguments().getString(THUMBNAIL_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentViewRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_recipe, container, false);
        mStepDao = AppDataBase.getDatabase(getContext()).stepDao();
        binding.setId(mID);
        binding.setDescription(mDescription);
        binding.setVideoUrl(mVideoUrl);
        binding.setThumbnailUrl(mThumbnailUrl);
        binding.setCount(mStepDao.getCount());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlayerView = view.findViewById(R.id.player_view);
        RecipeActivity recipeActivity = (RecipeActivity) getActivity();
        ActionBar actionBar = recipeActivity.getSupportActionBar();
        if (!recipeActivity.mTwoPane && recipeActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);

            if (actionBar != null) {
                actionBar.hide();
            }
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width = params.WRAP_CONTENT;
            params.height = params.WRAP_CONTENT;
            mPlayerView.setLayoutParams(params);
            if (actionBar != null) {
                actionBar.show();
            }
        }
        Button prev_btn = view.findViewById(R.id.prev_step);
        prev_btn.setOnClickListener(v -> {
            int prev_id = mID - 1;
            if (prev_id < 0) return;
            Step step = mStepDao.getStep(prev_id);
            Bundle args = new Bundle();
            args.putInt(ViewRecipeFragment.ID, step.getId());
            args.putString(ViewRecipeFragment.DESCRIPTION, step.getDescription());
            args.putString(ViewRecipeFragment.VIDEO_URL, step.getVideoURL());
            args.putString(ViewRecipeFragment.THUMBNAIL_URL, step.getThumbnailURL());
            if(recipeActivity.mTwoPane) {
                ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
                viewRecipeFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.recipe_contents, viewRecipeFragment).commit();
            } else {
                findNavController(this).navigate(R.id.view_recipe, args);
            }
        });

        Button next_btn = view.findViewById(R.id.next_step);
        next_btn.setOnClickListener(v -> {
            int next_id = mID + 1;
            if (next_id >= mStepDao.getCount()) return;

            Step step = mStepDao.getStep(next_id);

            Bundle args = new Bundle();
            args.putInt(ViewRecipeFragment.ID, step.getId());
            args.putString(ViewRecipeFragment.DESCRIPTION, step.getDescription());
            args.putString(ViewRecipeFragment.VIDEO_URL, step.getVideoURL());
            args.putString(ViewRecipeFragment.THUMBNAIL_URL, step.getThumbnailURL());
            if (recipeActivity.mTwoPane) {
                ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
                viewRecipeFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.recipe_contents, viewRecipeFragment).commit();
            } else {
                findNavController(this).navigate(R.id.view_recipe, args);
            }
        });
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl == null || "".equals(imageUrl)) return;
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

}
