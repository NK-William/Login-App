package com.example.practicingloginapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private String user_student_number;
    private String user_student_password;

    private String user_first_name;
    private String user_second_name;
    private String user_surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRegistration(View view) { // TODO - include implicit intent, proper activity
        Log.d(TAG, "openRegistration: register hyperlink pressed");
        Intent registerIntent = new Intent(this, Register.class);
        startActivity(registerIntent);


    }

    public void login(View view) {
        Log.d(TAG, "login: Button pressed");
        if (TextViewsNotEmpty()) {

            boolean successful = authentication(new User(user_student_number, user_student_password,"","",""));

            if (successful) { //TODO open the proper activity *Joint*
                //TODO putExtra
                Log.d(TAG, "login: preparing to open the menu option activity");
                User validUserOnLogin = new User("", "", user_first_name, user_second_name, user_surname);

                Intent intent = new Intent(this, MenuOption.class);
                intent.putExtra(User.class.getSimpleName(), validUserOnLogin);
                Log.d(TAG, "login: starting option menu activity");
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean TextViewsNotEmpty() {
        Log.d(TAG, "TextViewsNotEmpty: start");
        boolean validLogin;
        TextView student_number = findViewById(R.id.Login_student_number);
        TextView student_password = findViewById(R.id.Login_password);

        user_student_number = student_number.getText().toString();
        user_student_password = student_password.getText().toString();

        if (!user_student_number.equals("") && (user_student_number.length()) == 9) {
            validLogin = true;
        } else if (user_student_number.equals("")) {
            validLogin = false;
            Toast.makeText(this, "Please Enter Student Number", Toast.LENGTH_SHORT).show();
        } else {
            validLogin = false;
            Toast.makeText(this, "Invalid Student Number Length", Toast.LENGTH_SHORT).show();
        }
        if (user_student_password.equals("")) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            validLogin = false;
        }else{

        }
        Log.d(TAG, "TextViewsNotEmpty: return with validLogin = " + validLogin);
        return validLogin;
    }

    private boolean authentication(User user) {
        Log.d(TAG, "authentication: start");

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(UsersContract.CONTENT_URI,
                new String[]{UsersContract.Columns.USER_STUDENT_NUMBER, UsersContract.Columns.USER_PASSWORD,
                UsersContract.Columns.USER_FIRST_NAME, UsersContract.Columns.USER_SECOND_NAME, UsersContract.Columns.USER_SURNAME}, // ***************
                UsersContract.Columns.USER_STUDENT_NUMBER +"=?",
                new String[]{user.getStudent_number()},
                null);

        if(cursor != null && cursor.moveToNext() && cursor.getCount()>0){
            Log.d(TAG, "authentication: cursor is not null");

            Log.d(TAG, "authentication: comparing entered password with the db password = "+cursor.
                    getColumnName(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)));

            Log.d(TAG, "authentication: database password is " + cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)));
            if(user.getPassword().equals(cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)))){
                // retrieving a valid user's details
                user_first_name = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_FIRST_NAME));
                user_second_name = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_SECOND_NAME));
                if(user_second_name == null){
                    user_second_name = "";
                }
                user_surname = cursor.getString(cursor.getColumnIndex(UsersContract.Columns.USER_SURNAME));

                Log.d(TAG, "authentication: exit with return of true since the password match");
                return true;
            }
        }
        Log.d(TAG, "authentication: exit with the return of false because the password doesn't match");
        return false;

    }
}

