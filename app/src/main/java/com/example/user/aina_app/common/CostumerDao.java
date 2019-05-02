package com.example.user.aina_app.common;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CostumerDao {
    // 表格名稱
    public static final String TABLE_NAME = "customer";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String NAME_COLUMN = "name";
    public static final String PHONE_COLUMN = "phone";
    public static final String ADDRESS_COLUMN = "address";


    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT NOT NULL, " +
                    PHONE_COLUMN + " INTEGER , "+
                    ADDRESS_COLUMN + " TEXT, " ;

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public CostumerDao(Context context) {
        db = MyDBHelper2.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

//    public List<Customer> getAll(){
////
//    }

    // 新增參數指定的物件
    public Customer insert(Customer customer) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NAME_COLUMN, customer.getName());
        cv.put(PHONE_COLUMN, customer.getPhone());
        cv.put(ADDRESS_COLUMN, customer.getAddress());


        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        customer.setId(id);
        // 回傳結果
        return customer;
    }

    // 修改參數指定的物件
    public boolean update(Customer customer) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(NAME_COLUMN, customer.getName());
        cv.put(PHONE_COLUMN, customer.getPhone());
        cv.put(ADDRESS_COLUMN, customer.getAddress());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + customer.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    // 讀取所有記事資料
    public List<Customer> getAll() {
        List<Customer> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // 取得指定編號的資料物件
    public Customer get(long id) {
        // 準備回傳結果用的物件
        Customer item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public Customer getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Customer result = new Customer();

        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setPhone(cursor.getInt(2));
        result.setAddress(cursor.getString(3));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }

    // 建立範例資料
    public void sample() {
//        Customer item = new Customer(0, new Date().getTime(), Colors.RED, "關於Android Tutorial的事情.", "Hello content", "", 0, 0, 0);
//        Customer item2 = new Customer(0, new Date().getTime(), Colors.BLUE, "一隻非常可愛的小狗狗!", "她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。", "", 25.04719, 121.516981, 0);
//        Customer item3 = new Customer(0, new Date().getTime(), Colors.GREEN, "一首非常好聽的音樂！", "Hello content", "", 0, 0, 0);
//        Customer item4 = new Customer(0, new Date().getTime(), Colors.ORANGE, "儲存在資料庫的資料", "Hello content", "", 0, 0, 0);

//        insert(item);
//        insert(item2);
//        insert(item3);
//        insert(item4);
    }
}
