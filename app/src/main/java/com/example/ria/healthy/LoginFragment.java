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
import android.widget.Toast;

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoginBtn();
        initRegisterBtn();
    }

    void initLoginBtn() {
        // Config login button
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = getView().findViewById(R.id.login_user_id);
                EditText _password = getView().findViewById(R.id.login_user_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                Log.d("LOGIN", "On click");
                Log.d("LOGIN", "USER ID = " + _userIdStr);
                Log.d("LOGIN", "PASSWORD = " + _passwordStr);

                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("LOGIN", "USER OR PASSWORD IS EMPTY");
                    Toast.makeText(
                            getActivity(),"Please enter username or password",Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    if (_userIdStr.equals("admin") && _passwordStr.equals("admin")) {
                        Log.d("LOGIN", "GOTO BMI");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new BMIFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                    else {
                        Log.d("LOGIN", "INVALID USER OR PASSWORD");
                        Toast.makeText(
                                getActivity(),"Invalid username or password", Toast.LENGTH_SHORT
                        ).show();
                    }
                }

            }
        });
    }

    void initRegisterBtn() {
        // Config register button
        TextView _registerBtn = getView().findViewById(R.id.login_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGIN", "GOTO REGISTER");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
