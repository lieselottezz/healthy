package com.example.ria.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ria.healthy.R;

import java.util.List;

public class SleepAdapter extends ArrayAdapter {

    private List<Sleep> sleeps;
    private Context context;

    public SleepAdapter(@NonNull Context context,
                         int resource,
                         @NonNull List<Sleep> objects) {
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View sleepItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_sleep_item,
                parent,
                false);
        TextView date = sleepItem.findViewById(R.id.sleep_item_date);
        TextView sleepTime = sleepItem.findViewById(R.id.sleep_item_sleep_time);
        TextView wakeupTime = sleepItem.findViewById(R.id.sleep_item_wakeup_time);
        TextView totalSleepTime = sleepItem.findViewById(R.id.sleep_item_total_sleep_time);
        Sleep row = sleeps.get(position);
        date.setText(row.getDate());
        sleepTime.setText(row.getSleepTime());
        wakeupTime.setText(row.getWakeupTime());
        totalSleepTime.setText(row.getTotalSleepTime());
        return sleepItem;
    }
}
