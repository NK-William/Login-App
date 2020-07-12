package com.example.practicingloginapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";

    EditText studentNumber;
    EditText firstName;
    EditText secondName;
    EditText surname;
    EditText email;
    EditText password;

    String string_studentNumber;
    String string_firstName;
    String string_secondName;
    String string_surname;
    String string_email;
    String string_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void register(View view) {
        Log.d(TAG, "register: register button pressed");

        if(validTextViews()){
            if(!isStudentNumberExist(string_studentNumber)){
                Log.d(TAG, "register: starting to store data because the student number entered doesn't exist");
            ContentResolver contentResolver = getContentResolver();
                Log.d(TAG, "register: after getContentResolver");
                ContentValues values = new ContentValues();
                Log.d(TAG, "register: after new ContentValues");
                values.put(UsersContract.Columns.USER_STUDENT_NUMBER, string_studentNumber);
                values.put(UsersContract.Columns.USER_FIRST_NAME, string_firstName);
                Log.d(TAG, "register: getting two values so far");
                if(!string_secondName.isEmpty()){
                    Log.d(TAG, "register: getting the second name if not empty");
                    values.put(UsersContract.Columns.USER_SECOND_NAME, string_secondName);
                }
                values.put(UsersContract.Columns.USER_SURNAME, string_surname);
                values.put(UsersContract.Columns.USER_EMAIL, string_email);
                values.put(UsersContract.Columns.USER_PASSWORD, string_password);
                Log.d(TAG, "register: after getting all values");

                Log.d(TAG, "register: now inserting to database");
                contentResolver.insert(UsersContract.CONTENT_URI , values);
                Log.d(TAG, "register: after inserting to database");

                Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "register: Toast and then finish(closing the current activity)");
                finish();

            }


        }
    }

    private boolean validTextViews(){
        Log.d(TAG, "validTextViews: start");
        boolean validRegister;

        studentNumber = findViewById(R.id.registerStudentNumber);
        firstName = findViewById(R.id.registerFirstName);
        secondName = findViewById(R.id.registerSecondName);
        surname = findViewById(R.id.registerSurname);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);

        string_studentNumber = studentNumber.getText().toString();
        string_firstName = firstName.getText().toString();
        string_secondName = secondName.getText().toString();
        string_surname = surname.getText().toString();
        string_email = email.getText().toString();
        string_password = password.getText().toString();

        if (!string_studentNumber.equals("") && (string_studentNumber.length()) == 9) {
            validRegister = true;
        } else if (string_studentNumber.equals("")) {
            validRegister = false;
            Toast.makeText(this, "Please Enter Student Number", Toast.LENGTH_SHORT).show();
        } else {
            validRegister = false;
            Toast.makeText(this, "Invalid Student Number Length", Toast.LENGTH_SHORT).show();
        }

        if(string_firstName.isEmpty()){
            validRegister = false;
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }
        if(string_surname.isEmpty()){
            validRegister = false;
            Toast.makeText(this, "Enter your surname", Toast.LENGTH_SHORT).show();
        }
        if(string_email.isEmpty()){
            validRegister = false;
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
        }
        if(string_password.isEmpty()){
            validRegister = false;
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
        }else{
            if(string_password.length()<5){
                validRegister = false;
                Toast.makeText(this, "Password is too short, must have at least 5 characters", Toast.LENGTH_SHORT).show();
            }
        }
        Log.d(TAG, "validTextViews: return with validRegister = " +validRegister);
        return validRegister;
    }


    private boolean isStudentNumberExist(String string_studentNumber){
        Log.d(TAG, "isStudentNumberExist: start");

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(UsersContract.CONTENT_URI,
                new String[]{UsersContract.Columns.USER_STUDENT_NUMBER},
                UsersContract.Columns.USER_STUDENT_NUMBER + "=?",
                new String[]{string_studentNumber},
                null);

        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            //if cursor has value then in user database there is user associated with this given student number, so return true
            Toast.makeText(this, "The student number exist, enter unique student number", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "isStudentNumberExist: return true");
            return true;
        }
        // if student does not exist return false
        Log.d(TAG, "isStudentNumberExist: return false");
        return false;
    }
}
