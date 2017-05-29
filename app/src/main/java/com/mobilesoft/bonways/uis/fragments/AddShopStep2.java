package com.mobilesoft.bonways.uis.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.storage.BonWaysSettingsUtils;
import com.mobilesoft.bonways.uis.AddShopWizardActivity;
import com.squareup.picasso.Picasso;


public class AddShopStep2 extends AbstractStep implements OnMapReadyCallback {

    private int i = 1;
//    private Button button;
    private final static String TRADE = "TRADE";
    private final static String TAG = "AddShopStep2";
    private MapView map;
    private GoogleMap mGoogleMap;
    Trade trade;
    EditText nearestShop;
    EditText address;
    Location shopLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_shop_2, container, false);

        nearestShop = (EditText)v.findViewById(R.id.shopNearestShop);
        address = (EditText)v.findViewById(R.id.shopAddress);

        map = (MapView) v.findViewById(R.id.mapViewLocation);
        try {
            // Temporary fix for crash issue
            map.onCreate(savedInstanceState);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (map != null) {
            map.getMapAsync(this);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
//        state.putInt(CLICK, i);
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
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mGoogleMap.clear();
                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Shop")
                        .snippet(trade.getName()));
                trade.setLongitude(latLng.longitude);
                trade.setLatitude(latLng.latitude);
            }
        });
//        centerMapToUserLocation();
    }

    @Override
    public String name() {
        return "Tab " + getArguments().getInt("position", 0);
    }

    @Override
    public boolean isOptional() {
        return true;
    }


    @Override
    public void onStepVisible() {
        Log.d(TAG, ""+ AddShopWizardActivity.mTrade);
        trade = AddShopWizardActivity.mTrade;
//        Bundle bundle = getLastStepData();
//        if (bundle != null) {
//            Log.d(TAG, ""+bundle.getParcelable("TRADE"));
//        }
    }

    @Override
    public void onNext() {
        System.out.println("onNext");
        trade.setAddress(address.getText().toString());
        trade.setNearestShopName(nearestShop.getText().toString());
        trade.setConsumer(ProfileManager.getCurrentUserProfile().getConsumer());

        //// TODO: 28/01/2017 Get Shop Location

        AddShopWizardActivity.mTrade = trade;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onPrevious() {
        System.out.println("onPrevious");
    }

    @Override
    public String optional() {
        return "You can skip";
    }

    @Override
    public boolean nextIf() {
        //// TODO: 28/01/2017 check condition for progress
        return i > 1;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }
}
