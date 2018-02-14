package com.dongumen.nickolas.youthop.models.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nickolas on 14.02.2018.
 */

public class ItemListDataSource {

    DatabaseReference mDatabase;

    public ItemListDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference("listItem");
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }
}
