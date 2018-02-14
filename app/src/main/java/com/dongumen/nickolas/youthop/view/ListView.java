package com.dongumen.nickolas.youthop.view;

import com.arellomobile.mvp.MvpView;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;

import java.util.List;

/**
 * Created by Nickolas on 14.02.2018.
 */

public interface ListView extends MvpView {
    void showList(List<OppListItem> list);
}
