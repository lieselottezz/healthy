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

import com.example.ria.healthy.utility.Extension;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("LOGINFRAGMENT", "Already login with " + currentUser.getEmail());
            Extension.goTo(getActivity(), new MenuFragment());
        }
        initLoginBtn();
        initRegisterBtn();
    }

    void initLoginBtn() {
        Button loginBtn = getView().findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userEmail = getView().findViewById(R.id.login_user_email);
                EditText password = getView().findViewById(R.id.login_user_password);
                String userEmailStr = userEmail.getText().toString();
                String passwordStr = password.getText().toString();
                if (userEmailStr.isEmpty() || passwordStr.isEmpty()) {
                    Log.d("LOGINFRAGMENT", "Field is empty");
                    Extension.toast(getActivity(), "Please enter your email or password");
                } else {
                    Log.d("LOGINFRAGMENT", "Login with " + userEmailStr);
                    signInWithEmail(userEmailStr, passwordStr);
                }
            }
        });
    }

    void initRegisterBtn() {
        TextView registerBtn = getView().findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGINFRAGMENT", "Go to RegisterFragment");
                Extension.goTo(getActivity(), new RegisterFragment());
            }
        });
    }

    void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && mAuth.getCurrentUser().isEmailVerified()) {
                    Log.d("LOGINFRAGMENT", "Login success");
                    Extension.goTo(getActivity(), new MenuFragment());
                } else {
                    Log.d("LOGINFRAGMENT", "Login fail", task.getException());
                    Extension.toast(getActivity(), "Unable to login");
                    mAuth.signOut();
                }
            }
        });
    }
}
