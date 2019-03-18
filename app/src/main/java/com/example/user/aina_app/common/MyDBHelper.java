package com.example.user.aina_app.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "AINA.db";
    private final static String _TableName = "customer";


    private static final int DB_VERSION = 1; // the version of the database

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE  TABLE " + _TableName +
//                "(_id INTEGER PRIMARY KEY  autoincrement , "
//                + "name" + " text , "
//                + "phone" + " text , "
//                + "address" + " text " + ");");
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS customer");
//        onCreate(db);
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE customer" +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT, "
                    + "address TEXT, "
                    + "phone TEXT );");
        }
        if (oldVersion < 2) {
          //  db.execSQL("ALTER TABLE FAQ ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}
