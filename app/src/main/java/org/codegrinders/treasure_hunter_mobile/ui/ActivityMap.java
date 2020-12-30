package org.codegrinders.treasure_hunter_mobile.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

import org.codegrinders.treasure_hunter_mobile.R;
import org.codegrinders.treasure_hunter_mobile.model.User;
import org.codegrinders.treasure_hunter_mobile.retrofit.MarkersCall;
import org.codegrinders.treasure_hunter_mobile.retrofit.PuzzlesCall;
import org.codegrinders.treasure_hunter_mobile.retrofit.RetroCallBack;

import java.util.ArrayList;
import java.util.List;

public class ActivityMap extends AppCompatActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private final List<Marker> markerList = new ArrayList<>();
    private final MarkersCall markersCall = new MarkersCall();

    Button bt_leaderBoard;
    TextView tv_username;
    TextView tv_points;

    PuzzlesCall puzzlesCall = new PuzzlesCall();
    RetroCallBack retroCallBack;

    User user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        bt_leaderBoard = findViewById(R.id.bt_leaderBoard);
        bt_leaderBoard.setOnClickListener(v -> openActivityLeaderBoard());
        tv_points = findViewById(R.id.tv_points);
        tv_username = findViewById(R.id.tv_username);


        user = (User) getIntent().getSerializableExtra("User");
        tv_username.setText(user.getUsername());
        tv_points.setText("Score: " + user.getPoints());

    }

    private void openActivityLeaderBoard() {
        Intent intent = new Intent(this, ActivityLeaderBoard.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        retroCallBack = new RetroCallBack() {
            @Override
            public void onCallFinished(String callType) {
                if (callType.equals("Markers")) {
                    for (int i = 0; i < markersCall.getMarkers().size(); i++) {
                        markerList.add(mMap.addMarker(new MarkerOptions().position(new LatLng(markersCall.getMarkers().get(i).getLatitude(),
                                markersCall.getMarkers().get(i).getLongitude())).title(markersCall.getMarkers().get(i).getTitle()).snippet(markersCall.getMarkers().get(i).getSnippet()).visible(false)));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.07529, 23.55330), 17));
                    proximityMarkers();
                }
            }

            @Override
            public void onCallFailed(String errorMessage) {

            }
        };

        markersCall.setCallBack(retroCallBack);
        puzzlesCall.setCallBack(retroCallBack);
        puzzlesCall.puzzlesGetRequest();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        puzzlesCall.searchPuzzleByID(markersCall.searchMarkerByTitle(marker.getTitle()).getPuzzleId());
        openActivityPuzzles();
    }

    private void proximityMarkers() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            for (int i = 0; i < markersCall.getMarkers().size(); i++) {
                markerList.get(i).setVisible(SphericalUtil
                        .computeDistanceBetween(new LatLng(location1.getLatitude(), location1.getLongitude()), markerList.get(i).getPosition()) < 50
                        && markersCall.getMarkers().get(i).getVisibility());
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    private void openActivityPuzzles() {
        Intent intent = new Intent(this, ActivityPuzzle.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        markersCall.markersGetRequest();
    }
}