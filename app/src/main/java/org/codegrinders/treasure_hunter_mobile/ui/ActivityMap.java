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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.codegrinders.treasure_hunter_mobile.MapData;
import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroInstance;

public class ActivityMap extends AppCompatActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private Marker Library, Canteen, ManagementBuilding;
    RetroInstance retroInstance = new RetroInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        retroInstance.setCallListener(new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {

                LatLng university = new LatLng(41.07529, 23.55330);
                LatLng library = new LatLng(retroInstance.getMarkers().get(0).getLatitude(), retroInstance.getMarkers().get(0).getLongitude());
                LatLng canteen = new LatLng(retroInstance.getMarkers().get(1).getLatitude(), retroInstance.getMarkers().get(1).getLongitude());
                LatLng managementBuilding = new LatLng(retroInstance.getMarkers().get(2).getLatitude(), retroInstance.getMarkers().get(2).getLongitude());
                Library = mMap.addMarker(new MarkerOptions().position(library).title(retroInstance.getMarkers().get(0).getMarkerTile()).snippet(retroInstance.getMarkers().get(0).getSnippet()).visible(false));
                Canteen = mMap.addMarker(new MarkerOptions().position(canteen).title(retroInstance.getMarkers().get(1).getMarkerTile()).snippet(retroInstance.getMarkers().get(1).getSnippet()).visible(false));
                ManagementBuilding = mMap.addMarker(new MarkerOptions().position(managementBuilding).title(retroInstance.getMarkers().get(2).getMarkerTile()).snippet(retroInstance.getMarkers().get(2).getSnippet()).visible(false));


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(university, 17));

                MapData.names.add(Library.getTitle());
                MapData.names.add(Canteen.getTitle());
                MapData.names.add(ManagementBuilding.getTitle());

                proximityMarkers();

            }

            @Override
            public void onCallFailed(String errorMessage) {

            }
        });

        retroInstance.markersGetRequest();

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

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
    public void onInfoWindowClick(Marker marker) {
        MapData.markerName = marker.getTitle();
        openActivityPuzzles();
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

        final LocationListener locationListener = location1 -> {
            longitude[0] = location1.getLongitude();
            latitude[0] = location1.getLatitude();

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), Library.getPosition()) < 50) {
                Library.setVisible(true);
            }

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), Library.getPosition()) > 50) {
                Library.setVisible(false);
            }

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), Canteen.getPosition()) < 50) {
                Canteen.setVisible(true);
            }

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), Canteen.getPosition()) > 50) {
                Canteen.setVisible(false);
            }

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), ManagementBuilding.getPosition()) < 50) {
                ManagementBuilding.setVisible(true);
            }

            if (SphericalUtil.computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), ManagementBuilding.getPosition()) > 50) {
                ManagementBuilding.setVisible(false);
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}