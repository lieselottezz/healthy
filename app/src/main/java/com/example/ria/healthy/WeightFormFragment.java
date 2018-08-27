package com.example.ria.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ria.healthy.menu.WeightFragment;

public class WeightFormFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initSaveBtn();
    }

    void initBackBtn() {
        // Config back button
        Button _backBtn = getView().findViewById(R.id.weight_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHT FORM", "BACK TO WEIGHT");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void initSaveBtn() {

    }
}
