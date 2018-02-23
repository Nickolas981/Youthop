package com.dongumen.nickolas.youthop.view;

import com.arellomobile.mvp.MvpView;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;

/**
 * Created by Nickolas on 23.02.2018.
 */

public interface BookmarkView extends MvpView{
    void addBookmark(OppListItem oppListItem);
}
