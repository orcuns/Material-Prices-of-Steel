package com.example.celikmalzemefiyat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by orcun on 21.11.2013.
 */
public class DatabaseHelper2 extends SQLiteOpenHelper {

    // daha önceki database isimleri: skordatabase.db, ?
    public static final String DB_NAME="cekondatabase2.db";
    public static final String TABLE_NAME="malzemeler2";

    public DatabaseHelper2(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, malzemeisim TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

    public void insertData(String label){

        SQLiteDatabase db = this.getWritableDatabase();


        try {
            ContentValues values = new ContentValues();

            values.put("malzemeisim",label);
            if (db != null) {
                db.insert(DatabaseHelper2.TABLE_NAME, null, values);
            }
            Log.e("DBTEST", "KAYIT EKLENDI");

        }
        catch (Exception e) {

            Log.e("DBTEST", e.toString());

        }
        finally{
            db.close();

        }

    }
    public Set<String> getAllData() {


        Set<String> set = new HashSet<String>();

        String selectQuery = "select * from " + DatabaseHelper2.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                set.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return set;
    }



}