package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.view.SearchView;

import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;


@InjectViewState
public class SearchViewPresenter extends MvpPresenter<SearchView>{
    ItemListDataSource dataSource = new ItemListDataSource();

    public void search(String name){
        RxFirebaseDatabase.observeSingleValueEvent(dataSource.getmDatabase().limitToFirst(10),
                DataSnapshotMapper.listOf(OppListItem.class))
                .timeout(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(items -> getViewState().showList(items),
                        throwable -> getViewState().showError());
    }

}
