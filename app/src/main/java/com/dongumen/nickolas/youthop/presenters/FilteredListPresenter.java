package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.enteties.OppText;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.view.ListView;
import com.dongumen.nickolas.youthop.widgets.enums.OppType;
import com.dongumen.nickolas.youthop.widgets.enums.QueryTypes;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FilteredListPresenter extends MvpPresenter<ListView> {

    ItemListDataSource dataSource = new ItemListDataSource();

    public void getList(OppType oppType){
        Query query;
            query = dataSource.getmDatabase().limitToFirst(25).orderByChild("type").equalTo(oppType.name());

        RxFirebaseDatabase.observeSingleValueEvent(query,
                DataSnapshotMapper.listOf(OppListItem.class))
                .timeout(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(items -> getViewState().showList(items),
                        throwable -> getViewState().showError());
    }

    public void tryAgainClicked(OppType type) {
        getViewState().showLoading();
        getList(type);
    }

    public void refresh(OppType type){
        getViewState().showLoading();
        getList(type);
    }







}
