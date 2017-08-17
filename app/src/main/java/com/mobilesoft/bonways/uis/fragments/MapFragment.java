package com.mobilesoft.bonways.uis.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    private MapView mapView;
    Location userLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        if (mapView != null) {
            mapView.getMapAsync(this);
        }


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (MainActivity.mShopLocation != null && mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.mShopLocation, 16));

        }
        if (userLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 16));

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                for (Trade trade : MainActivity.mTrade) {
                    if (marker.getTitle().equals(trade.getId())) {
                        Intent intent = new Intent(getActivity(), ProductActivity.class);
                        intent.putExtra("trade", (Parcelable) trade);
                        MainActivity.tmpCurrentTrade = trade;
                        getActivity().startActivity(intent);
                    }
                }

            }
        });
        centerMapToUserLocation();
        loadShop();
    }

    private void loadShop() {
        MarkerOptions options = new MarkerOptions();

//        MainActivity.mTrade.addAll(DummyServer.getInstance().getTrades());
        MainActivity.mTrade.addAll(ProfileManager.getCurrentUserProfile().getTrades());

        for (Trade trade : MainActivity.mTrade) {
            options.title(trade.getId());
            options.draggable(false);
            options.position(new LatLng(trade.getLatitude(), trade.getLongitude()));

            if (trade.getMainCategory().equalsIgnoreCase("Beauté"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_beaute));
            else if (trade.getMainCategory().equalsIgnoreCase("Brico/Deco"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_bricolage));
            else if (trade.getMainCategory().equalsIgnoreCase("Kdo"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_cadeau));
            else if (trade.getMainCategory().equalsIgnoreCase("Transport"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_transport));
            else if (trade.getMainCategory().equalsIgnoreCase("Supermarché"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_epicerie));
            else if (trade.getMainCategory().equalsIgnoreCase("Banque/Service"))
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cat_service));
            else
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.bon_ways_shop));

            mGoogleMap.addMarker(options);
        }
    }

    private void centerMapToUserLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        userLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (userLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 15));
        }
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {


            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {


            for (Trade trade : MainActivity.mTrade) {
                if (marker.getTitle().equals(trade.getId())) {
                    final ImageView image = (view.findViewById(R.id.badge));
                    Picasso.with(getActivity()).load(trade.getLogoUrl()).placeholder(R.mipmap.ic_launcher).into(image);

                    final String title = trade.getName();
                    final TextView titleUi = (view.findViewById(R.id.title));
                    if (title != null) {
                        titleUi.setText(title);
                    } else {
                        titleUi.setText("");
                    }

                    final String snippet = trade.getAddress();
                    final TextView snippetUi = (view
                            .findViewById(R.id.snippet));
                    if (snippet != null) {
                        snippetUi.setText(snippet);
                    } else {
                        snippetUi.setText("");
                    }
                }
            }


            return view;
        }
    }
}
