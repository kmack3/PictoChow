package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Brian on 11/16/2017.
 */

public class Filter extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
    }

    public void onClick(View v) {
        int id = v.getId();
        Intent i = new Intent();
        int choice = -1;
        if (id == R.id.college){
            choice = 0;
        }
        else if (id == R.id.hurry) {
            choice = 1;
        }
        else if (id == R.id.fancy) {
            choice = 2;
        }
        else {
            choice = 3;
        }
        getIntent().putExtra("filter", choice);
        setResult(RESULT_OK, i);
        // @Brian how do we make it return back to map as soon as you press this
    }

}
