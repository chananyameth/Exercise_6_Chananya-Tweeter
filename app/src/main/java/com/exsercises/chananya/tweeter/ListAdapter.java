package com.exsercises.chananya.tweeter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gidi7 on 01/03/2016.
 */
public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> items;

    public ListAdapter(Context context, ArrayList<Post> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            Log.d("CustomAdapter", "New View");
        }
        else {
            Log.d("CustomAdapter", "using convertView");
        }

        Post curItem = items.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView bodyTextView = (TextView) convertView.findViewById(R.id.bodyTextView);

        titleTextView.setText(curItem.title);
        bodyTextView.setText(curItem.body);

        return convertView;
    }
}
