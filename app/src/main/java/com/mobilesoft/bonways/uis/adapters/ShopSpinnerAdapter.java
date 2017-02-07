package com.mobilesoft.bonways.uis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.core.models.TradeItem;

import java.util.ArrayList;

public class ShopSpinnerAdapter extends ArrayAdapter<String> {
    int groupid;
    ArrayList<String> list;
    LayoutInflater inflater;

    public ShopSpinnerAdapter(Activity context, int groupid, int id, ArrayList<String>
            list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        TextView shopName = (TextView) itemView.findViewById(R.id.text_title);
        shopName.setText(list.get(position));
//        TextView shopReference = (TextView) itemView.findViewById(R.id.text_subtitle);
//        shopReference.setText(list.get(position).getAddress());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);
    }
}