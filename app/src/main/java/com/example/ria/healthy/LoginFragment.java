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
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .addToBackStack(null)
                    .commit();
        }
        initLoginBtn();
        initRegisterBtn();
    }

    void initLoginBtn() {
        // Config login button
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userEmail = getView().findViewById(R.id.login_user_email);
                EditText _password = getView().findViewById(R.id.login_user_password);
                String _userEmailStr = _userEmail.getText().toString();
                String _passwordStr = _password.getText().toString();
                Log.d("LOGIN", "On click");
                Log.d("LOGIN", "USER EMAIL = " + _userEmail);

                if (_userEmailStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("LOGIN", "USER OR PASSWORD IS EMPTY");
                    Toast.makeText(
                            getActivity(),"Please enter your email or password",Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    signInWithEmail(_userEmailStr, _passwordStr);
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

    void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && mAuth.getCurrentUser().isEmailVerified()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LOGIN", "signInWithEmail:success");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new MenuFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    Log.d("LOGIN", "signInWithEmail:failure", task.getException());
                    mAuth.signOut();
                    Toast.makeText(
                            getActivity(),"Can't Login",Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}
