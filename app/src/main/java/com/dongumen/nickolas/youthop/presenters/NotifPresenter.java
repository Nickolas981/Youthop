package com.dongumen.nickolas.youthop.presenters;

import android.app.Activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.utils.NotifUtil;
import com.dongumen.nickolas.youthop.view.NotifView;
import com.google.firebase.database.Query;

import java.util.List;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

@InjectViewState
public class NotifPresenter extends MvpPresenter<NotifView> {
    NotifUtil notifUtil;
    ItemListDataSource dataSource = new ItemListDataSource();

    public void init(Activity activity) {
        notifUtil = new NotifUtil(activity);
    }

    public void getList() {
        List<String> listOfIds = notifUtil.getNotifList();
        for (String id : listOfIds) {
            Query query = dataSource.getmDatabase().orderByChild("id").equalTo(id);
            RxFirebaseDatabase.observeSingleValueEvent(query,
                    DataSnapshotMapper.listOf(OppListItem.class))
                    .map(oppListItems -> oppListItems.get(0))
                    .subscribe(oppListItem -> getViewState().addNotif(oppListItem));
        }
    }
}
