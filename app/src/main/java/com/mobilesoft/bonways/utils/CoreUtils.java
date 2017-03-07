package com.mobilesoft.bonways.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mobilesoft.bonways.R;

import java.text.DecimalFormat;


public class CoreUtils {

    public static final int ALL_PERMISSIONS_REQUEST = 300;

    public static boolean checkAllPermissions(Context context) {
        if (/*ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || */ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;

        } else {
           /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(getResources().getString(R.string.permission_denied));
            alertBuilder.setMessage(getResources().getString(R.string.permission_denied_explanation_login));
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alert = alertBuilder.create();
            alert.show();*/

            return true;

        }
    }

    public static void alertAndRequestPermission(final Activity context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(context.getResources().getString(R.string.permission_necessary));
        alertBuilder.setMessage(Html.fromHtml(context.getResources().getString(R.string.permission_explanation)));
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(context, new String[]{
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, ALL_PERMISSIONS_REQUEST);
            }
        });

        final AlertDialog alert = alertBuilder.create();

//        alert.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//            }
//        });
        alert.show();
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));

//        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.CAMERA)
//                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//        } else {
//            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{
//                    Manifest.permission.READ_CONTACTS,
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, ALL_PERMISSIONS_REQUEST);
//        }
    }

    public static double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

}
