package com.github.zjjfly.readinglist.model;

import lombok.Data;

/**
 * @author zjjfly[https://github.com/zjjfly] on 2020/4/16
 */
@Data
public class PlaceOfBirth {

    private String city;
    private String country;

    public PlaceOfBirth(String city) {
        this.city = city;
    }

    public PlaceOfBirth(String city, String country) {
        this(city);
        this.country = country;
    }

}
