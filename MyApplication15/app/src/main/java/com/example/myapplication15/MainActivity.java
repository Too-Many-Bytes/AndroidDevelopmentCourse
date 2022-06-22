package com.example.myapplication15;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends ListActivity {

    Integer i;
    String[] from;
    int[] to;
    static ListView listView;
    private EditText editadd;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        from = new String[]{"Name"};
        to = new int[] {R.id.listItem};
        Button btnadd = findViewById(R.id.buttonToAdd);
        editadd = findViewById(R.id.DataToAdd);
        SharedPreferences save = getSharedPreferences("SAVE",0);
        editadd.setText(save.getString("text",""));
        SQLiteDatabase db = openOrCreateDatabase("DBName",MODE_PRIVATE,null);
        db.execSQL(Constants.TABLE_STRUCTURE);
        Cursor cursor = db.rawQuery(Constants.SELECT, null);
        i=cursor.getCount()+1;
        if (cursor.getCount()>0) {
            MyCursorAdapter scAdapter = new MyCursorAdapter(MainActivity.this,R.layout.list_item,cursor,from,to);
            listView = getListView();
            listView.setAdapter(scAdapter);
        }

        db.close();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = openOrCreateDatabase("DBName",MODE_PRIVATE,null);
                Cursor cursor2 = db.rawQuery("SELECT * FROM my_table", null);
                i=cursor2.getCount()+1; //цикл для того, чтобы подбирать значения _id и не допускать повторения одинаковых значений (primary key как-никак)
                for (int k=1;k<=i;k++) {
                    Cursor cursor3 = db.rawQuery("SELECT * FROM my_table WHERE _id="+k+"", null);
                    if (cursor3.getCount()==0) {
                        i=k;
                        break;
                    }
                }

                db.execSQL("INSERT INTO my_table VALUES ('"+i+"','"+editadd.getText().toString()+"');"); //i++;
                Cursor cursor = db.rawQuery("SELECT * FROM my_table", null);
                MyCursorAdapter scAdapter = new MyCursorAdapter(MainActivity.this,R.layout.list_item,cursor,from,to);
                listView = getListView();
                listView.setAdapter(scAdapter);
                db.close();
                Toast.makeText(getListView().getContext(),"a row added to the table",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences save = getSharedPreferences("SAVE",0);
        SharedPreferences.Editor editor = save.edit(); //создаём редактор shared preferences
        editor.putString("text", editadd.getText().toString()); //сохраняем текст из edit1
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.programInfo)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString()+ " версия "+
                        getPackageManager().getPackageInfo(getPackageName(),0).versionName +
                        "\r\n\nПрограмма с примером использования базы данных \r\n\nАвтор - Федорович Игорь Сергеевич, гр. ББИ2007");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            dialog.setTitle("О программе");
            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.setIcon(R.mipmap.ic_launcher_round);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }

        if (item.getItemId()==R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}