package com.dongumen.nickolas.youthop.view;

import com.arellomobile.mvp.MvpView;
import com.dongumen.nickolas.youthop.models.enteties.Opportunity;

/**
 * Created by Nickolas on 19.02.2018.
 */

public interface OppView extends MvpView {
    void showOpp(Opportunity opportunity);
    void showToast(String str);
}
