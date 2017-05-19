package com.mobilesoft.bonways.core.models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 15/05/2017.
 */
@Data
public class Comment implements Serializable, Comparable<Comment>{

    private long id;

    private long date;

    private String authorId;
    private String authorName;
    private String pictureUrl;

    private String content;

    @Override
    public int compareTo(Comment o) {
        return new Date(date).compareTo(new Date(o.date));
    }
}
