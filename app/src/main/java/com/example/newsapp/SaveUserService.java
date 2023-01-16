package com.example.newsapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

public class SaveUserService extends Service {

    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor mySharedPreferencesEditor;

    public SaveUserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroy!");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mySharedPreferences = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        mySharedPreferencesEditor = mySharedPreferences.edit();

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        Boolean keepLogIn = intent.getBooleanExtra("keepLogIn",false);

        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        System.out.println(keepLogIn);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(keepLogIn){
                    mySharedPreferencesEditor.putString("name", name);
                    mySharedPreferencesEditor.putString("email", email);
                    mySharedPreferencesEditor.putString("password", password);
                    mySharedPreferencesEditor.putString("rememberUser", "True");
                    mySharedPreferencesEditor.commit();
                    System.out.println("SAVED!");
                }
                else{
                    mySharedPreferencesEditor.putString("rememberUser","False");
                    mySharedPreferencesEditor.commit();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        System.out.println("Removed!");
        super.onTaskRemoved(rootIntent);
    }
}