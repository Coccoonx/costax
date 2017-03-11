package com.mobilesoft.bonways.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;


@Data
public class Profile implements Serializable {

    User user = new User();
    List<Trade> trades = new ArrayList();
    Set<Product> myProducts = new HashSet<>();
    Set<Product> products = new HashSet<>();

}
