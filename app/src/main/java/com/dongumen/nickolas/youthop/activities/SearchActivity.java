package com.dongumen.nickolas.youthop.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.SearchViewPresenter;
import com.dongumen.nickolas.youthop.view.SearchView;
import com.dongumen.nickolas.youthop.widgets.adapters.ListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends MvpAppCompatActivity implements SearchView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search)
    EditText searchBox;

    @InjectPresenter
    SearchViewPresenter presenter;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.search(editable.toString());
            }
        });
        adapter = new ListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showList(List<OppListItem> list) {
        adapter.setListItems(list);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }
}
