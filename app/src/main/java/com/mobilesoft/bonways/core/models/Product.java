package com.mobilesoft.bonways.core.models;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 27/01/2017.
 */
@Data
public class Product implements Serializable {

    private String code;

    private String designation;

    private long unit;

    private long packing;

    private double price;

    private double discount;

    private String description;

    private Long unitQuantity;

    private String imageUrl;

    private String createdDate;

    private String updatedDate;

    private Category category;

    private Trade trade;
}
