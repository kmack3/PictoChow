package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class LuckyResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_result);
        Bundle extras = getIntent().getExtras();
        String[] m = extras.getStringArray("restaurant_names");
        Random r = new Random();
        String chosen_restaurant = m[r.nextInt(m.length)];
        TextView chosen = (TextView)findViewById(R.id.chosen);
        chosen.setText(chosen_restaurant);
    }
}