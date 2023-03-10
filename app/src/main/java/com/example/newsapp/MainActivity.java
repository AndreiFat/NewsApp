package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name, email, password;
    private CheckBox keepMeLogIn;

    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor mySharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide(); // nu mai afiseaza action bar-ul;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, // elimina titlul din action bar
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        keepMeLogIn = findViewById(R.id.keepMeLogIn);

        mySharedPreferences = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        mySharedPreferencesEditor = mySharedPreferences.edit();
        String autoFill = mySharedPreferences.getString("rememberUser","default");

        if(autoFill.equals("True")){
            name.setText(mySharedPreferences.getString("name","default"));
            email.setText(mySharedPreferences.getString("email","default"));
            password.setText(mySharedPreferences.getString("password","default"));
            keepMeLogIn.setChecked(true);
        }

    }

    public void goToHome(View view){
        String nameToSend = name.getText().toString();
        String emailToSend = email.getText().toString();
        String passwordToSend = password.getText().toString();
        Boolean keepMeLogInToSend = keepMeLogIn.isChecked();

        Intent serviceIntent = new Intent(this, SaveUserService.class);
        serviceIntent.putExtra("name", nameToSend);
        serviceIntent.putExtra("email", emailToSend);
        serviceIntent.putExtra("password", passwordToSend);
        serviceIntent.putExtra("keepLogIn", keepMeLogInToSend);
        startService(serviceIntent);
        Toast.makeText(this, "User saved!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
}