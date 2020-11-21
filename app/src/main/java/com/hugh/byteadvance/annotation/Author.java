package com.hugh.byteadvance.annotation;

/**
 * Created by chenyw on 2020/10/20.
 */
public class Author {
    public String name;
    public String website;

    public Author(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }
}
