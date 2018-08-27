package com.example.ria.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ria.healthy.menu.WeightFragment;

import java.util.ArrayList;

public class MenuFragment extends Fragment{

    ArrayList<String> _menu = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _menu.clear();
        _menu.add("BMI");
        _menu.add("Weight");

        initMenu();
    }

    void initMenu() {
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                _menu
        );
        ListView _menuList = getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU", "Select " + _menu.get(i));
                if (_menu.get(i).equals("BMI")) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else if (_menu.get(i).equals("Weight")) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}
