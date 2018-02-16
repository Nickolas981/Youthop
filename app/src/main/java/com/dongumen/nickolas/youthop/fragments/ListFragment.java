package com.dongumen.nickolas.youthop.fragments;

import android.content.Context;
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
import com.dongumen.nickolas.youthop.enums.QueryTypes;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.ListViewPresenter;
import com.dongumen.nickolas.youthop.view.ListView;
import com.dongumen.nickolas.youthop.widgets.adapters.ListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends MvpAppCompatFragment implements ListView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    View loadingView;
    @BindView(R.id.error_view)
    View errorView;
    @BindView(R.id.try_again)
    Button tryAgainButton;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    public QueryTypes type;


    @InjectPresenter
    ListViewPresenter presenter;
    ListAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance(QueryTypes t) {
        ListFragment fragment = new ListFragment();
        fragment.type = t;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        refreshLayout.setOnRefreshListener(this);
        tryAgainButton.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getList(type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showList(List<OppListItem> list) {
        adapter.setListItems(list);
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
        presenter.refresh(type);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.try_again)
            presenter.tryAgainClicked(type);
    }
}
