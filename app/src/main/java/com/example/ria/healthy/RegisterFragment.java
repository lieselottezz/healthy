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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initRegisterBtn();
    }

    void initRegisterBtn() {
        Button registerBtn = getView().findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = getView().findViewById(R.id.register_email);
                EditText password = getView().findViewById(R.id.register_password);
                EditText confirmPassword = getView().findViewById(R.id.register_confirm_password);
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String confirmPasswordStr = confirmPassword.getText().toString();
                if (passwordChecker(emailStr, passwordStr, confirmPasswordStr)) {
                    Log.d("REGISTERFRAGMENT", "The received information is correct");
                    createAccount(emailStr, passwordStr);
                }
            }
        });
    }

    boolean passwordChecker (String emailStr, String passwordStr, String confirmPasswordStr) {
        if (emailStr.isEmpty() || passwordStr.isEmpty() || confirmPasswordStr.isEmpty()) {
            Log.d("REGISTERFRAGMENT", "Field is empty");
            Utility.toast(getActivity(), "Please fill out your information in the empty field");
        }
        else {
            if (passwordStr.length() < 6) {
                Log.d("REGISTERFRAGMENT", "Password length < 6");
                Utility.toast(getActivity(), "Please fill out the password at least 6 characters");
            }
            else if (passwordStr.equals(confirmPasswordStr) != true) {
                Log.d("REGISTERFRAGMENT", "Password does not match the confirm password");
                Utility.toast(getActivity(), "Password does not match the confirm password");
            }
            else {
                return true;
            }
        }
        return false;
    }

    void createAccount (String emailStr, String passwordStr) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = mAuth.getCurrentUser();
                sendVerifiedEmail(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REGISTERFRAGMENT", e.getMessage());
                Utility.toast(getActivity(), "Unable to create account");
            }
        });
    }

    void sendVerifiedEmail (FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTERFRAGMENT", "Create account success");
                mAuth.signOut();
                Utility.goTo(getActivity(), new LoginFragment());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REGISTERFRAGMENT", e.getMessage());
                Utility.toast(getActivity(), "Can't send a verified email");
            }
        });
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.register_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("REGISTERFRAGMENT", "Goto LoginFragment");
                Utility.goTo(getActivity(), new LoginFragment());
            }
        });
    }
}
