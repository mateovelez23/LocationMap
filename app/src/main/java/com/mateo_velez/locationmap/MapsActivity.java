package com.mateo_velez.locationmap;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double latitude=0, longitude=0;
    double lat1, lat2, lat3, lat4, long1, long2, long3, long4, cte=0.0009999;
    private GoogleMap mMap;
    SharedPreferences sharedPreferences;
    int i=0;
    Marker m[]=new Marker[5];
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        latitude=Double.parseDouble(sharedPreferences.getString("latitud","0"));
        longitude=Double.parseDouble(sharedPreferences.getString("longitud","0"));
        Log.d("LATITUD",String.valueOf(latitude));
        Log.d("LONGITUD",String.valueOf(longitude));

        lat1=latitude-cte;
        long1=longitude-cte;

        lat2=latitude-cte;
        long2=longitude+cte;

        lat3=latitude+cte;
        long3=longitude-cte;

        lat4=latitude+cte;
        long4=longitude+cte;


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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng position = new LatLng(latitude,longitude);
        m[0] = mMap.addMarker(new MarkerOptions().position(position).title("Acá estás"));

        LatLng pos1 = new LatLng(lat1, long1);
        m[1] = mMap.addMarker(new MarkerOptions().position(pos1).title("¡Sabemos donde estás!"));


        LatLng pos2 = new LatLng(lat2, long2);
        m[2] = mMap.addMarker(new MarkerOptions().position(pos2).title("¡Iremos por ti!"));


        LatLng pos3 = new LatLng(lat3, long3);
        m[3] = mMap.addMarker(new MarkerOptions().position(pos3).title("¡Ni se te ocurra escapar!"));


        LatLng pos4 = new LatLng(lat4, long4);
        m[4] = mMap.addMarker(new MarkerOptions().position(pos4).title("¡Dejénmelo a mi!"));

        for(i=0;i<5;i++){
            countDownTimer = new CountDownTimer(1000,500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    Toast.makeText(MapsActivity.this, "ACA ESTAS", Toast.LENGTH_SHORT).show();
                    //m[i].showInfoWindow();
                }
            };
            countDownTimer.start();
        }

        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,17));
    }
}
