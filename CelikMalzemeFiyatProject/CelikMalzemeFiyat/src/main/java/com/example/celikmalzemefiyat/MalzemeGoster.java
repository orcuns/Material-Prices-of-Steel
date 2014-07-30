package com.example.celikmalzemefiyat;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by orcun on 24.07.2013.
 */


public class MalzemeGoster extends Activity {




    TextView txt1;
    Spinner spinner;

    private String array_spinner[];
    ArrayAdapter adapter;

    private DatabaseHelper databaseHelper;
    private DatabaseHelper2 databaseHelper2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malzeme_goster);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper2 = new DatabaseHelper2(this);

        txt1 = (TextView) findViewById(R.id.databaseView);



        //tabHostIleIlgiliHersey();

       // spinnerTanim();
        loadSpinnerFromDatabase();



    }


    private void tabHostIleIlgiliHersey(){
/*

        tabHost.setup();
        TabHost.TabSpec spec1=tabHost.newTabSpec("TAB 1");
       // spec1.setContent(R.id.tab1);
        spec1.setIndicator("Malzeme ismi");

        TabHost.TabSpec spec2=tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("Fiyat");
        //spec2.setContent(R.id.tab2);

        TabHost.TabSpec spec3=tabHost.newTabSpec("TAB 3");
       // spec3.setContent(R.id.tab3);
        spec3.setIndicator("Tarih");
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {

            //tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.BLACK); //Changing background color of tab

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); /*for Selected Tab changing text color
            tv.setTextColor(Color.WHITE);
        }*/
    }

    private void spinnerTanim(){
        array_spinner=new String[15];
        array_spinner[0]="Hazir Beton";
        array_spinner[1]="Demir";
        array_spinner[2]="Kum";
        array_spinner[3]="Cimento";
        array_spinner[4]="Kereste";
        array_spinner[5]="Kalip";
        array_spinner[6]="Seramik";
        array_spinner[7]="Vitrifiye";
        array_spinner[8]="S.Tesisat";
        array_spinner[9]="E.Tesisat";
        array_spinner[10]="Dograma";
        array_spinner[11]="Alet Edevat";
        array_spinner[12]="Aksesuarlar";
        array_spinner[13]="Kaplamalar";
        array_spinner[14]="Izolasyon";


        spinner = (Spinner) findViewById(R.id.comboBox);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
        spinner.setAdapter(adapter);
        // Log.e("asd",spinner.getSelectedItem().toString());

        clickEventForNameSearch();

    }


    private void loadSpinnerFromDatabase() {
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set =  databaseHelper2.getAllData();

        List<String> list = new ArrayList<String>(set);

        spinner = (Spinner) findViewById(R.id.comboBox);

        adapter = new ArrayAdapter<String>(MalzemeGoster.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setNotifyOnChange(true);
        spinner.setAdapter(adapter);
        spinner.setWillNotDraw(false);

        clickEventForNameSearch();

    }

    private void clickEventForNameSearch(){



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                databaseMalzemeAra();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void databaseMalzemeAra() {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{"_id", "malzemeisim", "malzemekod", "fiyat", "tarih"}, "malzemeisim LIKE '%"+spinner.getSelectedItem().toString()+"%'",
                null, null, null, "malzemekod");
        startManagingCursor(cursor);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString greenSpannableFiyat, redSpannableTarih;
        while(cursor.moveToNext())
        {

            //String malzemeisim = cursor.getString((cursor.getColumnIndex("malzemeisim")));
            String malzemekod = cursor.getString((cursor.getColumnIndex("malzemekod")));
            double fiyat = cursor.getDouble((cursor.getColumnIndex("fiyat")));
            String tarih = cursor.getString((cursor.getColumnIndex("tarih")));

            greenSpannableFiyat = new SpannableString(Double.toString(fiyat)+ " TL");
            greenSpannableFiyat.setSpan(new ForegroundColorSpan(Color.GREEN), 0, Double.toString(fiyat).length()+3, 0);

            redSpannableTarih = new SpannableString(tarih);
            redSpannableTarih.setSpan(new ForegroundColorSpan(Color.RED), 0, tarih.length(), 0);

            //builder.append(malzemeisim).append("-");
            builder.append(malzemekod).append(":    ");
            builder.append(greenSpannableFiyat).append("      |     ");
            builder.append(redSpannableTarih).append("\n");
        }
        txt1.setTextSize(18f);
        txt1.setTextColor(Color.WHITE);
        txt1.setText(builder, TextView.BufferType.SPANNABLE);

    }









}
