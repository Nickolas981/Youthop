package com.dongumen.nickolas.youthop.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.FilteredListPresenter;
import com.dongumen.nickolas.youthop.presenters.ListViewPresenter;
import com.dongumen.nickolas.youthop.view.ListView;
import com.dongumen.nickolas.youthop.widgets.adapters.FilteredListAdapter;
import com.dongumen.nickolas.youthop.widgets.adapters.SortedListAdapter;
import com.dongumen.nickolas.youthop.widgets.enums.OppType;
import com.dongumen.nickolas.youthop.widgets.listeners.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilteredListFragment extends MvpAppCompatFragment implements ListView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    View loadingView;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.try_again)
    Button tryAgainButton;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private OppType type;

    List<OppListItem> listItems;
    public LinearLayoutManager linearLayoutManager;


    @InjectPresenter
    FilteredListPresenter presenter;
    FilteredListAdapter adapter;

    public FilteredListFragment() {
    }

    public static FilteredListFragment newInstance(OppType type) {
        FilteredListFragment fragment = new FilteredListFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        refreshLayout.setOnRefreshListener(this);
        tryAgainButton.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FilteredListAdapter(getActivity(), listItems);
        recyclerView.setAdapter(adapter);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getList(type);
    }

    @Override
    public void showList(List<OppListItem> list) {
        for (OppListItem item: list){
            if (!listItems.contains(item)){
                listItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
        errorView.setVisibility(View.INVISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        refreshLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoading() {
        errorView.setVisibility(View.INVISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh() {
        listItems.clear();
        adapter.notifyDataSetChanged();
        presenter.refresh(type);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.try_again)
            presenter.tryAgainClicked(type);
    }
}