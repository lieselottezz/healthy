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
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    void initRegisterBtn() {
        // Config register button
        Button _registerBtn = getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _username = getView().findViewById(R.id.register_username);
                EditText _name = getView().findViewById(R.id.register_name);
                EditText _age = getView().findViewById(R.id.register_age);
                EditText _password = getView().findViewById(R.id.register_password);
                String _usernameStr = _username.getText().toString();
                String _nameStr = _name.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_usernameStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("REGISTER", "FIELD IS EMPTY");
                    Toast.makeText(
                            getActivity(),"Please enter your information", Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    if (_usernameStr.equals("admin")) {
                        Log.d("REGISTER", "USER ALREADY EXIST");
                        Toast.makeText(
                                getActivity(),"Username is already exist", Toast.LENGTH_SHORT
                        ).show();
                    }
                    else {
                        Log.d("REGISTER", "GOTO BMI");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new BMIFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
    }
}
