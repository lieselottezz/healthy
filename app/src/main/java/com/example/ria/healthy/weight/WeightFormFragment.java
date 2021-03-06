package com.example.ria.healthy.weight;

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

import com.example.ria.healthy.R;
import com.example.ria.healthy.utility.Extension;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeightFormFragment extends Fragment {

    private FirebaseFirestore mdb = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private Calendar calendar = Calendar.getInstance();
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        Button backBtn = getView().findViewById(R.id.weight_form_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHTFORMFRAGMENT", "Go to WeightFragment");
                Extension.goTo(getActivity(), new WeightFragment());
            }
        });
    }

    void initSaveBtn() {
        Button saveBtn = getView().findViewById(R.id.weight_form_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        EditText date = getView().findViewById(R.id.weight_form_date);
        EditText weight = getView().findViewById(R.id.weight_form_weight);
        String dateStr = date.getText().toString();
        int weightInt = Integer.parseInt(weight.getText().toString());
        Weight weightObj = new Weight(dateStr, weightInt);
        mdb.collection("myfitness")
                .document(auth.getCurrentUser().getUid())
                .collection("weight")
                .document(dateStr)
                .set(weightObj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("WEIGHTFORMFRAGMENT", "The information was saved");
                        Extension.toast(getActivity(), "Saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("WEIGHTFORMFRAGMENT", "Fail to save the information");
                Extension.toast(getActivity(), "Failed");
            }
        });
    }
}
