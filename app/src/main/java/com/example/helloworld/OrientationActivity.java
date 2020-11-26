package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class OrientationActivity extends AppCompatActivity {
    ConstraintLayout orientationLayout;
    TextView center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        center = findViewById(R.id.txtOrientation);
        orientationLayout = findViewById(R.id.orientationLayout);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            center.setText("Sedang dalam mode Portrait");
            orientationLayout.setBackgroundColor(Color.YELLOW);
        } else {
            center.setText("Sedang dalam mode Landscape");
            orientationLayout.setBackgroundColor(Color.LTGRAY);
        }
    }


}