package com.example.celikmalzemefiyat;

/**
 * Created by orcun on 29.10.2013.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // daha önceki database isimleri: skordatabase.db, ?
    public static final String DB_NAME="cekondatabase.db";
    public static final String TABLE_NAME="malzemeler";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, malzemeisim TEXT, malzemekod TEXT, fiyat REAL, tarih TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }




}
