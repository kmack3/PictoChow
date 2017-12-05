package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapView extends Activity {
    private int     filterSelected = -1;
    private com.mapbox.mapboxsdk.maps.MapView mapView;
    private MapboxMap mapbox_map;
    private Icon icons [];
    private Marker markers [];
    private HashMap<String, Map<String, String>> rest_data = new HashMap<String, Map<String, String>>();

    // Icons

    Icon icon_cur_loc;


    Icon icon_100_green;
    Icon icon_100_purple;
    Icon icon_75_green;
    Icon icon_75_purple;
    Icon icon_50_green;
    Icon icon_50_purple;
    Icon icon_25_green;
    Icon icon_25_purple;
    Icon icon_blank;

    // Markers
    Marker markerOne;
    Marker markerTwo;
    Marker markerThree;
    Marker markerFour;
    Marker markerFive;
    // Buttons
    Button compareButton;
    CompoundButton visualizeButton;

    Boolean selectMode = false;
    static final int PICK_FILTER_REQUEST = 1;  // The request code
    ArrayList<String> selected = new ArrayList<String>();
    ArrayList<String> selected_filters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoianVzdGlubWVpZCIsImEiOiJjaXlxcGNkaXQwMDJlMndxa3E0cGEzNnBoIn0.LHVEjoFcv0ZD6Dkp7ObVzQ");
        setContentView(R.layout.activity_map_view);

        init_data();
        selected_filters.add("college");

        // get buttons
        // randomButton = (Button) findViewById(R.id.randomButton);
        // filterButton = (Button) findViewById(R.id.filterButton);
        visualizeButton = (CompoundButton) findViewById(R.id.visualizeButton);

        compareButton = (Button) findViewById(R.id.compareButton);
        compareButton.setVisibility(View.GONE);

        // map stuff
        mapView = (com.mapbox.mapboxsdk.maps.MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // map
                mapbox_map = mapboxMap;

                // get icons
                icon_cur_loc = IconFactory.getInstance(MapView.this).fromResource(R.drawable.currentlocation);


                Drawable dr = getResources().getDrawable(R.drawable.agreen);
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_agreen = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.apurple);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_apurple = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.bgreen);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_bgreen = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.bpurple);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_bpurple = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.cgreen);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_cgreen = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.cpurple);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_cpurple = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.dgreen);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_dgreen = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                dr = getResources().getDrawable(R.drawable.dpurple);
                bitmap = ((BitmapDrawable) dr).getBitmap();
                Bitmap scaled_dpurple = Bitmap.createScaledBitmap(bitmap, 150, 150, true);

                icon_100_green = IconFactory.getInstance(MapView.this).fromBitmap(scaled_agreen);
                icon_100_purple = IconFactory.getInstance(MapView.this).fromBitmap(scaled_apurple);
                icon_75_green = IconFactory.getInstance(MapView.this).fromBitmap(scaled_bgreen);
                icon_75_purple = IconFactory.getInstance(MapView.this).fromBitmap(scaled_bpurple);
                icon_50_green = IconFactory.getInstance(MapView.this).fromBitmap(scaled_cgreen);
                icon_50_purple = IconFactory.getInstance(MapView.this).fromBitmap(scaled_cpurple);
                icon_25_green = IconFactory.getInstance(MapView.this).fromBitmap(scaled_dgreen);
                icon_25_purple = IconFactory.getInstance(MapView.this).fromBitmap(scaled_dpurple);




                // current location marker
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.110281,-88.232022))
                        .icon(icon_cur_loc)
                );

                // restaurant markers
                markerOne = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.110113,-88.233218))
                        .title("Sakanaya")
                        .snippet("$$$\n★★★★\nSushi, Ramen")
                        .icon(icon_100_green)
                );

                markerTwo = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1100646,-88.2296092))
                        .title("Cracked")
                        .snippet("$$\n★★★\nSandwich")
                        .icon(icon_100_green)
                );

                markerThree = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1111668573957,-88.2305980101228))
                        .title("Salad Meister")
                        .snippet("$$\n★★★★\nSalad, Bread")
                        .icon(icon_100_green)
                );

                markerFour = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1105683,-88.2325823))
                        .title("Bangkok Thai and Pho")
                        .snippet("$$\n★★★★★\nPho, Rice Noodle")
                        .icon(icon_100_green)
                );

                markerFive = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1104414,-88.2317108))
                        .title("Spoon House")
                        .snippet("$$\n★★★★\nKorean, Kimchi")
                        .icon(icon_100_green)
                );

                updateMarkers();
                updateSnippets();




                // marker click listener
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                       if(selectMode){
                            // toggle all markers except the current location marker
                            if(marker.getIcon()!=icon_cur_loc && marker.getIcon()!=icon_blank) {
                                // toggle icon
                                IconToggle(marker);



                                // remove/add marker title from/to selected list
                                if(selected.contains(marker.getTitle())){
                                    selected.remove(marker.getTitle());
                                }
                                else {
                                    selected.add(marker.getTitle());
                                }



                                if (selected.size() == 2) {
                                    compareButton.setText(selected.get(0) + " vs. " + selected.get(1));
                                    compareButton.setVisibility(View.VISIBLE);
                                } else {
                                    compareButton.setVisibility(View.GONE);
                                }
                            }
                       }
                            // return false if you still want the bubble to appear when selecting the marke
                       return false;
                    }
                });
            }
        });


    }

    public void IconToggle(Marker m){
        Icon curIcon = m.getIcon();
        if(curIcon == icon_100_purple){ m.setIcon(icon_100_green); }
        else if(curIcon == icon_100_green){ m.setIcon(icon_100_purple); }
        else if(curIcon == icon_75_purple){ m.setIcon(icon_75_green); }
        else if(curIcon == icon_75_green){ m.setIcon(icon_75_purple); }
        else if(curIcon == icon_50_purple){ m.setIcon(icon_50_green); }
        else if(curIcon == icon_50_green){ m.setIcon(icon_50_purple); }
        else if(curIcon == icon_25_purple){ m.setIcon(icon_25_green); }
        else if(curIcon == icon_25_green){ m.setIcon(icon_25_purple); }

    }

    public void updateSnippets(){
        Marker m []= new Marker[]{markerOne, markerTwo, markerThree, markerFour, markerFive};

        for(Marker mark: m){
            String snippet = "";

            if(selected_filters.contains("hurry")){
                snippet += "Wait: " + rest_data.get(mark.getTitle()).get("wait_time") + " min\n";

                snippet += rest_data.get(mark.getTitle()).get("distance") + " mi\n";
            }

            if(selected_filters.contains("healthy")){
                snippet += rest_data.get(mark.getTitle()).get("fat") + " fat\n";
                snippet += rest_data.get(mark.getTitle()).get("protein") + " protein\n";

            }

            if(selected_filters.contains("college")){
                String price = rest_data.get(mark.getTitle()).get("price");
                for(int x = 0; x < Integer.parseInt(price); x++){
                    snippet += "$";
                }
                snippet += "\n";
            }

            if(selected_filters.contains("fancy")){
                if(rest_data.get(mark.getTitle()).get("sitdown").equals("yes")){
                    snippet += "Sit down\n";
                }

                String rating = rest_data.get(mark.getTitle()).get("rating");
                for(int x = 0; x < Integer.parseInt(rating); x++){
                    snippet += "★";
                }
                snippet += "\n";

            }
            // add type to snippet always
            snippet += rest_data.get(mark.getTitle()).get("type");


            mark.setSnippet(snippet);

        }

    }

    public void buttonHandler(View v){
        switch(v.getId())
        {
            case R.id.filterButton:
                Intent i = new Intent(this, Filter.class);
                for (String filter: selected_filters) {
                    i.putExtra(filter, true);
                }
                startActivityForResult(i, PICK_FILTER_REQUEST);
                break;

            case R.id.randomButton:
                Intent k = new Intent(this, LuckyResult.class);
                String lucky_data[] = {markerOne.getTitle(), markerTwo.getTitle(), markerThree.getTitle(), markerFour.getTitle(), markerFive.getTitle()};
                k.putExtra("restaurant_names",lucky_data);
                startActivity(k);
                break;

            case R.id.visualizeButton:
                selectMode = !selectMode;
                if(selectMode){
                    visualizeButton.setChecked(true);
                    Toast.makeText(this, "Select two restaurants to compare", Toast.LENGTH_SHORT).show();
                }
                else{
                    visualizeButton.setChecked(false);
                    compareButton.setVisibility(View.GONE);
                    DeselectAllMarkers();
                    selected.clear();
                }
                break;

            case R.id.compareButton:
                Intent j = new Intent(this, Visual.class);
                j.putExtra("title", selected.get(0) + "," + selected.get(1));
                startActivity(j);
                break;

        }
    }

    public void updateMarkers(){
        Marker m []= new Marker[]{markerOne, markerTwo, markerThree, markerFour, markerFive};
        for(Marker mark: m){
            int score = calc_score(mark.getTitle());
            Log.d("debugg", Integer.toString(score));
            if(score == 100){
                mark.setIcon(icon_100_purple);
            }
            else if(score == 75){
                mark.setIcon(icon_75_purple);
            }
            else if(score == 50){
                mark.setIcon(icon_50_purple);
            }
            else if(score == 25){
                mark.setIcon(icon_25_purple);
            }
            else {
                mark.setIcon(icon_blank);
            }

        }
        // update filter list
        TextView filter_list = findViewById(R.id.filter_list);
        String filters = "";
        if (selected_filters.contains("hurry")){
            filters = filters + "In a Hurry\n";
            selected_filters.add("hurry");
        }
        if (selected_filters.contains("healthy")){
            filters = filters + "Healthy\n";
            selected_filters.add("healthy");
        }
        if (selected_filters.contains("college")){
            filters = filters + "Broke Student\n";
            selected_filters.add("college");
        }
        if (selected_filters.contains("fancy")){
            filters = filters + "Nice Dinner\n";
            selected_filters.add("fancy");
        }
        filter_list.setText(filters);


    }

    public void DeselectAllMarkers(){
        Marker m []= new Marker[]{markerOne, markerTwo, markerThree, markerFour, markerFive};
        for(Marker mark: m){
            Icon curIcon = mark.getIcon();
            if(curIcon == icon_100_green){ mark.setIcon(icon_100_purple); }
            else if(curIcon == icon_75_green){ mark.setIcon(icon_75_purple); }
            else if(curIcon == icon_50_green){ mark.setIcon(icon_50_purple); }
            else if(curIcon == icon_25_green){ mark.setIcon(icon_25_purple); }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String toasty = "";
                selected_filters = new ArrayList<String>();
                Boolean hurry = data.getBooleanExtra("hurry", false);
                if (hurry){
                    toasty = toasty + "In a Hurry. ";
                    selected_filters.add("hurry");
                }
                Boolean healthy = data.getBooleanExtra("healthy", false);
                if (healthy){
                    toasty = toasty + "Healthy. ";
                    selected_filters.add("healthy");
                }
                Boolean college = data.getBooleanExtra("college", false);
                if (college){
                    toasty = toasty + "Broke Student. ";
                    selected_filters.add("college");
                }
                Boolean fancy = data.getBooleanExtra("fancy", false);
                if (fancy){
                    toasty = toasty + "Nice Dinner. ";
                    selected_filters.add("fancy");
                }
                if (!toasty.isEmpty()) {
                    Toast.makeText(this, toasty, Toast.LENGTH_SHORT).show();
                    updateMarkers();
                    updateSnippets();
                }
            }
        }
    }

    public void init_data() {
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

    public int calc_score(String restaurant) {
        // must assign a score of 0, 25, 50, 75, or 100 to each restaurant
        // where 100 means very well satisfies all selected filters
        double total_score = 0;
        int total_filters = 0;
        for (String filter: selected_filters) {
            if (filter.equals("hurry")) {
                // this filter picks restaurants that are close and have low wait time
                // 50 points allotted to being close
                // 50 points allotted to being low wait time
                double dist = Double.parseDouble((rest_data.get(restaurant)).get("distance"));
                int wait = Integer.parseInt((rest_data.get(restaurant)).get("wait_time"));

                // if it is more than 30 minute wait, does not contribute to the score
                // 1-wait_time/30 * 50 is the points you get
                double wait_score = 0;
                if (wait <= 30) {
                    wait_score = (1 - (double)wait/30) * 50;
                }
                // if it is more than .5 miles away, does not contribute to the score
                // 1-(dist*10/5)* 50 is the points you get
                double dist_score = 0;
                if (dist <= .5) {
                    dist_score = (1 - (dist * 10)/5)*50;
                }
                total_score += dist_score;
                total_score += wait_score;
                total_filters++;
            }
            else if (filter.equals("healthy")) {
                // this filter picks restaurants that have high protein and low fat
                // 50 points allotted to being high protein
                // 50 points allotted to being low fat
                String fat = (rest_data.get(restaurant)).get("fat");
                String protein = (rest_data.get(restaurant)).get("protein");
                if (fat.equals("low")) {
                    total_score = +50;
                }
                if (protein.equals("high")) {
                    total_score += 50;
                }
                total_filters++;
            }
            else if (filter.equals("fancy")) {
                // this filter picks restaurants that are sit downs and rated highly
                // 50 points allotted to being a sitdown
                // 50 points allotted to being rated highly, calculated similarly to nearby and wait time
                String sit = (rest_data.get(restaurant)).get("sitdown");
                int rate = Integer.parseInt((rest_data.get(restaurant)).get("rating"));

                if (sit.equals("yes")) {
                    total_score += 50;
                }
                // avg rating % is found
                // and that percentage of 50 points are added to the score
                double rating_score = (double)rate/5 * 50;
                total_score += rating_score;
                total_filters++;
            }
            else { // filter is college
                // this filter picks restaurants that are close and have low wait time and are cheap
                // 30 points allotted to being close
                // 30 points allotted to being low wait time
                // 40 points allotted to being cheap
                double dist = Double.parseDouble((rest_data.get(restaurant)).get("distance"));
                int wait = Integer.parseInt((rest_data.get(restaurant)).get("wait_time"));
                int price = Integer.parseInt((rest_data.get(restaurant)).get("price"));

                // if it is more than 30 minute wait, does not contribute to the score
                double wait_score = 0;
                if (wait <= 30) {
                    wait_score = (1 - (double)wait/30) * 30;
                }
                // if it is more than .5 miles away, does not contribute to the score
                double dist_score = 0;
                if (dist <= .5) {
                    dist_score = (1 - (dist * 10)/5)*30;
                }
                // if it is more than 3 dollars signs expensive, does not contribute to score
                double price_score = 0;
                if (price <= 3) {
                    price_score = (1 - ((double)price/3)) * 40;
                }
                total_score += dist_score;
                total_score += wait_score;
                total_score += price_score;
                total_filters++;
            }

            double final_score = (total_score/((double)total_filters*100));
            final_score *= 100;
            Log.d("debugg", String.valueOf(final_score));
            if (final_score < 1) {
                return 0;
            }
            else if (final_score < 37.5 ) {
                return 25;
            }
            else if (final_score < 62.5) {
                return 50;
            }
            else if (final_score < 87.5) {
                return 75;
            }
            else {
                return 100;
            }
        }
        return -1;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
