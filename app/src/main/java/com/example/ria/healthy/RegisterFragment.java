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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _confirmPassword = getView().findViewById(R.id.register_confirm_password);
                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();
                String _confirmPasswordStr = _confirmPassword.getText().toString();
                initCheckPassword(_emailStr, _passwordStr, _confirmPasswordStr);
//                    if (_emailStr.equals("admin")) {
//                        Log.d("REGISTER", "USER ALREADY EXIST");
//                        Toast.makeText(
//                                getActivity(),"Username is already exist", Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                    else {
//                        Log.d("REGISTER", "GOTO BMI");
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_view, new BMIFragment())
//                                .addToBackStack(null)
//                                .commit();
//                    }
//                }
            }
        });
    }

    void initCheckPassword (String _emailStr, String _passwordStr, String _confirmPasswordStr) {
        if (_emailStr.isEmpty() || _passwordStr.isEmpty() || _confirmPasswordStr.isEmpty()) {
            Log.d("REGISTER", "FIELD IS EMPTY");
            Toast.makeText(
                    getActivity(),"Please fill out your information in the empty field", Toast.LENGTH_SHORT
            ).show();
        }
        else {
            if (_passwordStr.length() < 6) {
                Log.d("REGISTER", "LENGTH OF PASSWORD < 6");
                Toast.makeText(
                        getActivity(),"Please fill out the password at least 6 characters", Toast.LENGTH_SHORT
                ).show();
            }
            else if (_passwordStr.equals(_confirmPasswordStr) != true) {
                Log.d("REGISTER", "PASSWORD DOESN'T MATCH THE CONFIRM PASSWORD");
                Toast.makeText(
                        getActivity(),"Password does not match the confirm password", Toast.LENGTH_SHORT
                ).show();
            }
            else {
                Log.d("REGISTER", "PASSWORD IS CORRECT BY STATEMENT");
                createAccount(_emailStr, _passwordStr);
            }
        }
    }

    void createAccount (String _emailStr, String _passwordStr) {
        mAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d("REGISTER", "SUCCESS TO SEND VERIFIED EMAIL");
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser _user = mAuth.getCurrentUser();
                sendVerifiedEmail(_user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REGISTER", "FAIL TO SEND VERIFIED EMAIL");
                Toast.makeText(
                        getActivity(),"Can't create an account", Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    void sendVerifiedEmail (FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER", "ALREADY SENT");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REGISTER", "CAN'T SEND VERIFIED EMAIL");
                Toast.makeText(
                        getActivity(),"Can't send a verified email", Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
