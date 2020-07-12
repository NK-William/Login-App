package com.example.practicingloginapp;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.practicingloginapp.AppProvider.CONTENT_AUTHORITY;
import static com.example.practicingloginapp.AppProvider.CONTENT_AUTHORITY_URI;

public class UsersContract {

    static final String TABLE_NAME = "Users";

    // Users fields
    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String USER_STUDENT_NUMBER = "StudentNumber";
        public static final String USER_FIRST_NAME = "FirstName";
        public static final String USER_SECOND_NAME = "SecondName";
        public static final String USER_SURNAME = "Surname";
        public static final String USER_EMAIL = "Email";
        public static final String USER_PASSWORD = "Password";


        private Columns() {
            // private constructor to prevent instantiation
        }

    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    static long getTaskId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
