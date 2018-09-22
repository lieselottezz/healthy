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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment{

    private FirebaseAuth mAuth;
    private ArrayList<String> menu = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        menu.clear();
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Sign out");
        initMenu();
    }

    void initMenu() {
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );
        ListView menuList = getView().findViewById(R.id.menu_list);
        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (menu.get(i).equals("BMI")) {
                    Log.d("MENUFRAGEMNT", "Goto BMIFragment");
                    Utility.goTo(getActivity(), new BMIFragment());
                } else if (menu.get(i).equals("Weight")) {
                    Log.d("MENUFRAGEMNT", "Goto WeightFragment");
                    Utility.goTo(getActivity(), new WeightFragment());
                } else if (menu.get(i).equals("Sign out")) {
                    Log.d("MENUFRAGEMNT", "Sign out");
                    mAuth.signOut();
                    Utility.goTo(getActivity(), new LoginFragment());
                }
            }
        });
    }
}
