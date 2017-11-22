package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import java.util.List;

public class MapView extends Activity {
    private int     filterSelected = -1;
    private com.mapbox.mapboxsdk.maps.MapView mapView;
    private MapboxMap mapbox_map;
    private Icon icons [];
    private Marker markers [];

    // Icons
    Icon icon_a_purple;
    Icon icon_f_purple;
    Icon icon_a_orange;
    Icon icon_f_orange;
    Icon icon_cur_loc;
    // Markers
    Marker markerOne;
    Marker markerTwo;
    Marker markerThree;
    Marker markerFour;
    // Buttons
    Button compareButton;
    CompoundButton visualizeButton;

    Boolean selectMode = false;
    static final int PICK_FILTER_REQUEST = 1;  // The request code
    ArrayList<String> selected = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoianVzdGlubWVpZCIsImEiOiJjaXlxcGNkaXQwMDJlMndxa3E0cGEzNnBoIn0.LHVEjoFcv0ZD6Dkp7ObVzQ");
        setContentView(R.layout.activity_map_view);

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
                icon_a_purple = IconFactory.getInstance(MapView.this).fromResource(R.drawable.apurple);
                icon_f_purple = IconFactory.getInstance(MapView.this).fromResource(R.drawable.fpurple);
                icon_a_orange = IconFactory.getInstance(MapView.this).fromResource(R.drawable.aorange);
                icon_f_orange = IconFactory.getInstance(MapView.this).fromResource(R.drawable.forange);
                icon_cur_loc = IconFactory.getInstance(MapView.this).fromResource(R.drawable.currentlocation);



                // current location marker
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.110281,-88.232022))
                        .icon(icon_cur_loc)
                );

                // restaurant markers
                markerOne = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.110113,-88.233218))
                        .title("Sakanaya")
                        .snippet("5 Stars")
                        .icon(icon_f_purple)
                );

                markerTwo = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1100646,-88.2296092))
                        .title("Cracked")
                        .snippet("4 Stars")
                        .icon(icon_a_purple)
                );

                markerThree = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1111668573957,-88.2305980101228))
                        .title("Salad Meister")
                        .snippet("4 Stars")
                        .icon(icon_f_purple)
                );

                markerFour = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1105683,-88.2325823))
                        .title("Bangkok Thai and Pho")
                        .snippet("5 Stars")
                        .icon(icon_a_purple)
                );



                // marker click listener
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                       if(selectMode){
                            // toggle all markers except the current location marker
                            if(marker.getIcon()!=icon_cur_loc) {
                                // check if marker has been selected
                                if (selected.contains(marker.getTitle())) {
                                    // toggle icon
                                    if (marker.getIcon() == icon_a_orange) {
                                        marker.setIcon(icon_a_purple);
                                    } else {
                                        marker.setIcon(icon_f_purple);
                                    }


                                    // remove from selected arraylist
                                    selected.remove(marker.getTitle());

                                } else {
                                    // toggle icon
                                    if (marker.getIcon() == icon_a_purple) {
                                        marker.setIcon(icon_a_orange);
                                    } else {
                                        marker.setIcon(icon_f_orange);
                                    }

                                    // add to selected arraylist
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

    public void buttonHandler(View v){
        switch(v.getId())
        {
            case R.id.filterButton:
                Intent i = new Intent(this, Filter.class);
                startActivityForResult(i, PICK_FILTER_REQUEST);
                break;

            case R.id.randomButton:
                Intent k = new Intent(this, LuckyCoin.class);
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
                j.putExtra("title", selected.get(0) + " vs. " + selected.get(1));
                startActivity(j);
                break;

        }
    }

    public void updateMarkers(){
        switch (filterSelected){
            case 0:
                markerOne.setIcon(icon_a_purple);
                markerTwo.setIcon(icon_f_purple);
                markerThree.setIcon(icon_a_purple);
                markerFour.setIcon(icon_f_purple);
                break;
            case 1:
                markerOne.setIcon(icon_f_purple);
                markerTwo.setIcon(icon_a_purple);
                markerThree.setIcon(icon_f_purple);
                markerFour.setIcon(icon_a_purple);
                break;
            case 2:
                markerOne.setIcon(icon_f_purple);
                markerTwo.setIcon(icon_a_purple);
                markerThree.setIcon(icon_a_purple);
                markerFour.setIcon(icon_f_purple);
                break;
            case 3:
                markerOne.setIcon(icon_a_purple);
                markerTwo.setIcon(icon_f_purple);
                markerThree.setIcon(icon_f_purple);
                markerFour.setIcon(icon_a_purple);
                break;
        }

    }

    public void DeselectAllMarkers(){
        Marker m []= new Marker[]{markerOne, markerTwo, markerThree, markerFour};
        for(Marker mark: m){
            if (mark.getIcon() == icon_a_orange) {
                mark.setIcon(icon_a_purple);
            }
            else if (mark.getIcon() == icon_f_orange){
                mark.setIcon(icon_f_purple);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_FILTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                filterSelected = data.getIntExtra("filter", -1);
                String filterNames []= new String[]{ "College", "Hurry", "Fancy", "Healthy"};
                if(filterSelected > -1){
                    Toast.makeText(this, filterNames[filterSelected], Toast.LENGTH_SHORT).show();
                }
                updateMarkers();
            }
        }
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
