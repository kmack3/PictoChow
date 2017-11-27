package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LuckyResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_result);

        TextView chosen = (TextView)findViewById(R.id.chosen);
        Intent intent = getIntent();

        chosen.setText(intent.getStringExtra("chosen"));
    }
}