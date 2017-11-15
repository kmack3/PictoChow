package edu.illinois.cs465.pictochow;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapView extends Activity {

    private com.mapbox.mapboxsdk.maps.MapView mapView;
    private Button randomButton;
    private Button  filterButton;
    private Button  visualizeButton;
    private boolean selectMode = false;
    private int     numberSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoianVzdGlubWVpZCIsImEiOiJjaXlxcGNkaXQwMDJlMndxa3E0cGEzNnBoIn0.LHVEjoFcv0ZD6Dkp7ObVzQ");
        setContentView(R.layout.activity_main);


        randomButton = (Button) findViewById(R.id.randomButton);
        filterButton = (Button) findViewById(R.id.filterButton);
        visualizeButton = (Button) findViewById(R.id.visualizeButton);

        // the following link has how to create intent from these buttons


        // map stuff

        mapView = (com.mapbox.mapboxsdk.maps.MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // get icons
                final Icon icon_a_purple = IconFactory.getInstance(MapView.this).fromResource(R.drawable.apurple);
                Icon icon_f_purple = IconFactory.getInstance(MapView.this).fromResource(R.drawable.fpurple);
                final Icon icon_a_orange = IconFactory.getInstance(MapView.this).fromResource(R.drawable.aorange);
                final Icon icon_f_orange = IconFactory.getInstance(MapView.this).fromResource(R.drawable.forange);

                // One way to add a marker view
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.110113,-88.233218))
                        .title("Sakanaya")
                        .snippet("5 Stars")
                        .icon(icon_a_purple)
                );

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1100646,-88.2296092))
                        .title("Cracked")
                        .snippet("4 Stars")
                        .icon(icon_a_purple)
                );

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1111668573957,-88.2305980101228))
                        .title("Salad Meister")
                        .snippet("4 Stars")
                        .icon(icon_a_purple)
                );

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.1105683,-88.2325823))
                        .title("Bangkok Thai and Pho")
                        .snippet("5 Stars")
                        .icon(icon_a_purple)
                );

                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(selectMode == true) {
                            if(marker.getIcon() == icon_a_orange){
                                marker.setIcon(icon_a_purple);
                                numberSelected = numberSelected + 1;
                                if(numberSelected >= 1){
                                    // call some function to go to visualization activity

                                }
                            }
                            else {
                                marker.setIcon(icon_a_orange);
                                numberSelected--;
                            }
                        }
                        return false;
                    }
                });
            }
        });


    }


    public void visualizeButtonClick(View v) {
        if (selectMode == false){
            selectMode = true;
            Toast.makeText(this, "Select two restaurants", Toast.LENGTH_SHORT).show();
        }
        else {
            selectMode = false;
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
