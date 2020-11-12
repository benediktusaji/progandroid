package com.example.helloworld.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.helloworld.models.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final String SHARED_PREF_NAME = "session";
    private final String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        //Save user's session
        String email = user.getEmail();
        editor.putString(SESSION_KEY,email).commit();
    }

    public boolean getSession(){
        //return user whose session is saved
        if(sharedPreferences.getString(SESSION_KEY,null)!=null){
            return true;
        }else{
            return false;
        }
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,null).commit();
    }
}
