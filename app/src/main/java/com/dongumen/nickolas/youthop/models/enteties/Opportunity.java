package com.dongumen.nickolas.youthop.models.enteties;



public class Opportunity extends OppListItem {
    public String price;
    public OppText oppText;
    public OppUrls oppUrls;
    public OppDates dates;

    public Opportunity(OppListItem oppListItem, String price, OppText oppText, OppUrls oppUrls, OppDates dates) {
        super(oppListItem.type, oppListItem.place, oppListItem.imageId,
                oppListItem.id, oppListItem.name, oppListItem.deadline, oppListItem.dateCreated );
        this.price = price;
        this.oppText = oppText;
        this.oppUrls = oppUrls;
        this.dates = dates;
    }
    public Opportunity() {
    }
}
