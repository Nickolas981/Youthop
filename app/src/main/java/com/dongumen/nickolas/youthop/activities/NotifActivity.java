package com.dongumen.nickolas.youthop.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.NotifPresenter;
import com.dongumen.nickolas.youthop.utils.NotifUtil;
import com.dongumen.nickolas.youthop.view.NotifView;
import com.dongumen.nickolas.youthop.widgets.adapters.SortedListAdapter;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifActivity extends MvpAppCompatActivity implements NotifView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    NotifPresenter presenter;

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
        presenter.getList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new NotifUtil(this).deleteNotif();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void addNotif(OppListItem oppListItem) {
        oppListItems.add(oppListItem);
        listAdapter.notifyDataSetChanged();
    }
}
