package com.dongumen.nickolas.youthop.models.enteties;


public class OppListItem {

    public String type, place, url, id, name;
    public long date;

    public OppListItem() {
    }

    public OppListItem(String type, String place, String url, String id, String name, long date) {
        this.type = type;
        this.place = place;
        this.url = url;
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
