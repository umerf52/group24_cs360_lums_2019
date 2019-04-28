package com.apps.edu_gate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends BaseActivity {

    private EditText old_password;
    private EditText new_password;
    private EditText re_new_password;
    private FirebaseAuth firebaseAuth;
    Button change_password_button;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Change Password");

        //findViewById(R.id.change_password_button).setOnClickListener(this);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        re_new_password = findViewById(R.id.re_new_password);
        change_password_button = findViewById(R.id.change_password_button);
        dialog = new ProgressDialog(this);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        change_password_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                changePassword();
            }
        });
    }


    public void changePassword() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        showProgressDialog();

        if (user != null) {
            String email = user.getEmail();
            String oldpass = old_password.getText().toString();

            AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideProgressDialog();
                    if (true) {
                        String newpass = new_password.getText().toString();
                        String confirmpass = re_new_password.getText().toString();

                        if(!newpass.equals(confirmpass)){
                            Toast.makeText(getApplicationContext(), "New Password and Confirm Password don't match", Toast.LENGTH_LONG).show();
                            return;
                        }

                        user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Your password has been changed", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(ChangePasswordActivity.this, ViewYourProfileActivity.class);
                                    ChangePasswordActivity.this.startActivity(myIntent);
                                }
                            }

                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Old Password. Authentication Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }


}
