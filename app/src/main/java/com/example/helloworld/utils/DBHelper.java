package com.example.helloworld.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.helloworld.models.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ProgAndro.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USERS = "USERS";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE "+TABLE_USERS+"(USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        onCreate(db);
    }

    public void addRecord(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("USERNAME",user.getUsername());
        value.put("PASSWORD",user.getPassword());

        db.insert(TABLE_USERS, null,value);
        db.close();
    }

    public Boolean checkUserExists(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE USERNAME = ?", new String[]{username});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE USERNAME = ?  AND PASSWORD = ?", new String[]{username, password});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return new User(cursor.getString(0), cursor.getString(1));
        }else{
            return null;
        }
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User User = new User(cursor.getString(0),cursor.getString(1));
                userList.add(User);
            } while (cursor.moveToNext());
        }
        return userList;
    }
}
