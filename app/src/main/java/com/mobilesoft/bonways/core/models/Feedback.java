package com.mobilesoft.bonways.core.models;

import com.mobilesoft.bonways.core.managers.ProfileManager;

import java.util.Date;
import lombok.Data;

@Data
public class Feedback {
    private Long id;
    private String userId;
    private String description;
    private String title;
    private Date reportDate;

    public Feedback() {
        userId = ProfileManager.getCurrentUserProfile().getConsumer().getEmail();
    }

}
