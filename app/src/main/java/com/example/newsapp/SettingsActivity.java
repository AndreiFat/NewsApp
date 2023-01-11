package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor mySharedPreferencesEditor;
    private EditText name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(1,90,255)));

        mySharedPreferences = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        mySharedPreferencesEditor = mySharedPreferences.edit();
        String autoFill = mySharedPreferences.getString("rememberUser","default");

        if(autoFill.equals("True")){
            name.setText(mySharedPreferences.getString("name","default"));
            email.setText(mySharedPreferences.getString("email","default"));
            password.setText(mySharedPreferences.getString("password","default"));
        }

    }

    public void onClickChangeSettings(View view){
        String nameToSend = name.getText().toString();
        String emailToSend = email.getText().toString();
        String passwordToSend = password.getText().toString();

        Intent serviceIntent = new Intent(this, SaveUserService.class);
        serviceIntent.putExtra("name", nameToSend);
        serviceIntent.putExtra("email", emailToSend);
        serviceIntent.putExtra("password", passwordToSend);
        serviceIntent.putExtra("keepLogIn", true);
        startService(serviceIntent);
        Toast.makeText(this, "User saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}