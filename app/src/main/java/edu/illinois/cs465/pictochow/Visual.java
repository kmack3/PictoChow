package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.illinois.cs465.pictochow.R;

public class Visual extends Activity {
    ViewPager viewPager;
    private Map<String, Map<String, String>> rest_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondversion);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        init_data();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Intent intent = getIntent();
        TextView compareTitle = (TextView) findViewById(R.id.compareTitle);
        String ctitle = intent.getStringExtra("title");
        compareTitle.setText(ctitle);

    }

    public void init_data() {
        rest_data = new HashMap<String, Map<String, String>>();
        Map<String, String> sakanaya_data = new HashMap<String, String>();
        Map<String, String> sh_data = new HashMap<String, String>();
        Map<String, String> cracked_data = new HashMap<String, String>();
        Map<String, String> bangkok_data = new HashMap<String, String>();
        Map<String, String> salad_data = new HashMap<String, String>();

        sakanaya_data.put("wait_time", "60");
        sh_data.put("wait_time", "15");
        cracked_data.put("wait_time", "30");
        bangkok_data.put("wait_time", "15");
        salad_data.put("wait_time", "45");

        sakanaya_data.put("fat", "low");
        sh_data.put("fat", "low");
        cracked_data.put("fat", "high");
        bangkok_data.put("fat", "high");
        salad_data.put("fat", "low");

        sakanaya_data.put("protein", "high");
        sh_data.put("protein", "low");
        cracked_data.put("protein", "high");
        bangkok_data.put("protein", "high");
        salad_data.put("protein", "low");

        sakanaya_data.put("price", "5");
        sh_data.put("price", "2");
        cracked_data.put("price", "3");
        bangkok_data.put("price", "2");
        salad_data.put("price", "4");

        sakanaya_data.put("rating", "4");
        sh_data.put("rating", "5");
        cracked_data.put("rating", "4");
        bangkok_data.put("rating", "2");
        salad_data.put("rating", "3");

        sakanaya_data.put("type", "Sushi");
        sh_data.put("type", "Korean");
        cracked_data.put("type", "Breakfast");
        bangkok_data.put("type", "Thai");
        salad_data.put("type", "Soup and salad");

        sakanaya_data.put("distance", ".2");
        sh_data.put("distance", ".1");
        cracked_data.put("distance", ".3");
        bangkok_data.put("distance", ".1");
        salad_data.put("distance", ".3");

        sakanaya_data.put("sitdown", "yes");
        sh_data.put("sitdown", "yes");
        cracked_data.put("sitdown", "no");
        bangkok_data.put("sitdown", "no");
        salad_data.put("sitdown", "no");

        rest_data.put("Sakanaya", sakanaya_data);
        rest_data.put("Cracked", cracked_data);
        rest_data.put("Salad Meister", salad_data);
        rest_data.put("Bangkok Thai and Pho", bangkok_data);
        rest_data.put("Spoon House", sh_data);
    }
}
