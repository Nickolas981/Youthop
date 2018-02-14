package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.view.ListView;

import java.util.List;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

@InjectViewState
public class ListViewPresenter extends MvpPresenter<ListView> {
    ItemListDataSource dataSource = new ItemListDataSource();
    String lastId = "";


    public void getList(){
        RxFirebaseDatabase.observeSingleValueEvent(dataSource.getmDatabase().limitToFirst(10),
                DataSnapshotMapper.listOf(OppListItem.class))
                .subscribe(items -> getViewState().showList(items));
    }
}
