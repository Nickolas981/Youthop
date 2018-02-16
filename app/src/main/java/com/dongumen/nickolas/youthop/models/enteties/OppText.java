package com.dongumen.nickolas.youthop.models.enteties;

import java.util.List;

/**
 * Created by Nickolas on 16.02.2018.
 */

public class OppText {
    public String description, location, eligibleRegions;
    public List<String> benefits, eligibilities;

    public OppText(String description, String location,
                   String eligibleRegions, List<String> benefits, List<String> eligibilities) {
        this.description = description;
        this.location = location;
        this.eligibleRegions = eligibleRegions;
        this.benefits = benefits;
        this.eligibilities = eligibilities;
    }

    public OppText() {

    }
}
