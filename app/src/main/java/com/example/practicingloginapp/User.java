package com.example.practicingloginapp;

import java.io.Serializable;

public class User implements Serializable {
    // do something here if code malfunction

    private String student_number;
    private String password;
    private String first_name;
    private String second_name;
    private String surname;

    public User(String student_number, String password, String first_name, String second_name, String surname) {
        this.student_number = student_number;
        this.password = password;
        this.first_name = first_name ;
        this.second_name = second_name;
        this.surname = surname;
    }

    public String getStudent_number() {
        return student_number;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getSurname() {
        return surname;
    }
}
