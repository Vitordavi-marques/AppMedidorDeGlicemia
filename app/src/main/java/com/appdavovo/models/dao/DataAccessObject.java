package com.appdavovo.models.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.appdavovo.database.DatabaseHelper;

import java.io.IOException;

public class DataAccessObject {

    protected DatabaseHelper databaseHelper;
    protected SQLiteDatabase db;

    public DataAccessObject(Context context) {
        databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
}
