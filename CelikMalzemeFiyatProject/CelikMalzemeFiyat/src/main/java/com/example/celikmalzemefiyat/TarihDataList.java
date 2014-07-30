package com.example.celikmalzemefiyat;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by orcun on 10.08.2013.
 */
public class TarihDataList extends Activity {



    Cursor cursor;

    DatabaseHelper dbHelper;

    Button tarihButton;
    EditText dateTimePicker, dateTimePicker2;
    ListView listContent;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);


        dbHelper = new DatabaseHelper(this);
        listContent = (ListView)findViewById(R.id.listView);
        dateTimePicker = (EditText) findViewById(R.id.editText);
        dateTimePicker2 = (EditText) findViewById(R.id.editText2);
        tarihButton = (Button) findViewById(R.id.buttonTarih);


        dateTimePicker2.setText(MainActivity.strDate);
        dateTimePicker.setText(MainActivity.strDate);



        defineOnClickEvents();

    }



    private void defineOnClickEvents() {

        tarihButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SQLiteDatabase db = dbHelper.getReadableDatabase();
                cursor = db.rawQuery("select _id, malzemeisim, malzemekod, fiyat, tarih from " + DatabaseHelper.TABLE_NAME + " where tarih BETWEEN '" +
                        dateTimePicker.getText().toString() + "' AND '" + dateTimePicker2.getText().toString() + "' ORDER BY tarih ASC", null);

                Log.e("naber canim ya", dateTimePicker.getText().toString());

                //cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{"_id", "malzemeisim", "malzemekod", "fiyat", "tarih"}, "tarih LIKE '%"+ dateTimePicker.getText().toString()  +"%'",
                //null, null, null, "malzemekod");

                startManagingCursor(cursor);

                String[] from = new String[]{"_id", "malzemeisim","malzemekod", "fiyat", "tarih"};
                int[] to = new int[]{R.id._malzeme_id ,R.id.malzemeisimi, R.id.malzemekodu, R.id.fiyat, R.id.tarih};

                cursorAdapter = new SimpleCursorAdapter(TarihDataList.this, R.layout.row, cursor, from, to);

                listContent.setAdapter(cursorAdapter);





            }
        });

        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder adb=new AlertDialog.Builder(TarihDataList.this);
                adb.setTitle("Delete?");
                adb.setMessage("Silmek istedigine emin misin?" /*+  listContent.getAdapter().getItemId(i)*/ );
                final int positionToRemove = i;
                adb.setNegativeButton("Cancel", null);
                final AlertDialog.Builder ok = adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        long string;
                        string = listContent.getAdapter().getItemId(positionToRemove);
                        db.execSQL("DELETE FROM "+DatabaseHelper.TABLE_NAME+" WHERE _id = '" + string + "'");
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