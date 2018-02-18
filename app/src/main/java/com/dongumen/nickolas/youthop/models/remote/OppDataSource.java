package com.dongumen.nickolas.youthop.models.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nickolas on 19.02.2018.
 */

public class OppDataSource {
    DatabaseReference mDatabase;

    public OppDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference("opp");
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }
}
