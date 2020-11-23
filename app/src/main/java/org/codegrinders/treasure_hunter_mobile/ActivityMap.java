package org.codegrinders.treasure_hunter_mobile;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in University and move the camera
        LatLng university = new LatLng(41.07529 , 23.55330);
        LatLng library = new LatLng(41.07634, 23.55451);
        LatLng canteen = new LatLng(41.07457, 23.55395);
        LatLng managementBuilding = new LatLng(41.07637 , 23.55309);
        mMap.addMarker(new MarkerOptions().position(library).title("Library"));
        mMap.addMarker(new MarkerOptions().position(canteen).title("Canteen"));
        mMap.addMarker(new MarkerOptions().position(managementBuilding).title("Management building"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(university, 17));
    }
}