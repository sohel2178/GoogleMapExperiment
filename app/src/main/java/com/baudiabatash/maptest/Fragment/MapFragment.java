package com.baudiabatash.maptest.Fragment;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baudiabatash.maptest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener{
    private GoogleMap mMap;
    private EditText etAddress;
    private Button btnGo,btnZoom;

    private LatLng dhaka;
    private LatLng gazipur;
    private LatLng tangail;
    private LatLng sirajganj;
    private LatLng latLng;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        btnGo = (Button) view.findViewById(R.id.btn_go);
        btnZoom = (Button) view.findViewById(R.id.zoom);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnGo.setOnClickListener(this);
        btnZoom.setOnClickListener(this);
        //btnZoom = (Button) view.findViewById(R.id.zoom);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        dhaka = new LatLng(23.777176,90.399452);
        gazipur = new LatLng(23.911522,90.388962);
        tangail = new LatLng(24.244968,89.911305);
        sirajganj = new LatLng(24.452646,89.681621);

        test();

        /*gotoLocationWithZoom( 28.592140,77.046051,12);*/

        latLng= new LatLng(23.781541, 90.363739);

        /*mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-18.142, 178.431), 2));

        // Polylines are useful for marking paths and routes on the map.
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(-33.866, 151.195))  // Sydney
                .add(new LatLng(-18.142, 178.431))  // Fiji
                .add(new LatLng(21.291, -157.821))  // Hawaii
                .add(new LatLng(37.423, -122.091))  // Mountain View
        );*/

       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }

    private void gotoLocationWithZoom(double lt, double ln, int zoom) {
        LatLng myLoc = new LatLng(lt,ln);
        MarkerOptions option = new MarkerOptions().position(myLoc).title("My Location");
        mMap.addMarker(option);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,zoom));
    }

    private void test(){
        MarkerOptions option1 = new MarkerOptions().position(dhaka).title("Dhaka");
        MarkerOptions option2 = new MarkerOptions().position(gazipur).title("Gazipur");
        MarkerOptions option3 = new MarkerOptions().position(tangail).title("Tangail");
        MarkerOptions option4 = new MarkerOptions().position(sirajganj).title("Sirajgang");
        mMap.addMarker(option1);
        mMap.addMarker(option2);
        mMap.addMarker(option3);
        mMap.addMarker(option4);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tangail,10));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go:
                String location = etAddress.getText().toString().trim();
                Geocoder geocoder = new Geocoder(getActivity());

                try {
                    List<Address> addressList = geocoder.getFromLocationName(location,1);
                    Address address = addressList.get(0);
                    String locality = address.getLocality();

                    Toast.makeText(getActivity(), locality, Toast.LENGTH_SHORT).show();

                    double lat = address.getLatitude();
                    double lng = address.getLongitude();

                    gotoLocationWithZoom(lat,lng,15);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.zoom:
                if(mMap !=null){

                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .position(latLng));
                }
                break;

        }

    }
}
