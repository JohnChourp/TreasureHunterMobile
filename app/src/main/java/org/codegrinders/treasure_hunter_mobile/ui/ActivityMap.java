package org.codegrinders.treasure_hunter_mobile.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.codegrinders.treasure_hunter_mobile.R;

public class ActivityMap extends AppCompatActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        OnMarkerClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private GoogleMap mMap;
    private Marker Library, Canteen, ManagementBuilding;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        LatLng university = new LatLng(41.07529, 23.55330);
        LatLng library = new LatLng(41.07634, 23.55451);
        LatLng canteen = new LatLng(41.07457, 23.55395);
        LatLng managementBuilding = new LatLng(41.07637, 23.55309);
        Library = mMap.addMarker(new MarkerOptions().position(library).title("Library").visible(false));
        Canteen = mMap.addMarker(new MarkerOptions().position(canteen).title("Canteen").visible(false));
        ManagementBuilding = mMap.addMarker(new MarkerOptions().position(managementBuilding).title("Management building").visible(false));

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(university, 17));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        proximityMarkers();
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        return false;
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "I am Here", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        openActivityPuzzles();
        return false;
    }


    private void openActivityPuzzles() {
        Intent intent = new Intent(this, ActivityPuzzle.class);
        startActivity(intent);
    }


    private void proximityMarkers() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        final double[] longitude = {location.getLongitude()};
        final double[] latitude = {location.getLatitude()};

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude[0] = location.getLongitude();
                latitude[0] = location.getLatitude();

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), Library.getPosition()) < 50) {
                    Library.setVisible(true);
                }

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), Library.getPosition()) > 50) {
                    Library.setVisible(false);
                }

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), Canteen.getPosition()) < 50) {
                    Canteen.setVisible(true);
                }

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), Canteen.getPosition()) > 50) {
                    Canteen.setVisible(false);
                }

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), ManagementBuilding.getPosition()) < 50) {
                    ManagementBuilding.setVisible(true);
                }

                if (SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()), ManagementBuilding.getPosition()) > 50) {
                    ManagementBuilding.setVisible(false);
                }

            }


        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}