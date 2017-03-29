package com.mobilesoft.bonways.core.models;

import java.io.Serializable;

import lombok.Data;


@Data
public class Category implements Serializable {

    Long id;
    String title;
    Category parent;
    int iconIntUrl;
    String iconStringUrl;

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Category))
            return false;

        Category tmp = (Category) o;

        return tmp.getTitle().equals(this.getTitle());
    }


}