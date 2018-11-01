package com.example.ria.healthy.sleep;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.example.ria.healthy.R;
import com.example.ria.healthy.utility.DBHelper;
import com.example.ria.healthy.utility.Extension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SleepFormFragment extends Fragment {

    private Button backBtn;
    private Button saveBtn;
    private EditText sleepDateForm;
    private EditText sleepTimeForm;
    private EditText wakeupTimeForm;
    private String sleepDateStr;
    private String sleepTimeStr;
    private String wakeupTimeStr;
    private String totalSleepTimeStr;
    private DBHelper dbHelper;
    private int id = -1;

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
        sleepDateForm = getView().findViewById(R.id.sleep_form_date);
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        sleepDateForm.setText(fmt.format(calendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initSaveBtn();
        initDatePicker();
        initTimePicker();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            Log.d("SLEEPFORMFRAGMENT", Integer.toString(id));
            setEditText();
        }
    }

    void initBackBtn() {
        backBtn = getView().findViewById(R.id.sleep_form_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SLEEPFORMFRAGMENT", "Go to SleepFragment");
                Extension.goTo(getActivity(), new SleepFragment());
            }
        });
    }

    void initSaveBtn(){
        saveBtn = getView().findViewById(R.id.sleep_form_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addObj();
            }
        });
    }

    void initDatePicker() {
        sleepDateForm = getView().findViewById(R.id.sleep_form_date);
        sleepDateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void initTimePicker() {
        sleepTimeForm = getView().findViewById(R.id.sleep_form_sleep_time);
        sleepTimeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String output = String.format("%02d:%02d", selectedHour, selectedMinute);
                        sleepTimeForm.setText(output);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        wakeupTimeForm = getView().findViewById(R.id.sleep_form_wakeup_time);
        wakeupTimeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String output = String.format("%02d:%02d", selectedHour, selectedMinute);
                        wakeupTimeForm.setText(output);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    void addObj() {
        dbHelper = new DBHelper(getActivity());
        sleepDateStr = ((EditText) getView().findViewById(R.id.sleep_form_date)).getText().toString();
        sleepTimeStr = ((EditText) getView().findViewById(R.id.sleep_form_sleep_time)).getText().toString();
        wakeupTimeStr = ((EditText) getView().findViewById(R.id.sleep_form_wakeup_time)).getText().toString();
        totalSleepTimeStr = calTotalSleepTime(sleepTimeStr, wakeupTimeStr);
        Log.d("SLEEPFORMFRAGMENT", totalSleepTimeStr);
        Sleep sleep = new Sleep();
        sleep.setDate(sleepDateStr);
        sleep.setSleepTime(sleepTimeStr);
        sleep.setWakeupTime(wakeupTimeStr);
        sleep.setTotalSleepTime(totalSleepTimeStr);
        if (id == -1) {
            dbHelper.addSleep(sleep);
            Extension.toast(getActivity(), "Adding complete");
            Extension.goTo(getActivity(), new SleepFragment());
            Log.d("SLEEPFORMFRAGMENT", "Adding complete");
        } else {
            sleep.setId(id);
            dbHelper.updateSleep(sleep);
            Extension.toast(getActivity(), "Updating complete");
            Extension.goTo(getActivity(), new SleepFragment());
            Log.d("SLEEPFORMFRAGMENT", "Updating complete");
        }
    }

    public String calTotalSleepTime(String sleepTime, String wakeupTime) {
        String time = null;
        long diff = 0;
        long diffMinutes = 0;
        long diffHours = 0;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date start = format.parse(sleepTime);
            Date stop = format.parse(wakeupTime);
            if (start.getTime() > stop.getTime()) {
                diff = 86400000 - (start.getTime() - stop.getTime());
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;
            } else {
                diff = stop.getTime() - start.getTime();
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;
            }
            time = String.format("%02d:%02d", diffHours, diffMinutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    void setEditText() {
        dbHelper = new DBHelper(getActivity());
        Sleep sleep = dbHelper.getSleep(id);
        sleepDateForm.setText(sleep.getDate());
        sleepTimeForm.setText(sleep.getSleepTime());
        wakeupTimeForm.setText(sleep.getWakeupTime());
    }
}
