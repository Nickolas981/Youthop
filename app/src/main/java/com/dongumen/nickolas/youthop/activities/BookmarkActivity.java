package com.dongumen.nickolas.youthop.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.BookmarkPresenter;
import com.dongumen.nickolas.youthop.view.BookmarkView;
import com.dongumen.nickolas.youthop.widgets.adapters.SortedListAdapter;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarkActivity extends MvpAppCompatActivity implements BookmarkView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    BookmarkPresenter presenter;

    ArrayList<OppListItem> oppListItems;
    SortedListAdapter listAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);
        Slidr.attach(this);
        oppListItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listAdapter = new SortedListAdapter(this, oppListItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        oppListItems.clear();
        listAdapter.notifyDataSetChanged();
        presenter.getList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void addBookmark(OppListItem oppListItem) {
        oppListItems.add(oppListItem);
        listAdapter.notifyDataSetChanged();
    }
}
