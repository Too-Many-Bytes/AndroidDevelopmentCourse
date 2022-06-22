package com.example.myapplication15;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class MyCursorAdapter extends SimpleCursorAdapter {
    private int layout_;
    private Cursor cursor;
    String[] from;
    int[] to;
    ListView listView;
    EditText edit2;


    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        layout_=layout;
        cursor = c;
    }

    @Override
    public void bindView(View view, Context _context, Cursor cursor) {
        String data = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        TextView text = view.findViewById(R.id.listItem);
        text.setText(data);
        Button butdel = view.findViewById(R.id.buttonToDelete);
        Button butedit = view.findViewById(R.id.buttonToEdit);
        listView = MainActivity.listView;
        butdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = _context.openOrCreateDatabase("DBName", MODE_PRIVATE, null);
                db.execSQL("DELETE FROM my_table WHERE _id=" + id + "");
                Cursor cursor = db.rawQuery("SELECT * FROM my_table", null);
                from = new String[]{"Name"};
                to = new int[]{R.id.listItem};
                MyCursorAdapter scAdapter = new MyCursorAdapter(_context, R.layout.list_item, cursor, from, to);
                listView.setAdapter(scAdapter);
                db.close();
                Toast.makeText(_context, "row deleted from the db id=" + id, Toast.LENGTH_LONG).show();
            }
        });

        butedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(_context);
                dialog.setMessage("Enter new value:");
                dialog.setTitle("Changing the item");
                LayoutInflater inflater = new LayoutInflater(_context) {

                    @Override
                    public LayoutInflater cloneInContext(Context context) {

                        return null;
                    }
                };
                View dialogview = inflater.inflate(R.layout.dialog, null);
                dialog.setView(dialogview);
                edit2 = dialogview.findViewById(R.id.NewDataInput);
                edit2.setText(text.getText().toString());
                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        SQLiteDatabase db = _context.openOrCreateDatabase("DBName", MODE_PRIVATE, null);
                        db.execSQL("UPDATE my_table SET Name='" + edit2.getText().toString() + "' WHERE _id=" + id + "");
                        Cursor cursor = db.rawQuery("SELECT * FROM my_table", null);
                        from = new String[]{"Name"};
                        to = new int[]{R.id.listItem};
                        MyCursorAdapter scAdapter = new MyCursorAdapter(_context, R.layout.list_item, cursor, from, to);
                        listView.setAdapter(scAdapter);
                        db.close();
                        Toast.makeText(_context, "row edited from the db row id=" + id, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                dialog.setIcon(R.mipmap.ic_launcher_round);
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout_,parent,false);
        return view;
    }
}
