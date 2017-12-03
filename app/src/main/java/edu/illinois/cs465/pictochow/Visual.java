package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Visual extends Activity {
    TextView title;
    private Map<String, Map<String, String>> rest_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visual);

        init_data();



        Intent intent = getIntent();
        String restaurants = intent.getStringExtra("title");

        String [] targets = restaurants.split(",");

        // Set up the title
        title = (TextView) findViewById(R.id.title);
        title.setText(targets[0]+" vs. "+targets[1]);



        // Initialize the wait time chart
        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(Integer.parseInt(rest_data.get(targets[0]).get("wait_time")), 0));
        entries.add(new BarEntry(Integer.parseInt(rest_data.get(targets[1]).get("wait_time")), 1));
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        set.setBarSpacePercent(50f);
        YAxis left = chart.getAxisLeft();
        left.setDrawGridLines(false); // no grid lines
        ArrayList<String> labels = new ArrayList<String>();
        labels.add(targets[0]);
        labels.add(targets[1]);
        BarData data = new BarData(labels, set);
        data.setValueTextSize(12);
        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.setData(data);
        chart.setDescription("Wait Time Comparison");
        chart.animateY(2000);



        // Initialize the distance chart
        HorizontalBarChart dist_chart = (HorizontalBarChart) findViewById(R.id.distance);
        List<BarEntry> dist_entries = new ArrayList<>();
        dist_entries.add(new BarEntry(Integer.parseInt(rest_data.get(targets[0]).get("distance")), 0));
        dist_entries.add(new BarEntry(Integer.parseInt(rest_data.get(targets[1]).get("distance")), 1));
        BarDataSet dist_set = new BarDataSet(dist_entries, "BarDataSet");
        dist_set.setColors(ColorTemplate.JOYFUL_COLORS);
        dist_set.setBarSpacePercent(50f);
        YAxis dist_left = chart.getAxisLeft();
        dist_left.setDrawGridLines(false); // no grid lines
        ArrayList<String> dist_labels = new ArrayList<String>();
        dist_labels.add(targets[0]);
        dist_labels.add(targets[1]);
        BarData dist_data = new BarData(dist_labels, dist_set);
        dist_data.setValueTextSize(12);
        dist_chart.getLegend().setEnabled(false);   // Hide the legend
        dist_chart.setData(dist_data);
        dist_chart.setDescription("Distance Comparison");
        dist_chart.animateY(2000);


        // Initialize the popularity chart
        LineChart lineChart = (LineChart) findViewById(R.id.popularity);
        ArrayList<Entry> dataset1 = new ArrayList<Entry>();
        String[] trend_one = rest_data.get(targets[0]).get("trend").split(",");
        for(int i=0;i<trend_one.length;i++){
            dataset1.add(new Entry(Integer.parseInt(trend_one[i]), i));
        }

        ArrayList<Entry> dataset2 = new ArrayList<Entry>();
        String[] trend_two = rest_data.get(targets[1]).get("trend").split(",");
        for(int i=0;i<trend_two.length;i++){
            dataset2.add(new Entry(Integer.parseInt(trend_two[i]), i));
        }

        String[] xAxis = new String[] {"8am", "10am", "12am", "2pm", "4pm", "6pm", "8pm", "10pm"};

        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(dataset1, targets[0]);
        lDataSet1.setColor(Color.parseColor("#f99613"));
        lDataSet1.setCircleColor(Color.parseColor("#f99613"));
        lDataSet1.setDrawValues(false);
        lDataSet1.setDrawCubic(true);
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(dataset2, targets[1]);
        lDataSet2.setDrawValues(false);
        lDataSet2.setDrawCubic(true);
        lDataSet2.setColor(Color.parseColor("#f95499"));
        lDataSet2.setCircleColor(Color.parseColor("#f95499"));
        lines.add(lDataSet2);

        lineChart.setData(new LineData(xAxis, lines));
        lineChart.setDescription("Popularity trends");
        lineChart.animateY(2000);



        // Initialize the title for pie chart one
        TextView rating_one_title = (TextView) findViewById(R.id.rating_title_one);
        rating_one_title.setText(targets[0]+" Yelp rating summary");

        // Initialize the pie chart for target one
        PieChart pieChart_one = (PieChart) findViewById(R.id.rating_one);

        ArrayList<Entry> rating_one = new ArrayList<>();
        String [] rating_data_one = rest_data.get(targets[0]).get("rating").split(",");
        for(int i=0;i<rating_data_one.length;i++){
            rating_one.add(new Entry(Integer.parseInt(rating_data_one[i]), i));
        }
//        rating_one.add(new Entry(1, 0));
//        rating_one.add(new Entry(2, 1));
//        rating_one.add(new Entry(10, 2));
//        rating_one.add(new Entry(11, 3));
//        rating_one.add(new Entry(18, 4));

        PieDataSet rating_one_dataset = new PieDataSet(rating_one, "");

        ArrayList<String> rating_labels = new ArrayList<String>();
        rating_labels.add("★");
        rating_labels.add("★★");
        rating_labels.add("★★★");
        rating_labels.add("★★★★");
        rating_labels.add("★★★★★");

        PieData rating_one_data = new PieData(rating_labels, rating_one_dataset);
        rating_one_data.setValueTextSize(14);
        rating_one_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart_one.getLegend().setEnabled(false);
        pieChart_one.setDescription("Yelp rating");
        pieChart_one.setData(rating_one_data);
        pieChart_one.animateY(2000);


        // Initialize the title for pie chart one
        TextView rating_two_title = (TextView) findViewById(R.id.rating_title_two);
        rating_two_title.setText(targets[1]+" Yelp rating summary");


        // Initialize the pie chart for target two
        PieChart pieChart_two = (PieChart) findViewById(R.id.rating_two);

        ArrayList<Entry> rating_two = new ArrayList<>();
        String [] rating_data_two = rest_data.get(targets[1]).get("rating").split(",");
        for(int i=0;i<rating_data_two.length;i++){
            rating_two.add(new Entry(Integer.parseInt(rating_data_two[i]), i));
        }
//        rating_two.add(new Entry(1, 0));
//        rating_two.add(new Entry(5, 1));
//        rating_two.add(new Entry(1, 2));
//        rating_two.add(new Entry(21, 3));
//        rating_two.add(new Entry(1, 4));

        PieDataSet rating_two_dataset = new PieDataSet(rating_two, "");

        PieData rating_two_data = new PieData(rating_labels, rating_two_dataset);
        rating_two_data.setValueTextSize(14);
        rating_two_dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart_two.getLegend().setEnabled(false);
        pieChart_two.setDescription("Yelp rating");
        pieChart_two.setData(rating_two_data);
        pieChart_two.animateY(2000);
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

        sakanaya_data.put("rating", "5,5,20,30,20");
        sh_data.put("rating", "5,5,5,5,40");
        cracked_data.put("rating", "1,4,20,30,10");
        bangkok_data.put("rating", "10,30,20,10,5");
        salad_data.put("rating", "10,20,30,20,10");

        sakanaya_data.put("type", "Sushi");
        sh_data.put("type", "Korean");
        cracked_data.put("type", "Breakfast");
        bangkok_data.put("type", "Thai");
        salad_data.put("type", "Soup and salad");

        sakanaya_data.put("distance", "200");
        sh_data.put("distance", "100");
        cracked_data.put("distance", "300");
        bangkok_data.put("distance", "100");
        salad_data.put("distance", "300");

        sakanaya_data.put("sitdown", "yes");
        sh_data.put("sitdown", "yes");
        cracked_data.put("sitdown", "no");
        bangkok_data.put("sitdown", "no");
        salad_data.put("sitdown", "no");


        sakanaya_data.put("trend", "0,3,7,0,3,7,10,7");
        sh_data.put("trend", "0,2,5,5,3,4,7,0");
        cracked_data.put("trend", "7,9,7,3,0,0,0,0");
        bangkok_data.put("trend", "0,4,7,5,6,8,9,3");
        salad_data.put("trend", "0,6,9,4,7,8,6,0");



        rest_data.put("Sakanaya", sakanaya_data);
        rest_data.put("Cracked", cracked_data);
        rest_data.put("Salad Meister", salad_data);
        rest_data.put("Bangkok Thai and Pho", bangkok_data);
        rest_data.put("Spoon House", sh_data);
    }
}
