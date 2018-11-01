package com.example.ria.healthy.weight;

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

import com.example.ria.healthy.MenuFragment;
import com.example.ria.healthy.R;
import com.example.ria.healthy.utility.Extension;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class WeightFragment extends Fragment{

    private ArrayList<Weight> weights = new ArrayList<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initAddBtn();
        initHistory();
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.weight_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHTFRAGMENT", "Goto MenuFragment");
                Extension.goTo(getActivity(), new MenuFragment());
            }
        });
    }

    void initAddBtn() {
        Button addBtn = getView().findViewById(R.id.weight_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WEIGHTFRAGMENT", "Goto WeightFormFragment");
                Extension.goTo(getActivity(), new WeightFormFragment());
            }
        });
    }

    void initHistory() {
        ListView weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                weights
        );
        weightList.setAdapter(weightAdapter);
        weights.clear();
        Log.d("WEIGHTFRAGMENT", "Loading history...");
        mdb.collection("myfitness")
                .document(auth.getCurrentUser().getUid())
                .collection("weight")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qsnap) {
                        for (QueryDocumentSnapshot doc : qsnap) {
                            weights.add(doc.toObject(Weight.class));
                            Collections.sort(weights);
                            weightAdapter.notifyDataSetChanged();
                        }
                        Log.d("WEIGHTFRAGMENT", "Loading history success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("WEIGHTFRAGMENT", "Fail to loading history");
                    }
                });
    }
}
