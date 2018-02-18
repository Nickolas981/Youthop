package com.dongumen.nickolas.youthop.activities;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;
import com.dongumen.nickolas.youthop.presenters.OppPresenter;
import com.dongumen.nickolas.youthop.view.OppView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OppActivity extends MvpAppCompatActivity implements OppView {

    @InjectPresenter
    OppPresenter presenter;
    @BindView(R.id.image_id)
    ImageView image;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images");

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opp);
        ButterKnife.bind(this);
        initActivityTransitions();
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        id = getIntent().getStringExtra("id");
        presenter.loadOpp(id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void showOpp(Opportunity opportunity) {
        Toast.makeText(this, "loaded", Toast.LENGTH_SHORT).show();
        Glide.with(this)
                .load(storageReference.child(opportunity.imageId))
                .into(image);
    }
}
