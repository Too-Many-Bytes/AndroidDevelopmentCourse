package com.example.examtask1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    GestureDetector mDetector;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.OutputGesture);
        mDetector = new GestureDetector (this,this);
        mDetector.setOnDoubleTapListener(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        output.setText("onSingleTapConfirmed: " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        output.setText("onDoubleTap: " + e.toString());
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        output.setText("onDoubleTapEvent: " + e.toString());
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        output.setText("onDown: " + e.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        output.setText("onShowPress: " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        output.setText("onSingleTapUp: " + e.toString());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        output.setText("onScroll: " + e1.toString() + e2.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        output.setText("onLongPress: " + e.toString());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        output.setText("onFling: " + e1.toString() + e2.toString());
        return false;
    }
}