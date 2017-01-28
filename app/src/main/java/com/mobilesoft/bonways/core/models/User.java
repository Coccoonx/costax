package com.mobilesoft.bonways.core.models;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 21/01/2017.
 */

@Data
public class User implements Serializable {

    private String dob;

    private String email;

    private String firstname;

    private String lastname;

    private String  phone;

    private String userToken;

    private boolean isTrader;

    private String traderType;

    private String displayName;

    private String createdDate;

    private String updatedDate;

    private String imageUrl;

    private boolean isLogged;
}
