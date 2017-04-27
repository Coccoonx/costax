package com.mobilesoft.bonways.core.models;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 11/04/2017.
 */

@Data
public class Reservation implements Serializable{
    private String id;
    private String code;
    private int quantity;
    private Product object;
    private String dateOfReservation;
    private ReservationStatus status;

    public Reservation() {
        id = UUID.randomUUID().toString();
        code = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        dateOfReservation = sdf.format(new Date());
        status = ReservationStatus.PENDING;

    }

}
