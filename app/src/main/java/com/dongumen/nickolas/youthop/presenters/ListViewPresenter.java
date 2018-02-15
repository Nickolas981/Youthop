package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.view.ListView;

import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class ListViewPresenter extends MvpPresenter<ListView> {
    ItemListDataSource dataSource = new ItemListDataSource();
    String lastId = "";

    public void getList() {
        RxFirebaseDatabase.observeSingleValueEvent(dataSource.getmDatabase().limitToFirst(10),
                DataSnapshotMapper.listOf(OppListItem.class))
                .timeout(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(oppListItems -> {
                    lastId = oppListItems.get(oppListItems.size() - 1).id;
                    return oppListItems;
                })
                .subscribe(items -> getViewState().showList(items),
                        throwable -> getViewState().showError());
    }

    public void tryAgainClicked() {
        getViewState().showLoading();
        lastId = "";
        getList();
    }

    public void refresh(){
        lastId = "";
        getList();
    }
}
