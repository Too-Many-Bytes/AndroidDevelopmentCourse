package com.example.examtask5;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    int currentImage = 0;
    ArrayList<String> images;
    ImageView imageView;
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPrevious();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        currentImage = 0;
        Log.d("myLogs", "onResume cI=" + currentImage);
        nameView = ((TextView) findViewById(R.id.textView));
        images = new ArrayList<String>();
        imageView = ((ImageView) findViewById(R.id.imageView));
        try {
            File imagesDirectory = new File("/sdcard/TrainingMedia");
            images = searchImage(imagesDirectory);
            updatePhoto(Uri.parse(images.get(currentImage)));
        } catch (Exception e) {
            nameView.setText("Ошибка: Папка '/sdcard/TrainingMedia/' не найдена");
            Log.d("myLogs", "Ошибка");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        images.clear();
        Log.d("myLogs", "onPause cI=" + currentImage);
    }

    private ArrayList<String> searchImage(File dir) {
        ArrayList<String> imagesFinded = new ArrayList<String>();
        Log.v("Files",dir.exists()+"");
        Log.v("Files",dir.isDirectory()+"");
        Log.v("Files",dir.listFiles()+"");

        for (File f : dir.listFiles()) {
            if (!f.isDirectory()) {
                String fileExt = getFileExt(f.getAbsolutePath());
                if (fileExt.equals("png") || fileExt.equals("jpg") || fileExt.equals("jpeg")) {
                    Log.d("myLogs", "Файл найден " + f.getAbsolutePath());
                    imagesFinded.add(f.getAbsolutePath());
                }
            }
        }
        return imagesFinded;
    }

    public static String getFileExt(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void updatePhoto(Uri uri) {
        try {
            nameView.setText((currentImage + 1) + "/" + images.size());
            imageView.setImageURI(uri);
        } catch (Exception e) {
            nameView.setText("Ошибка загрузки файла");
        }
    }

    public void onNext() {
        if (currentImage + 1 < images.size() && images.size() > 0) {
            currentImage++;
            updatePhoto(Uri.parse(images.get(currentImage)));
        }
    }

    public void onPrevious() {
        if (currentImage > 0 && images.size() > 0) {
            currentImage--;
            updatePhoto(Uri.parse(images.get(currentImage)));
        }
    }
}