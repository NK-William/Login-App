package com.example.practicingloginapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";

    private static final String DATABASE_NAME = "UserLogin.db";
    private static final int DATABASE_VERSION = 1;

    // implement AppDatabase as singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase:  in the Constructor");
    }

    static AppDatabase getInstance(Context context) {
        Log.d(TAG, "Entering AppDatabase.getInstance");
        if (instance == null) {
            Log.d(TAG, "AppDatabase.getInstance: new database helper instance created");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "AppDatabase.onCreate: start");
        String sSQL;

        sSQL = "CREATE TABLE " + UsersContract.TABLE_NAME + " ("                     /// TTTTTTTTTTEST**********************************
                + UsersContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UsersContract.Columns.USER_STUDENT_NUMBER + " INTEGER NOT NULL, "
                + UsersContract.Columns.USER_FIRST_NAME + " TEXT NOT NULL, "
                + UsersContract.Columns.USER_SECOND_NAME + " TEXT, "
                + UsersContract.Columns.USER_SURNAME + " TEXT NOT NULL, "
                + UsersContract.Columns.USER_EMAIL + " TEXT NOT NULL, "
                + UsersContract.Columns.USER_PASSWORD + " INTEGER NOT NULL);";

        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "AppDatabase.onCreate: ends");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "AppDatabase.onUpgrade: starts");
        switch(oldVersion){
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }

    }
}
