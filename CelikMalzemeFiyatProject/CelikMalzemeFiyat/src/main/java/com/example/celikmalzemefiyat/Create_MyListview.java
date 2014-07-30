package com.example.celikmalzemefiyat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * Created by orcun on 21.11.2013.
 */
public class Create_MyListview extends Activity {

    String name;
    MainActivity MA;
    private ListView listView;
    private Button btnAdd, btnLoad;
    private EditText editText;
    DatabaseHelper2 databaseHelper;
    Toast toast1, toast2;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;

    List<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listview_data);


        editText = (EditText) findViewById(R.id.mylistview_editText);
        btnAdd = (Button) findViewById(R.id.mylistview_button_veriEkle);
        btnLoad = (Button) findViewById(R.id.mylistview_button_veriGoster);
        listView = (ListView) findViewById(R.id.mylistview_gosterilenVeri);

        databaseHelper = new DatabaseHelper2(this);

        defineOnClickEvents();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name = editText.getText().toString().trim();

             /*   MA = new MainActivity();
                MA.set_ArraySpinner(name);*/

                add_newItem_Name();


            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("Kapandı", "KAAAAAAAAAAAPANDIIII !!");
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);

    }

    public void add_newItem_Name(){

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            if (editText.getText().toString().equals("")){
                toast1 = Toast.makeText(this.getApplicationContext(), "Veri Girin",Toast.LENGTH_SHORT);
                toast1.show();
            }

            else if (db != null) {
                values.put("malzemeisim", editText.getText().toString());
                toast2 = Toast.makeText(this.getApplicationContext(), "KAYIT EKLENDI",Toast.LENGTH_SHORT);
                toast2.show();
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

    private void defineOnClickEvents() {

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                cursor = db.rawQuery("select _id, malzemeisim  from " + DatabaseHelper2.TABLE_NAME + " ORDER BY malzemeisim ASC", null);

                startManagingCursor(cursor);

                String[] from = new String[]{"_id", "malzemeisim" };
                int[] to = new int[]{R.id._malzeme_id ,R.id.malzemeisimi };

                cursorAdapter = new SimpleCursorAdapter(Create_MyListview.this, R.layout.row2, cursor, from, to);

                listView.setAdapter(cursorAdapter);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder adb=new AlertDialog.Builder(Create_MyListview.this);
                adb.setTitle("Delete?");
                adb.setMessage("Silmek istedigine emin misin?" /*+  listContent.getAdapter().getItemId(i)*/ );
                final int positionToRemove = i;
                adb.setNegativeButton("Cancel", null);
                final AlertDialog.Builder ok = adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
                        long string;
                        string = listView.getAdapter().getItemId(positionToRemove);
                        db.execSQL("DELETE FROM "+DatabaseHelper2.TABLE_NAME+" WHERE _id = '" + string + "'");
                        //db.delete(DatabaseHelper.TABLE_NAME, string+"="+ "_id" ,null );

                        // listContent.remove(to[positionToRemove]);
                        cursorAdapter.notifyDataSetChanged();
                        cursor.requery();
                    }
                });
                adb.show();
            }
        });
    }





}
