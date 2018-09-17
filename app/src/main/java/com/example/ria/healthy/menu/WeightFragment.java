package com.example.ria.healthy.menu;

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

import com.example.ria.healthy.R;
import com.example.ria.healthy.WeightFormFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class WeightFragment extends Fragment{

    ArrayList<Weight> weights = new ArrayList<>();

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddBtn();

        // Intialize ListView of history

        ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                weights
        );
        _weightList.setAdapter(_weightAdapter);

        // Get object from Firestore

        weights.clear();

        mdb.collection("myfitness")
                .document(auth.getCurrentUser().getUid())
                .collection("weight")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qsnap) {
                        for (QueryDocumentSnapshot doc : qsnap) {
                            Log.d("WEIGHTFRAGMENT", doc.toObject(Weight.class).getDate());
                            weights.add(doc.toObject(Weight.class));
                            Collections.sort(weights);
                            _weightAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("WEIGHTFRAGMENT", e.getMessage());
                    }
                });
    }

    void initAddBtn() {
        // Config add weight button
        Button _addBtn = getView().findViewById(R.id.weight_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Weight", "GOTO WEIGHT FORM");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
                }
        });
    }
}
