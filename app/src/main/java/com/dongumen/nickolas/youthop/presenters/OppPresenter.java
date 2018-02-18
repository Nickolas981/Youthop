package com.dongumen.nickolas.youthop.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dongumen.nickolas.youthop.enums.QueryTypes;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;
import com.dongumen.nickolas.youthop.models.remote.OppDataSource;
import com.dongumen.nickolas.youthop.view.OppView;
import com.google.firebase.database.Query;

import durdinapps.rxfirebase2.RxFirebaseDatabase;

/**
 * Created by Nickolas on 19.02.2018.
 */
@InjectViewState
public class OppPresenter extends MvpPresenter<OppView> {

    OppDataSource dataSource = new OppDataSource();

    public void loadOpp(String id){
        Query query = dataSource.getmDatabase().child(id);
        RxFirebaseDatabase.observeSingleValueEvent(query, Opportunity.class)
                .subscribe(opportunity -> getViewState().showOpp(opportunity));
    }

}
