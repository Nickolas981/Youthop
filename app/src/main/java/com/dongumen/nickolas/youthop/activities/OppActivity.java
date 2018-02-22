package com.dongumen.nickolas.youthop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.transition.Slide;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppDates;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;
import com.dongumen.nickolas.youthop.presenters.OppPresenter;
import com.dongumen.nickolas.youthop.utils.DateUtil;
import com.dongumen.nickolas.youthop.view.OppView;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OppActivity extends MvpAppCompatActivity implements OppView {

    @InjectPresenter
    OppPresenter presenter;
    @BindView(R.id.image_id)
    PanoramaImageView image;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.deadline)
    TextView deadline;
    @BindView(R.id.time_left)
    TextView timeLeft;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.fab)
    FloatingActionButton button;
    @BindView(R.id.description)
    TextView description;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images");
    private GyroscopeObserver gyroscopeObserver;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opp);
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);
        Slidr.attach(this);
        ButterKnife.bind(this);
        image.setGyroscopeObserver(gyroscopeObserver);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        id = getIntent().getStringExtra("id");
        presenter.loadOpp(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    public void showOpp(Opportunity opportunity) {
        Toast.makeText(this, "loaded", Toast.LENGTH_SHORT).show();
        button.setVisibility(View.VISIBLE);
        setOnClick(opportunity);
        Glide.with(this)
                .load(storageReference.child(opportunity.imageId))
                .into(image);
        type.setText(opportunity.type);
        name.setText(opportunity.name);
        timeLeft.append(DateUtil.getDeadlineDays(opportunity.deadline));
        location.setText(opportunity.place);
        deadline.append(DateUtil.getDateFrormated(opportunity.deadline));
        description.setText(opportunity.oppText.description);
    }

    private void setOnClick(Opportunity opportunity) {
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, opportunity.name);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    opportunity.dates.start);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    opportunity.dates.end);
            intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
            intent.putExtra(CalendarContract.Events.DESCRIPTION, opportunity.type);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_opp_menu, menu);


        return true;
    }
}
