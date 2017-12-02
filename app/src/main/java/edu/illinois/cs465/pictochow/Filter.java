package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Brian on 11/16/2017.
 */

public class Filter extends Activity{

    private HashMap<String, Boolean> selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        Intent i = getIntent();
        selected = new HashMap<String, Boolean>();
        selected.put("hurry", i.getBooleanExtra("hurry", false));
        selected.put("healthy", i.getBooleanExtra("healthy", false));
        selected.put("fancy", i.getBooleanExtra("fancy", false));
        selected.put("college", i.getBooleanExtra("college", false));

        CheckBox healthy_button = (CheckBox) findViewById(R.id.healthy);
        if (i.getBooleanExtra("healthy", false)) {
            healthy_button.setChecked(true);
        }
        CheckBox college_button = (CheckBox) findViewById(R.id.college);
        if (i.getBooleanExtra("college", false)) {
            college_button.setChecked(true);
        }
        CheckBox hurry_button = (CheckBox) findViewById(R.id.hurry);
        if (i.getBooleanExtra("hurry", false)) {
            hurry_button.setChecked(true);
        }
        CheckBox fancy_button = (CheckBox) findViewById(R.id.fancy);
        if (i.getBooleanExtra("fancy", false)) {
            fancy_button.setChecked(true);
        }
    }




    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.hurry:
                if (checked) {
                    selected.remove("hurry");
                    selected.put("hurry", true);
                }
                else {
                    selected.remove("hurry");
                    selected.put("hurry", false);
                }
                // Put some meat on the sandwich
                break;
            case R.id.fancy:
                if (checked) {
                    selected.remove("fancy");
                    selected.put("fancy", true);
                }
                else {
                    selected.remove("fancy");
                    selected.put("fancy", false);
                }
                break;
            case R.id.college:
                if (checked) {
                    selected.remove("college");
                    selected.put("college", true);
                }
                else {
                    selected.remove("college");
                    selected.put("college", false);
                }
                break;
            case R.id.healthy:
                if (checked) {
                    selected.remove("healthy");
                    selected.put("healthy", true);
                }
                else {
                    selected.remove("healthy");
                    selected.put("healthy", false);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("hurry", selected.get("hurry"));
        i.putExtra("healthy", selected.get("healthy"));
        i.putExtra("fancy", selected.get("fancy"));
        i.putExtra("college", selected.get("college"));
        setResult(RESULT_OK, i);
        finish();
    }
}
