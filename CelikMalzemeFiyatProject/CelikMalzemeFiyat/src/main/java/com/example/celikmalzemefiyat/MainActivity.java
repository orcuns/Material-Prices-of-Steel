package com.example.celikmalzemefiyat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity {


    Create_MyListview create_Your_Listview;
    DatabaseHelper databaseHelper;
    DatabaseHelper2 databaseHelper2;
    Button kaydetButon, malzemeGoster, tariheGoreAra, malzemeEkle;
    EditText dateTimePicker, fiyat, malzemeKod;
    Spinner spinner;
     Context context;
    public static String strDate;
    CharSequence text1, text2;
    int duration;
    Toast toast1, toast2;
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        spinner = (Spinner) findViewById(R.id.comboBox);
        malzemeEkle = (Button) findViewById(R.id.malzemeniEkle);
        malzemeGoster = (Button) findViewById(R.id.malzemeGoster);
        tariheGoreAra = (Button) findViewById(R.id.tariheGore);
        kaydetButon = (Button) findViewById(R.id.kaydetButton);
        dateTimePicker = (EditText) findViewById(R.id.dateTime);
        fiyat = (EditText) findViewById(R.id.fiyat);
        malzemeKod = (EditText) findViewById(R.id.malzemeKod);

        create_Your_Listview = new Create_MyListview();
        databaseHelper2 = new DatabaseHelper2(MainActivity.this);
        databaseHelper = new DatabaseHelper(this);
        context = getApplicationContext();
        text1 = "Kayit Veritabanina Eklendi!";
        text2 = "Kayit Eklenemedi!!";
        duration = Toast.LENGTH_SHORT;
        //Date date = new Date();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        strDate = sdf.format(cal.getTime());

        dateTimePicker.setText(strDate);

        SQLiteDatabase db = databaseHelper2.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+ DatabaseHelper2.TABLE_NAME, null);
        if (cur != null){
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                // Empty
                databaseHelper2.insertData("Hazir Beton");
                databaseHelper2.insertData("Demir");
                databaseHelper2.insertData("Kum");
                databaseHelper2.insertData("Cimento");
                databaseHelper2.insertData("Kereste");
                databaseHelper2.insertData("Kalip");
                databaseHelper2.insertData("Seramik");
                databaseHelper2.insertData("Vitrifiye");
                databaseHelper2.insertData("S.Tesisat");
                databaseHelper2.insertData("E.Tesisat");
                databaseHelper2.insertData("Dograma");
                databaseHelper2.insertData("Alet Edevat");
                databaseHelper2.insertData("Aksesuarlar");
                databaseHelper2.insertData("Kaplamalar");
                databaseHelper2.insertData("Izolasyon");
            }

        }

        loadSpinnerFromDatabase();

        defineOnClickEvents();
    }


    private void loadSpinnerFromDatabase() {
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set =  databaseHelper2.getAllData();

        List<String> list = new ArrayList<String>(set);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setNotifyOnChange(true);
        spinner.setAdapter(adapter);
        spinner.setWillNotDraw(false);

    }

    private void defineOnClickEvents() {

        try {

        kaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
                String databaseDate = iso8601Format.format(strDate);*/

                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();

                    values.put("malzemeisim", spinner.getSelectedItem().toString());
                    values.put("malzemekod", malzemeKod.getText().toString());
                    values.put("fiyat", Double.valueOf(fiyat.getText().toString()));
                    values.put("tarih", dateTimePicker.getText().toString() );

                    if (db != null) {
                        db.insert(DatabaseHelper.TABLE_NAME, null, values);
                    }
                    Log.e("DBTEST", "KAYIT EKLENDI");
                    toast1 = Toast.makeText(context, text1,duration);
                    toast1.show();


                } catch (Exception e) {
                    toast2 = Toast.makeText(context, text2,duration);
                    toast2.show();
                    Log.e("DBTEST", e.toString());

                }
                finally{
                    db.close();

                }
            }
        });

        malzemeGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openNewScreen = new Intent(MainActivity.this, MalzemeGoster.class);
                MainActivity.this.startActivity(openNewScreen);
            }
        });

        tariheGoreAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openNewScreen = new Intent(MainActivity.this, TarihDataList.class);
                MainActivity.this.startActivity(openNewScreen);
            }
        });

        malzemeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openNewScreen = new Intent(MainActivity.this, Create_MyListview.class);
                MainActivity.this.startActivity(openNewScreen);
               finish();

            }
        });
       }catch (Exception e){
        Log.e("HALLO", "This is CATCH ERROR");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}