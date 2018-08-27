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
        // Config calculate button
        Button _calculateBtn = getView().findViewById(R.id.bmi_calculate_btn);
        _calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _height = getView().findViewById(R.id.bmi_height);
                EditText _weight = getView().findViewById(R.id.bmi_weight);
                TextView _yourbmiTxtView = getView().findViewById(R.id.bmi_yourbmi_txtview);
                TextView _bmivalueTxtView = getView().findViewById(R.id.bmi_bmi_value_txtview);
                if (_height.getText().toString().isEmpty() || _weight.getText().toString().isEmpty()) {
                    Log.d("BMI", "FIELD IS EMPTY");
                }
                else {
                    double _heightValue = Double.parseDouble(_height.getText().toString()) * 0.01;
                    double _weightValue = Double.parseDouble(_weight.getText().toString());
                    double _bmiValue = _weightValue / (_heightValue * _heightValue);
                    DecimalFormat df = new DecimalFormat(".##");
                    String _bmiStr = df.format(_bmiValue);
                    Log.d("BMI", "BMI VALUE IS " + _bmiStr);
                    _yourbmiTxtView.setText("Your BMI");
                    _bmivalueTxtView.setText(_bmiStr);
                }

            }
        });
    }

    void initBackBtn() {
        // Config back button
        Button _backBtn = getView().findViewById(R.id.bmi_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BMI", "BACK TO MENU");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
