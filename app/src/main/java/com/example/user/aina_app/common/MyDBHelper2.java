package com.example.user.aina_app.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper2 extends SQLiteOpenHelper {
    // 資料庫名稱
    private static final String DATABASE_NAME = "mydata.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    private static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;

    // 建構子，在一般的應用都不需要修改
    public MyDBHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CostumerDao.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + CostumerDao.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }

    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    // MyDBHelper2 is not singleton, it's writableDatabase is Singleton
    // lazy initialization
    // AsyncTask
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper2(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }
        return database;
    }
}
