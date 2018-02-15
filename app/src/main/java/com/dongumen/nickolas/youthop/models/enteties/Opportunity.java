package com.dongumen.nickolas.youthop.models.enteties;

import android.graphics.Path;

/**
 * Created by Nickolas on 16.02.2018.
 */

public class Opportunity extends OppListItem {
    public String price;
    public OppText oppText;
    public OppUrls oppUrls;
    public int shares;

    public Opportunity(OppListItem oppListItem, String price, OppText oppText, OppUrls oppUrls, int shares) {
        super(oppListItem.type, oppListItem.place, oppListItem.url,
                oppListItem.id, oppListItem.name, oppListItem.date);
        this.price = price;
        this.oppText = oppText;
        this.oppUrls = oppUrls;
        this.shares = shares;
    }
    public Opportunity() {
    }
}
