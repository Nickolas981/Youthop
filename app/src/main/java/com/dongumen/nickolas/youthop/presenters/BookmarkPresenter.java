package com.dongumen.nickolas.youthop.presenters;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.enums.QueryTypes;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.models.remote.ItemListDataSource;
import com.dongumen.nickolas.youthop.utils.BookmarkUtil;
import com.dongumen.nickolas.youthop.view.BookmarkView;
import com.dongumen.nickolas.youthop.view.SearchView;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

@InjectViewState
public class BookmarkPresenter extends MvpPresenter<BookmarkView> {
    BookmarkUtil bookmarkUtil;
    ItemListDataSource dataSource = new ItemListDataSource();

    public void init(Activity activity){
        bookmarkUtil = new BookmarkUtil(activity);
    }

    public void getList(){
        List<String> listOfIds = bookmarkUtil.getBookmarkList();
        for (String id : listOfIds){
            Query query = dataSource.getmDatabase().orderByChild("id").equalTo(id);
            RxFirebaseDatabase.observeSingleValueEvent(query,
                    DataSnapshotMapper.listOf(OppListItem.class))
                    .map(oppListItems -> oppListItems.get(0))
                    .subscribe(oppListItem -> getViewState().addBookmark(oppListItem));
        }
    }

}
