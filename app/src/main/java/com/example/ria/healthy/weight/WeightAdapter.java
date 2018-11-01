package com.example.ria.healthy.weight;

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

public class WeightAdapter extends ArrayAdapter {

    private List<Weight> weights;
    private Context context;

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
        View weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false);
        TextView date = weightItem.findViewById(R.id.weight_item_date);
        TextView weight = weightItem.findViewById(R.id.weight_item_weight);
        TextView status = weightItem.findViewById(R.id.weight_item_status);
        if (position+1 == weights.size()) {
            status.setText("");
        } else {
            if (weights.get(position).getWeight() > weights.get(position+1).getWeight()) {
                status.setText("Up");
            } else if (weights.get(position).getWeight() < weights.get(position+1).getWeight()) {
                status.setText("Down");
            } else {
                status.setText("");
            }
        }
        Weight row = weights.get(position);
        date.setText(row.getDate());
        weight.setText(Integer.toString(row.getWeight()));
        return weightItem;
    }
}
