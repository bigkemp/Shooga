package com.example.pelleg.shooga;

import android.content.Context;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Alarm> {

    public ListViewAdapter(Context context, ArrayList<Alarm> resource) {
        super(context, R.layout.row_alarm, resource);

    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View Custom;
        // View Custom2 = Inflater.inflate(R.layout.xheader,parent,false);
        Alarm singleItem = getItem(position);

        Custom = Inflater.inflate(R.layout.row_alarm, parent,false);

        TextView name = (TextView) Custom.findViewById(R.id.tvAlarmName);
        TextView time = (TextView) Custom.findViewById(R.id.tvAlarmTime);
        TextView repeat = (TextView) Custom.findViewById(R.id.tvAlarmRepeat);
        TextView statue = (TextView) Custom.findViewById(R.id.tvAlarmStatue);

        if (singleItem.getRepeat()) {
            repeat.setTextColor(Color.RED);
            repeat.setTypeface(repeat.getTypeface(), Typeface.BOLD_ITALIC);
            repeat.setText("Daily");
        }
        else
            repeat.setText("Single");


        if (singleItem.getStatue()) {
            statue.setText("ON");
            statue.setTextColor(Color.RED);
            statue.setTypeface(repeat.getTypeface(), Typeface.BOLD_ITALIC);
        }
        else
            statue.setText("OFF");

        name.setText(singleItem.getName());
        time.setText(singleItem.getTime());

        return Custom;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getPosition( @Nullable Alarm item ) {
        return super.getPosition(item);
    }
    public void yayziz (){}

    @Nullable
    @Override
    public Alarm getItem( int position ) {
        return super.getItem(position);
    }


}



