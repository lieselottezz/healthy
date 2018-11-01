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
import android.widget.EditText;
import android.widget.TextView;

import com.example.ria.healthy.utility.Extension;

import java.text.DecimalFormat;

public class BMIFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCalculateBtn();
        initBackBtn();
    }

    void initCalculateBtn() {
        Button calculateBtn = getView().findViewById(R.id.bmi_calculate_btn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText height = getView().findViewById(R.id.bmi_height);
                EditText weight = getView().findViewById(R.id.bmi_weight);
                String heightStr =  height.getText().toString();
                String weightStr =  weight.getText().toString();
                TextView yourbmiTxtView = getView().findViewById(R.id.bmi_yourbmi_txtview);
                TextView bmivalueTxtView = getView().findViewById(R.id.bmi_bmi_value_txtview);
                if (!heightStr.isEmpty() || !weightStr.isEmpty()) {
                    double heightValue = Double.parseDouble(heightStr) * 0.01;
                    double weightValue = Double.parseDouble(weightStr);
                    double bmiValue = weightValue / (heightValue * heightValue);
                    DecimalFormat df = new DecimalFormat(".##");
                    String _bmiStr = df.format(bmiValue);
                    Log.d("BMIFRAGMENT", "BMI = " + _bmiStr);
                    yourbmiTxtView.setText("Your BMI");
                    bmivalueTxtView.setText(_bmiStr);
                } else {
                    Log.d("BMIFRAGMENT", "Field is empty");
                }
            }
        });
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.bmi_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BMIFRAGMENT", "Goto MenuFragment");
                Extension.goTo(getActivity(), new MenuFragment());
            }
        });
    }
}
