package com.example.examtask6;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyCursorAdapter extends SimpleCursorAdapter {
    private int layout_;
    private Cursor cursor;
    String[] from;
    int[] to;
    ListView listView;
    EditText edit2;
    public static ArrayList<String> nums = new ArrayList<String>();


    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        layout_ = layout;
        cursor = c;
        nums.add("88005553535");
        nums.add("89564164721");
        nums.add("89656147214");
        nums.add("89561467142");
        nums.add("89654617241");
    }

    @Override
    public void bindView(View view, Context _context, Cursor cursor) {
        String data = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        TextView text = view.findViewById(R.id.listItem);
        text.setText(data);
        ImageButton butcall = view.findViewById(R.id.buttonCall);
        ImageButton butmessage = view.findViewById(R.id.buttonSendMessage);
        ImageButton butdel = view.findViewById(R.id.buttonToDelete);
        ImageButton butedit = view.findViewById(R.id.buttonToEdit);
        listView = MainActivity.listView;


        butcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = _context.openOrCreateDatabase("DBName", MODE_PRIVATE, null);
//                Cursor cursor = db.rawQuery("SELECT Name FROM my_table WHERE _id=" + id + "", null);
//                String numberToCall = "tel:" + cursor.getString(cursor.getColumnIndexOrThrow("Name"));
//                db.close();
                String numberToCall = "tel:"+ nums.get(id % 5);
                _context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(numberToCall)));
            }
        });

        butmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.putExtra("address"  , nums.get(id % 5));
                _context.startActivity(smsIntent);
            }
        });
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
                Toast.makeText(_context, "Number has been deleted successfully", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(_context, "Number has been changed successfully", Toast.LENGTH_LONG).show();
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
        View view = inflater.inflate(layout_, parent, false);
        return view;
    }
}
