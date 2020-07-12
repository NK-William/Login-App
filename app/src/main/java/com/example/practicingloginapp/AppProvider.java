package com.example.practicingloginapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";
    private AppDatabase mOpenHelper;
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    static final String CONTENT_AUTHORITY = "com.example.practicingloginapp.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int USERS = 100;
    private static final int USERS_ID = 101;

    private static UriMatcher buildUriMatcher() {
        Log.d(TAG, "AppProvider.buildUriMatcher: start");

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME, USERS);
        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME + "/#", USERS_ID);

        Log.d(TAG, "AppProvider.buildUriMatcher: end");
        return matcher;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "AppProvider.onCreate: initialising mOpenHelper");

        mOpenHelper = AppDatabase.getInstance(getContext());

        Log.d(TAG, "AppProvider.onCreate: ends");
        return true;
    }

    @Nullable
    @Override
    //this method will be helpful when retrieving student's surname and initials to display.
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.d(TAG, "AppProvider.query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "AppProvider.query: match is " + match);


        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case USERS:
                Log.d(TAG, "to query the whole table");
                queryBuilder.setTables(UsersContract.TABLE_NAME);
                break;
            case USERS_ID:
                queryBuilder.setTables(UsersContract.TABLE_NAME);
                long taskId = UsersContract.getTaskId(uri);
                Log.d(TAG, "to query the record with id " + taskId);
                queryBuilder.appendWhere(UsersContract.Columns._ID + " = " + taskId);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase database = mOpenHelper.getReadableDatabase();

        Log.d(TAG, "AppProvider.query: exit returning cursor");
        return queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USERS:
                return UsersContract.CONTENT_TYPE;

            case USERS_ID:
                return UsersContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Log.d(TAG, "AppProvider.insert, called with uri:" + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase database;

        Uri returnUri;
        long recordId;

        switch (match) {
            case USERS:
                database = mOpenHelper.getWritableDatabase();
                recordId = database.insert(UsersContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = UsersContract.buildTaskUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        Log.d(TAG, "Exiting AppProvider.insert, returning " + returnUri);
        return returnUri;

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //TODO add delete if needed in advance
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Log.d(TAG, "AppProvider.update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase database;
        int count;

        String selectionCriteria;

        switch (match) {
            case USERS: // TODO remove if not used.
                Log.d(TAG, "update: table");
                database = mOpenHelper.getWritableDatabase();
                count = database.update(UsersContract.TABLE_NAME, values, selection, selectionArgs); // number of records updated
                break;

            case USERS_ID:
                Log.d(TAG, "update: record");
                database = mOpenHelper.getWritableDatabase();
                long taskId = UsersContract.getTaskId(uri);
                selectionCriteria = UsersContract.Columns._ID + " = " + taskId; // building where clause

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")"; // this provides for more flexibility (hence mandatory)
                }
                // the following substitute selection by selectionCriteria
                count = database.update(UsersContract.TABLE_NAME, values, selectionCriteria, selectionArgs); // updating with where clause
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        Log.d(TAG, "Exiting AppProvider.update, returning " + count);
        return count;
    }
}
