package edu.illinois.cs465.pictochow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.String;

public class LuckyCoin extends Activity implements View.OnClickListener {

    private Button choose_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_coin);


        choose_button = (Button) findViewById(R.id.choose_for_me);
        choose_button.setOnClickListener(this);

        LinearLayout ll = (LinearLayout) findViewById(R.id.frame);


        String m []= new String[]{"Mia Za's", "Slice Factory", "Subways", "Spoon House","Panda Express","Jimmy John"};

        for (int i=0;i<5;i++) {
            TextView textView = new TextView(this);
            textView.setText(m[i]);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i!=4)
                llp.setMargins(0, 60, 0, 60);
            else
                llp.setMargins(0, 120, 0, 60);
            textView.setLayoutParams(llp);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(30);
            ll.addView(textView, 1);
        }

    }

    public void onClick(View v) {
        if (v.getId() == R.id.choose_for_me) {
            Intent intent = new Intent(this,LuckyResult.class);
            intent.putExtra("chosen","Miaza's");
            startActivity(intent);
        }
    }
}