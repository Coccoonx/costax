package com.mobilesoft.bonways.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by Lyonnel Dzotang on 14/04/2017.
 */
@Data
public class SubscriptionType implements Serializable, Parcelable {

    private String id;
    private String code;
    private String name;
    private String description;
    private double cost;
    private int imageInt;
    private String imageUrl;
    private int validity;
    private Date startTime;
    private Date endTime;

    public SubscriptionType(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeDouble(this.cost);
        dest.writeInt(this.imageInt);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.validity);
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
    }

    protected SubscriptionType(Parcel in) {
        this.id = in.readString();
        this.code = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.cost = in.readDouble();
        this.imageInt = in.readInt();
        this.imageUrl = in.readString();
        this.validity = in.readInt();
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
    }

    public static final Parcelable.Creator<SubscriptionType> CREATOR = new Parcelable.Creator<SubscriptionType>() {
        @Override
        public SubscriptionType createFromParcel(Parcel source) {
            return new SubscriptionType(source);
        }

        @Override
        public SubscriptionType[] newArray(int size) {
            return new SubscriptionType[size];
        }
    };
}
