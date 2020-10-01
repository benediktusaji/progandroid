package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
//Ini contoh komentar
    private static final String TAG = "Aktivitasku";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Aktivitasku","Check");
        setContentView(R.layout.activity_main);
    }
}