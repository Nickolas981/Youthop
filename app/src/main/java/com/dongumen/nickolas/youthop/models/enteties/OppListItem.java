package com.dongumen.nickolas.youthop.models.enteties;


public class OppListItem {

    public String name;
    public String type, place, url;
    public long date;

    public OppListItem() {
    }

    public OppListItem(String name, String type, String place, String url, long date) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.url = url;
        this.date = date;
    }
}
