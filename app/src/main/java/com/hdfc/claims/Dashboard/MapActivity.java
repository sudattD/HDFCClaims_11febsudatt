package com.hdfc.claims.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hdfc.claims.R;

public class MapActivity extends AppCompatActivity {

    private View view;
    Context context;

    private static GoogleMap googleMap;

    SupportMapFragment supportMapFragment;

    public MapActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        context = MapActivity.this;

        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to load map. If map is not created it will create it for you
     */


/*
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // latitude and longitude
            double latitude = 23.038316 ;
            double longitude = 72.511938 ;

            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Synoverge Technologies");

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            // adding marker
            googleMap.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(15).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initilizeMap() {

  	FragmentManager fm = this.getSupportFragmentManager();
            supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
            if (supportMapFragment == null) {
                 supportMapFragment = SupportMapFragment.newInstance();
                fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
            }

        googleMap = supportMapFragment.getMap();
        double latitude = 23.038316 ;
        double longitude = 72.511938 ;
        LatLng latlong = new LatLng(latitude, longitude);

        googleMap.addMarker(new MarkerOptions().position(latlong).title("Hari Motor Works").draggable(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        if (googleMap == null) {
            Toast.makeText(this,
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
