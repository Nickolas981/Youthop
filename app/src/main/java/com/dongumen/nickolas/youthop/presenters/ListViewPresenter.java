package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.widgets.enums.QueryTypes;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.view.ListView;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class ListViewPresenter extends MvpPresenter<ListView> {
        ItemListDataSource dataSource = new ItemListDataSource();
        long lastDate ;

    public void getList(QueryTypes type) {
        Query query;
        if (type == QueryTypes.Latest){
            query = dataSource.getmDatabase().limitToFirst(10).orderByChild("dateCreated");
        }else if (type == QueryTypes.Approach){
            query = dataSource.getmDatabase().limitToFirst(10).orderByChild("deadline");
        }else {
            query = dataSource.getmDatabase().limitToLast(10).orderByChild("deadline");
        }

        if (lastDate != 0) {
                query =  query.startAt(lastDate);
        }
        RxFirebaseDatabase.observeSingleValueEvent(query,
                DataSnapshotMapper.listOf(OppListItem.class))
                .timeout(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(oppListItems -> {
                    long newDate;
                    if (type == QueryTypes.Latest) {
                        newDate = oppListItems.get(oppListItems.size() - 1).dateCreated;
                    } else {
                        newDate = oppListItems.get(oppListItems.size() - 1).deadline;
                    }
                    if (newDate == lastDate)
                        return new ArrayList<OppListItem>();
                    lastDate = newDate;
                    return oppListItems;
                })
                .subscribe(items -> getViewState().showList(items),
                        throwable -> getViewState().showError());
    }

    public void tryAgainClicked(QueryTypes type) {
        getViewState().showLoading();
        lastDate = 0;
        getList(type);
    }

    public void refresh(QueryTypes type){
        lastDate = 0;
        getViewState().showLoading();
        getList(type);
    }
}
