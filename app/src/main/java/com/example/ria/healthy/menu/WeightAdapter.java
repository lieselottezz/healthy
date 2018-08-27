package com.example.ria.healthy.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ria.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter {

    List<Weight> weights = new ArrayList<Weight>();
    Context context;

    public WeightAdapter(@NonNull Context context,
                         int resource,
                         @NonNull List<Weight> objects) {
        super(context, resource, objects);
        this.weights = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View _weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false);
        TextView _date = _weightItem.findViewById(R.id.weight_item_date);
        TextView _weight = _weightItem.findViewById(R.id.weight_item_weight);
        TextView _status = _weightItem.findViewById(R.id.weight_item_status);
        Weight _row = weights.get(position);
        _date.setText(_row.getDate());
        _weight.setText(Integer.toString(_row.getWeight()));
        _status.setText(_row.getStatus());
        return super.getView(position, convertView, parent);
    }
}
