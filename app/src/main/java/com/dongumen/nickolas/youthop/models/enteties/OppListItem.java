package com.dongumen.nickolas.youthop.models.enteties;


public class OppListItem {
    public String price;

    public String type, place, imageId, id, name;
    public long deadline, dateCreated;

    public OppListItem() {
    }

    public OppListItem(String type, String place, String imageId, String id, String name, long deadline, long dateCreated, String price) {
        this.type = type;
        this.place = place;
        this.imageId = imageId;
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.dateCreated = dateCreated;
        this.price = price;
    }
}
