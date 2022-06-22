package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    public static String text2remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.editTextTextPersonName);
        Button button = findViewById(R.id.buttonFirst);
        editText.setText(text2remember);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2remember = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}