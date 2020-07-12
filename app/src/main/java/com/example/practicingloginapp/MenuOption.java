package com.example.practicingloginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MenuOption extends AppCompatActivity {
    private static final String TAG = "MenuOption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);

        Bundle arguments = getIntent().getExtras();
        User user = (User) arguments.getSerializable(User.class.getSimpleName());

        TextView textView = findViewById(R.id.student_details);


        char loggedIn_user_first_name_first_letter = user.getFirst_name().charAt(0);
        String loggedIn_user_surname = user.getSurname();

        if (user.getSecond_name().equals("")) {
            String one_name_user_details = loggedIn_user_surname + " " + loggedIn_user_first_name_first_letter;
            textView.setText(one_name_user_details);
        } else {
            char loggedIn_user_second_name_first_letter = user.getSecond_name().charAt(0);
            String two_names_user_details = loggedIn_user_surname + " " + loggedIn_user_first_name_first_letter + "" + loggedIn_user_second_name_first_letter;
            textView.setText(two_names_user_details);
        }


    }
}
