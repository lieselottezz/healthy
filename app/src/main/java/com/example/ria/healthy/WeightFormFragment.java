package com.example.ria.healthy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ria.healthy.menu.Weight;
import com.example.ria.healthy.menu.WeightFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeightFormFragment extends Fragment {

    FirebaseFirestore mdb = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // Create object and set listener for DatePicker
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        }
    };

    private void updateLabel() {
        EditText weighDateForm = getView().findViewById(R.id.weight_form_date);
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        weighDateForm.setText(fmt.format(calendar.getTime()));
    }

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
        initDatePicker();
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
        // Config save button
        Button _saveBtn = getView().findViewById(R.id.weight_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHT FORM", "SAVED");
                addObj();
            }
        });
    }

    void initDatePicker() {
        EditText weighDateForm = getView().findViewById(R.id.weight_form_date);
        weighDateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void addObj() {
        // Add Weight object to firestore
        EditText _date = getView().findViewById(R.id.weight_form_date);
        EditText _weight = getView().findViewById(R.id.weight_form_weight);
        String _dateStr = _date.getText().toString();
        Log.d("WEIGHT FORM", _dateStr);
        int _weightInt = Integer.parseInt(_weight.getText().toString());
        Weight _weightObj = new Weight(_dateStr, _weightInt);
        mdb.collection("myfitness")
                .document(auth.getCurrentUser().getUid())
                .collection("weight")
                .document(_dateStr)
                .set(_weightObj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(
                                getActivity(),"Saved",Toast.LENGTH_SHORT
                        ).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("WEIGHT FORM", e.getMessage());
                Toast.makeText(
                        getActivity(),"Failed",Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
