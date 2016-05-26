package com.example.durga.hackathon;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeleteMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getLocation(googleMap);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
               // saveLocation(point , googleMap);
               // getnearLocation(point, googleMap);
               // getDirections(point);

                System.out.println(point.latitude+"---"+ point.longitude);
            }
          /* // @Override
            public boolean onMarkerClick(Marker marker) {
                // your code here
                Toast.makeText(getBaseContext(), "inside marker click " , Toast.LENGTH_SHORT).show();
                LatLng myLatLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                getKey(googleMap, myLatLng.latitude,myLatLng.longitude);
                return true;
            } */

        });
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    boolean doNotMoveCameraToCenterMarker = true;
                    public boolean onMarkerClick(Marker marker) {
                        //Do whatever you need to do here ....
                      //  marker.remove();
                     //   marker.setVisible(false);

                //        Toast.makeText(getBaseContext(), "inside marker click " , Toast.LENGTH_SHORT).show();
                        LatLng myLatLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                        getKey(googleMap, myLatLng.latitude,myLatLng.longitude);
                        googleMap.clear();
                        getLocation(googleMap);
                        //return true;

                        //

                        return doNotMoveCameraToCenterMarker;
                    }
                });
     /*   // Setting click event handler for InfoWIndow
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
// Remove the marker
                marker.remove();
            }
        }); */
    }
    private void getLocation( GoogleMap googleMap){
        int mylocation =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);


        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 0; i < mylocation; i++) {

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat" + i, "0");
                latitude = Double.parseDouble(lat);
                //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng" + i, "0");
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);
                // Drawing marker on the map
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("New Marker");

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));
            }

        }
        //  editor.commit();
    }
    private void getKey( GoogleMap googleMap, double Mylatitude, double Mylongitude){
        int mylocation =0;
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.durga.hackathon",MODE_PRIVATE);

      //  Toast.makeText(getBaseContext(), "inside get key " , Toast.LENGTH_SHORT).show();
        //  SavePreferences();
        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        mylocation = sharedPreferences.getInt("noofLocations", 0);
        // Toast.makeText(getBaseContext(),  "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");

        // If locations are already saved
        if(mylocation!=0) {

            String lat = "";
            String lng = "";
            double latitude = 0;
            double longitude = 0;
            // Iterating through all the locations stored
            for (int i = 0; i < mylocation; i++) {

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat" + i, "0");
                latitude = Double.parseDouble(lat);
                //  Toast.makeText(getBaseContext(), "no of locations is " + mylocation, Toast.LENGTH_SHORT).show();
                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng" + i, "0");
                // Toast.makeText(getBaseContext(), "the lat is " + lat, Toast.LENGTH_SHORT).show();
                longitude = Double.parseDouble(lng);
                // Drawing marker on the map
             /*   MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latitude, longitude)).title("New Marker");

                googleMap.addMarker(marker);
                googleMap.addMarker(new MarkerOptions().position( new LatLng(latitude, longitude)));*/
                if (Mylatitude == latitude && Mylongitude == longitude)
                {
                    Toast.makeText(getBaseContext(), "Marker Deleted " , Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().remove("lat" + i).commit();
                    sharedPreferences.edit().remove("lng" + i).commit();

                }
            }

        }
        //  editor.commit();
    }
}
