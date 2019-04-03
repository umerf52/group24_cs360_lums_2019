package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {

    Button student_button;
    Button tutor_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        student_button = findViewById(R.id.student_button);
        tutor_button = findViewById(R.id.tutor_button);

        tutor_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(StartupActivity.this, LoginActivity.class);
                StartupActivity.this.startActivity(myIntent);
            }
        });

        student_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(StartupActivity.this, SearchActivity.class);
                StartupActivity.this.startActivity(myIntent);
            }
        });
    }


}