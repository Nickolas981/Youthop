package com.dongumen.nickolas.youthop.view;

import com.arellomobile.mvp.MvpView;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;

import java.util.List;


public interface ListView extends MvpView {
    void showList(List<OppListItem> list);
    void showError();
    void showLoading();
}
