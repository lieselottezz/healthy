package com.example.ria.healthy.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ria.healthy.MenuFragment;
import com.example.ria.healthy.R;
import com.example.ria.healthy.utility.DBHelper;
import com.example.ria.healthy.utility.Extension;

import java.util.ArrayList;
import java.util.Collections;

public class SleepFragment extends Fragment {

    private DBHelper dbHelper;
    private ArrayList<Sleep> sleeps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initAddBtn();
        initHistory();
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.sleep_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SLEEPFRAGMENT", "Goto MenuFragment");
                Extension.goTo(getActivity(), new MenuFragment());
            }
        });
    }

    void initAddBtn() {
        Button addBtn = getView().findViewById(R.id.sleep_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SLEEPFRAGMENT", "Goto SleepFormFragment");
                Extension.goTo(getActivity(), new SleepFormFragment());
            }
        });
    }

    void initHistory() {
        dbHelper = new DBHelper(getActivity());
        ListView sleepList = getView().findViewById(R.id.sleep_list);
        sleeps.clear();
        sleeps = dbHelper.getAllSleepObjects();
        Collections.sort(sleeps);
//        for (Sleep s : sleeps) {
//            Log.d("SLEEPFRAGMENT", "History: " + s.getDate());
//        }
        SleepAdapter sleepAdapter = new SleepAdapter(
                getActivity(),
                R.layout.fragment_sleep_item,
                sleeps
        );
        sleepList.setAdapter(sleepAdapter);
        sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sleep s = (Sleep) adapterView.getItemAtPosition(i);
                Log.d("SLEEPFRAGMENT", Integer.toString(s.getId()));
                Bundle bundle = new Bundle();
                bundle.putInt("id", s.getId());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SleepFormFragment sff = new SleepFormFragment();
                sff.setArguments(bundle);
                ft.replace(R.id.main_view, sff);
                ft.commit();
            }
        });
    }
}
