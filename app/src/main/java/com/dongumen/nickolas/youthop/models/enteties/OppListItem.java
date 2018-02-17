package com.dongumen.nickolas.youthop.models.enteties;


public class OppListItem {

    public String type, place, imageId, id, name;
    public long deadline, dateCreated;

    public OppListItem() {
    }

    public OppListItem(String type, String place, String imageId, String id, String name, long deadline, long dateCreated) {
        this.type = type;
        this.place = place;
        this.imageId = imageId;
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OppListItem)) {
            return false;
        }
        OppListItem other = (OppListItem) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
            return id.hashCode();
    }
}
