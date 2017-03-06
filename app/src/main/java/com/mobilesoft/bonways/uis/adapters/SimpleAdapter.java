package com.mobilesoft.bonways.uis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.uis.MainActivity;

import java.util.List;

public class SimpleAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List mData;
    private Context mContext;

    public SimpleAdapter(Context context, List arrayList) {
        layoutInflater = LayoutInflater.from(context);
        mData = arrayList;
        mContext = context;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            viewHolder.container = (LinearLayout) view.findViewById(R.id.holder_container);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Category category = (Category) mData.get(position);
        viewHolder.textView.setText(category.getTitle());
        viewHolder.imageView.setImageResource(category.getIconIntUrl());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Category choosed: " + category.getTitle(), Toast.LENGTH_SHORT).show();
                if (MainActivity.dialog !=null)
                    MainActivity.dialog.dismiss();
            }
        });


        return view;
    }

    static class ViewHolder {
        LinearLayout container;
        TextView textView;
        ImageView imageView;
    }
}
