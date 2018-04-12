package com.ivan.jgntic.taximaptest;

import android.graphics.Camera;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    AutoCompleteTextView searchForDestination;
    Button goButton;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        searchForDestination=(AutoCompleteTextView)findViewById(R.id.searchForDestination);
        goButton=(Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(this);

        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        searchForDestination.setAdapter(arrayAdapter);
        searchForDestination.setThreshold(1);

        searchForDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!arrayList.isEmpty())
                {
                    searchForDestination.showDropDown();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.goButton:

                String getLocation=searchForDestination.getText().toString();

                arrayList.add(getLocation);
                arrayAdapter.notifyDataSetChanged();

                Geocoder geocoder=new Geocoder(this);
                android.location.Address address;
                LatLng latLng;

                try {

                    List<android.location.Address> addressList=geocoder.getFromLocationName(getLocation,1);
                    address=addressList.get(0);

                    latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;


        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getMyLocation();
    }
}
