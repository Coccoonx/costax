package com.mobilesoft.bonways.core.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 21/01/2017.
 */

@Data
public class Consumer implements Serializable {

    private long id;

    private String email;

    private String firstname;

    private String lastname;

    private String  phone;

    private String userToken;

    private boolean isTrader;

    private String displayName;

    private Date createdDate;

    private Date updatedDate;

    private String imageUrl;

    private boolean isLogged;

    private SubscriptionType subscriptionType;
}
