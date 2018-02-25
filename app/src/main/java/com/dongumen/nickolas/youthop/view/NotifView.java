package com.dongumen.nickolas.youthop.view;

import com.arellomobile.mvp.MvpView;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;

public interface NotifView extends MvpView{
    void addNotif(OppListItem oppListItem);
}
