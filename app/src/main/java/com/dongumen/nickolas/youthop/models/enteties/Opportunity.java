package com.dongumen.nickolas.youthop.models.enteties;

/**
 * Created by Nickolas on 16.02.2018.
 */

public class Opportunity extends OppListItem {
    public String price;
    public OppText oppText;
    public OppUrls oppUrls;

    public Opportunity(OppListItem oppListItem, String price, OppText oppText, OppUrls oppUrls) {
        super(oppListItem.type, oppListItem.place, oppListItem.imageId,
                oppListItem.id, oppListItem.name, oppListItem.deadline);
        this.price = price;
        this.oppText = oppText;
        this.oppUrls = oppUrls;
    }
    public Opportunity() {
    }
}
