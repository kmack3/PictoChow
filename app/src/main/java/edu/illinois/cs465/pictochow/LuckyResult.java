package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

public class LuckyResult extends Activity {

    public TextView title;
    public TextView chosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_result);
        Bundle extras = getIntent().getExtras();
        String[] m = extras.getStringArray("restaurant_names");
        Random r = new Random();
        final String chosen_restaurant = m[r.nextInt(m.length)];

        title = (TextView) findViewById(R.id.getting_random);
        title.setText("Choosing a restaurant...");
        chosen = (TextView) findViewById(R.id.chosen);

        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {
                title.setText("You are going to:");
                chosen.setText(chosen_restaurant);
            }}, 2000);
    }
}