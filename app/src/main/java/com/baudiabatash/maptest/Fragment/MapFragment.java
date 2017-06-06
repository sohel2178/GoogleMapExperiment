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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener{
    private GoogleMap mMap;
    private EditText etAddress;
    private Button btnGo;


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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnGo.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gotoLocationWithZoom(23.781541, 90.363739,16);

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

    @Override
    public void onClick(View v) {
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
    }
}
