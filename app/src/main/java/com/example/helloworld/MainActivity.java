package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//Ini contoh komentar
    private static final String TAG = "Aktivitasku";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("Aktivitasku","Check");
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.btnLogin);
        final EditText txtUsername = findViewById(R.id.editTextUsername);
        final EditText txtPassword = findViewById(R.id.editTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(txtUsername.getText().toString().equals("admin")&&txtPassword.getText().toString().equals("admin")){
                    // Masuk ke activity baru
                    Intent intent = new Intent(v.getContext(), HomeScreen.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Username atau Password Anda tidak benar!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}