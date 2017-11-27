package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.illinois.cs465.pictochow.R;

public class Visual extends Activity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondversion);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Intent intent = getIntent();
        TextView compareTitle = (TextView) findViewById(R.id.compareTitle);
        String ctitle = intent.getStringExtra("title");
        compareTitle.setText(ctitle);

    }
}
