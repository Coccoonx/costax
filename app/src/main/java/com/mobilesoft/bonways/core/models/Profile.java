package com.mobilesoft.bonways.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;


@Data
public class Profile implements Serializable {

    Consumer consumer = new Consumer();
    List<Trade> trades = new ArrayList();
    Set<Product> myProducts = new HashSet<>();
    Set<Product> products = new HashSet<>();
    Set<Reservation> myReservations = new HashSet<>();

}
