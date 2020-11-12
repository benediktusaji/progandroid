package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helloworld.models.User;
import com.example.helloworld.utils.DBHelper;

public class SignUpActivity extends AppCompatActivity {

    DBHelper db;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText)findViewById(R.id.editTextTextEmail);
        password = (EditText)findViewById(R.id.editTextTextPassword);
        db = new DBHelper(this);
    }

    public void moveToLoginActivity(View v) {
        Intent intent = new Intent(v.getContext(),LoginActivity.class);
        startActivity(intent);
    }

    public void signUp(View v) {
        if(!db.checkUserExists(email.getText().toString())){
            db.addRecord(new User(email.getText().toString(),password.getText().toString()));
            Toast.makeText(getApplicationContext(),"Berhasil mendaftar",Toast.LENGTH_SHORT).show();
            Log.d("SignUp", "signUp: "+ email.toString()+" "+ password.toString());
            moveToLoginActivity(v);
        }else{
            Toast.makeText(getApplicationContext(),"User Exist",Toast.LENGTH_SHORT).show();
        }

    }
}