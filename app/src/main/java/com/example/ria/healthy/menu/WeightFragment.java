package com.example.ria.healthy.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ria.healthy.R;
import com.example.ria.healthy.WeightFormFragment;

import java.util.ArrayList;

public class WeightFragment extends Fragment{

    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddBtn();
        weights.add(new Weight("01 JAN 2018", 63, "UP"));
        weights.add(new Weight("02 JAN 2018", 64, "DOWN"));
        weights.add(new Weight("03 JAN 2018", 63, "UP"));

        ListView _weightList = getView().findViewById(R.id.weight_list);
        WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                weights
        );
        _weightList.setAdapter(_weightAdapter);
    }

    void initAddBtn() {
        // Config add weight button
        Button _addBtn = getView().findViewById(R.id.weight_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Weight", "GOTO WEIGHT FORM");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
                }
        });
    }
}
