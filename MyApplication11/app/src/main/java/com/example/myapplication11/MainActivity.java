package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                NotificationChannel newnotchan= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    newnotchan= new NotificationChannel("mychannel1","mychannel",
                            NotificationManager.IMPORTANCE_HIGH);
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA) .build();
                    newnotchan.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+
                            "://"+getPackageName()+"/"+R.raw.al_heylisten), audioAttributes);
                }
                NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(newnotchan);
                }
                Notification notification = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification = new Notification.Builder(context,"mychannel1")
                            .setContentTitle("Hi from ME") .setContentText("Hey, listen!")
                            .setTicker("new notification!") .setChannelId("mychannel1")
                            .setSmallIcon(android.R.drawable.ic_dialog_alert) .setOngoing(true)
                            .build();
                }
                notificationManager.notify(0, notification);
            }
        });
    }
}