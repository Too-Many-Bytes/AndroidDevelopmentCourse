package com.example.examtask3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class MainActivity extends AppCompatActivity {
    private static final int TEXT_SIZE = 40;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = buildIntent();
        startActivity(intent);
    }


    public Intent buildIntent() {
        int[] values = new int[]{78, 21, 1};
        String[] bars = new String[]{"Азот", "Кислород", "Прочие газы"};
        int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.RED};
        CategorySeries series = new CategorySeries("Pie Chart");
        DefaultRenderer dr = new DefaultRenderer();
        for (int v = 0; v < 3; v++) {
            series.add(bars[v], values[v]);
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[v]);
            dr.addSeriesRenderer(r);
        }


        dr.setZoomButtonsVisible(true);
        dr.setZoomEnabled(true);
        dr.setChartTitleTextSize(40);
        dr.setLegendTextSize(TEXT_SIZE);
        dr.setLabelsTextSize(TEXT_SIZE);
        dr.setLabelsColor(Color.BLACK);
        dr.setBackgroundColor(Color.rgb(65, 125, 50));
        dr.setApplyBackgroundColor(true);
        dr.setShowGridX(true);
        dr.setChartTitle("Состав атмосферы");

        return ChartFactory.getPieChartIntent(this, series, dr, "Atmosphere");
    }
}