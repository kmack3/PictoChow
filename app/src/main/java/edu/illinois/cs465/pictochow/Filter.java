package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Brian on 11/16/2017.
 */

public class Filter extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        Button healthy_button = (Button) findViewById(R.id.healthy);
        healthy_button.setOnClickListener(this);

        Button college_button = (Button) findViewById(R.id.college);
        college_button.setOnClickListener(this);

        Button hurry_button = (Button) findViewById(R.id.hurry);
        hurry_button.setOnClickListener(this);

        Button fancy_button = (Button) findViewById(R.id.fancy);
        fancy_button.setOnClickListener(this);
    }




    public void onClick(View v) {
        int id = v.getId();
        Intent i = new Intent();
        int choice = -1;
        if (id == R.id.college) {
            choice = 0;
        } else if (id == R.id.hurry) {
            choice = 1;
        } else if (id == R.id.fancy) {
            choice = 2;
        } else {
            choice = 3;
        }
        i.putExtra("filter", choice);
        setResult(RESULT_OK, i);
        finish();
    }
}
