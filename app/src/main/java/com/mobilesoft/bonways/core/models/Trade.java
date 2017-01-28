package com.mobilesoft.bonways.core.models;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */

@Data
public class Trade implements Serializable{

    private String designation;

    private String description;

    private double latitude;

    private double longitude;

    private String  phone;

    private String poBox;

    private String createdDate;

    private String updatedDate;

    private User user;

    private String logoUrl;

    private Set<Product> productslist;
}
