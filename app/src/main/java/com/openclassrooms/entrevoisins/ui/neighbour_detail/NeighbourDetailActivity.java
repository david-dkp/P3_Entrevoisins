package com.openclassrooms.entrevoisins.ui.neighbour_detail;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class NeighbourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NEIGHBOUR = "EXTRA_NEIGHBOUR";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.tvNeighbourName)
    TextView mTvNeighbourName;

    @BindView(R.id.tvNeighbourAddress)
    TextView mTvNeighbourAddress;

    @BindView(R.id.tvNeighbourCall)
    TextView mTvNeighbourCellNumber;

    @BindView(R.id.tvNeighbourAbout)
    TextView mTvNeighbourAbout;

    @BindView(R.id.fabToggleFavorite)
    FloatingActionButton mFabToggleFavorite;

    @BindView(R.id.ivNeighbourProfile)
    ImageView mIvNeighbourProfile;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Neighbour neighbour;

    private NeighbourApiService service;

    private Drawable filledStar;
    private Drawable strokeStar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);

        filledStar = ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp);
        strokeStar = ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp);

        service = DI.getNeighbourApiService();

        ButterKnife.bind(this);

        setupToolbar();

        neighbour = (Neighbour) getIntent().getSerializableExtra(EXTRA_NEIGHBOUR);

        updateUi();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateUi() {
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(mIvNeighbourProfile);

        mCollapsingToolbar.setTitle(neighbour.getName());
        mTvNeighbourName.setText(neighbour.getName());
        mTvNeighbourAddress.setText(neighbour.getAddress());
        mTvNeighbourCellNumber.setText(neighbour.getPhoneNumber());
        mTvNeighbourAbout.setText(neighbour.getAboutMe());

        updateFavorite();
    }

    private void updateFavorite() {
        Drawable icFavorite = service.isNeighbourFavorite(neighbour)
                ? filledStar
                : strokeStar;

        mFabToggleFavorite.setImageDrawable(icFavorite);
    }

    public static void navigate(Context context, Neighbour neighbour) {
        Intent intent = new Intent(context, NeighbourDetailActivity.class);
        intent.putExtra(EXTRA_NEIGHBOUR, neighbour);
        context.startActivity(intent);
    }

    @OnClick(R.id.fabToggleFavorite)
    public void toggleFavorite() {
        mFabToggleFavorite
                .animate()
                .setDuration(125)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFabToggleFavorite
                                .animate()
                                .setDuration(125)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setListener(null)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
        if (service.isNeighbourFavorite(neighbour)) {
            service.removeNeighbourFromFavorite(neighbour);
        } else {
            service.addNeighbourToFavorite(neighbour);
        }

        updateFavorite();
    }

}
