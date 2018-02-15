package com.dongumen.nickolas.youthop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.presenters.ListViewPresenter;
import com.dongumen.nickolas.youthop.view.ListView;
import com.dongumen.nickolas.youthop.widgets.adapters.ListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends MvpAppCompatFragment implements ListView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectPresenter
    ListViewPresenter presenter;
    ListAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getList();
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
    }
}
