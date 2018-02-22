package com.dongumen.nickolas.youthop.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;
import com.dongumen.nickolas.youthop.presenters.OppPresenter;
import com.dongumen.nickolas.youthop.utils.BookmarkUtil;
import com.dongumen.nickolas.youthop.utils.DateUtil;
import com.dongumen.nickolas.youthop.view.OppView;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.r0adkll.slidr.Slidr;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OppActivity extends MvpAppCompatActivity implements OppView, View.OnClickListener {

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
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.fab)
    FloatingActionButton button;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.bottom_bar)
    View bottomBar;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.location)
    TextView location;
    MenuItem bookmark;
    @BindView(R.id.benefits_container)
    View benefitContainer;
    @BindView(R.id.benefits)
    LinearLayout benefitsList;
    @BindView(R.id.eligibilities_container)
    View eligibilitiesContainer;
    @BindView(R.id.eligibilities)
    LinearLayout eligibilitiesList;
    @BindView(R.id.eligible_regions)
    TextView eligibleRegions;



    private Opportunity opportunity;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images");
    private GyroscopeObserver gyroscopeObserver;
    private BookmarkUtil bookmarkUtil;
    MenuItem.OnMenuItemClickListener bookmarkEnabled, bookmarkDisabled;


    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opp);
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        opportunity = new Opportunity();
        bookmarkUtil = new BookmarkUtil(this);
        Slidr.attach(this);
        ButterKnife.bind(this);
        initListeners();
        image.setGyroscopeObserver(gyroscopeObserver);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        id = getIntent().getStringExtra("id");
        presenter.loadOpp(id);
    }

    private void initListeners() {
        bookmarkDisabled = view -> {
            bookmark.setIcon(R.drawable.ic_bookmark_fill_black_24dp);
            bookmarkUtil.addBookmark(opportunity.id);
            bookmark.setOnMenuItemClickListener(bookmarkEnabled);
            return true;
        };
        bookmarkEnabled = view -> {
            bookmark.setIcon(R.drawable.ic_bookmark_black_24dp);
            bookmarkUtil.deleteBookmark(opportunity.id);
            bookmark.setOnMenuItemClickListener(bookmarkDisabled);
            return true;
        };
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
        this.opportunity = opportunity;
        button.setVisibility(View.VISIBLE);
        bottomBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(storageReference.child(opportunity.imageId))
                .into(image);
        type.setText(opportunity.type);
        name.setText(opportunity.name);
        price.setText(opportunity.price);
        timeLeft.append(DateUtil.getDeadlineDays(opportunity.deadline));
        place.setText(opportunity.place);
        deadline.append(DateUtil.getDateFrormated(opportunity.deadline));
        description.setText(opportunity.oppText.description);
        location.setText(opportunity.oppText.location);
        if (opportunity.oppText.benefits != null && !opportunity.oppText.benefits.isEmpty()) {
            benefitContainer.setVisibility(View.VISIBLE);
            loadList(opportunity.oppText.benefits, benefitsList);
        }
        if (opportunity.oppText.eligibilities != null && !opportunity.oppText.eligibilities.isEmpty()) {
            eligibilitiesContainer.setVisibility(View.VISIBLE);
            loadList(opportunity.oppText.eligibilities, eligibilitiesList);
        }
        eligibleRegions.setText(opportunity.oppText.eligibleRegions);
    }


    public void loadList(List<String> list, LinearLayout l) {
        for (String benefit : list) {
            View view = getLayoutInflater().inflate(R.layout.text_item, null);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(benefit);
            l.addView(view);
        }
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_opp_menu, menu);
        bookmark = menu.findItem(R.id.action_bookmark);
        if (bookmarkUtil.getBookmarkList().contains(opportunity.id)) {
            bookmark.setIcon(R.drawable.ic_bookmark_fill_black_24dp);
            bookmark.setOnMenuItemClickListener(bookmarkEnabled);
        } else {
            bookmark.setIcon(R.drawable.ic_bookmark_black_24dp);
            bookmark.setOnMenuItemClickListener(bookmarkDisabled);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.share:
                presenter.share(opportunity, this);
                break;
            case R.id.twitter:
                presenter.shareTwitter(opportunity, this);
                break;
            case R.id.viber:
                presenter.shareViber(opportunity, this);
                break;
            case R.id.whatsapp:
                presenter.shareWhatsapp(opportunity, this);
                break;
            case R.id.mail:
                presenter.shareMail(opportunity, this);
                break;
            case R.id.facebook:
                break;
            case R.id.fab:
                presenter.shareCalendar(opportunity, this);
                break;
            case R.id.apply_now:
                presenter.applyNow(opportunity, this);
                break;
            case R.id.official_link:
                presenter.officialLink(opportunity, this);
                break;
            default:
                Toast.makeText(this, "Not defined", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
