package com.mobilesoft.bonways.uis;

import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Reservation;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    ImageView imageProduct;
    ImageView shopLogo;
    ImageView iconLiked;
    ImageView reserved;
    TextView titleProduct;
    TextView descriptionProduct;
    LinearLayout containerLiked;
    TextView normalPrice;
    TextView promoPrice;
    TextView shopName;
    TextView tradePhone;
    TextView shopDistance;
    TextView percentageDiscount;
    TextView timeOff;
    TextView liked;
    TextView productLeft;
    TextView watched;
    TextView category;
    TextView currency;
    Product mProduct;
    Product mClone;
    CircularImageView go;
    Button reserve;
    public static DisplayShop instance;
    private TextView timePosted;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reserved = (ImageView) findViewById(R.id.reserved);

        imageProduct = (ImageView) findViewById(R.id.productImage);
        shopLogo = (ImageView) findViewById(R.id.shop_logo);
        iconLiked = (ImageView) findViewById(R.id.icon_liked);
        titleProduct = (TextView) findViewById(R.id.productTitle);
        descriptionProduct = (TextView) findViewById(R.id.product_description);

        normalPrice = (TextView) findViewById(R.id.normal_price);
        promoPrice = (TextView) findViewById(R.id.promo_price);
        percentageDiscount = (TextView) findViewById(R.id.percentage_discount);
        liked = (TextView) findViewById(R.id.item_liked);
        watched = (TextView) findViewById(R.id.item_watched);
        category = (TextView) findViewById(R.id.category);
        timePosted = (TextView) findViewById(R.id.timeposted);
        timeOff = (TextView) findViewById(R.id.timeleft);
        productLeft = (TextView) findViewById(R.id.productleft);
        shopName = (TextView) findViewById(R.id.shop_name);
        shopDistance = (TextView) findViewById(R.id.shop_distance);
        tradePhone = (TextView) findViewById(R.id.shop_phone);
        currency = (TextView) findViewById(R.id.currency);
//        currency.setRotation(310);
        containerLiked = (LinearLayout) findViewById(R.id.container_social_liked);
        go = (CircularImageView) findViewById(R.id.button_go);
        reserve = (Button) findViewById(R.id.button_reserve);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(1);

            mProduct = b.getParcelable("product");
            if (mProduct != null) {
                mClone = mProduct.clone();
                getSupportActionBar().setTitle(mProduct.getDesignation());

                if (mProduct.getImageUrl().contains("http") || mProduct.getImageUrl().contains("cdn"))
                    Picasso.with(this).load(mProduct.getImageUrl()).into(imageProduct);
                else
                    Picasso.with(this).load("file://" + mProduct.getImageUrl()).into(imageProduct);

                for (Reservation reservation : ProfileManager.getCurrentUserProfile().getMyReservations()) {
                    if (reservation.getObject().getCode().equals(mProduct.getCode())) {
                        reserved.setVisibility(View.VISIBLE);
                    }
                }

                Picasso.with(this).load(mProduct.getTrade().getLogoUrl()).into(shopLogo);
                titleProduct.setText(mProduct.getDesignation());
                descriptionProduct.setText(mProduct.getDescription());
                shopName.setText(mProduct.getTrade().getName());
                category.setText(mProduct.getCategory().getTitle());
                timePosted.setText(mProduct.getCreatedDate());
                productLeft.setText("" + mProduct.getUnitQuantity());
                tradePhone.setText(mProduct.getTrade().getPhone());

                if (MainActivity.mUserLocation != null) {
                    Location tradeLoc = new Location(mProduct.getTrade().getName());
                    tradeLoc.setLatitude(mProduct.getTrade().getLatitude());
                    tradeLoc.setLongitude(mProduct.getTrade().getLongitude());
                    int distance = (int) MainActivity.mUserLocation.distanceTo(tradeLoc);
                    if (distance < 1000) {
                        shopDistance.setText(nf.format(distance) + " m");
                    } else {
                        double d = distance / 1000.0;
                        shopDistance.setText(nf.format(d) + " km");
                    }
                }

                percentageDiscount.setText("-" + nf.format(mProduct.getDiscountPercentage()) + "%");
                liked.setText("" + mProduct.getLikers().size());

                if (!mProduct.isWatched()) {
                    mProduct.setWatched(true);
                    mProduct.getWatchers().add(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                }
                watched.setText("" + mProduct.getWatchers().size());

                normalPrice.setText(nf.format(mProduct.getPrice()) + "");
                normalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                double promo = mProduct.getPrice() - (mProduct.getPrice() * mProduct.getDiscountPercentage() / 100);
                promoPrice.setText(nf.format(promo) + "");


                Calendar calendar = Calendar.getInstance(Locale.FRENCH);
                Log.d(TAG, "" + calendar.getTime());
                if (new Date(Date.parse(mProduct.getDateTimeOff())).after(calendar.getTime())) {
                    t = new Thread() {

                        @Override
                        public void run() {
                            try {
                                while (!isInterrupted()) {
                                    Thread.sleep(1000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // update TextView here!


                                            long diff = Date.parse(mProduct.getDateTimeOff()) - new Date().getTime();
                                            Log.d("TIMER", "" + diff);
                                            int timeInSeconds = Math.abs((int) diff / 1000);
                                            int hours, minutes, seconds;
                                            hours = timeInSeconds / 3600;
                                            if (hours > 24) {
                                                int days = hours / 24;
                                                timeOff.setText(days + " " + getResources().getString(R.string.label_timeleft_value));

                                            } else {
                                                timeInSeconds = timeInSeconds - (hours * 3600);
//                                                if (timeInSeconds < 0) {
//
//                                                    timeInSeconds = (hours * 3600) - timeInSeconds;
//                                                }
                                                Log.d(TAG, "timeInSeconds " + timeInSeconds);

                                                minutes = timeInSeconds / 60;
                                                timeInSeconds = timeInSeconds - (minutes * 60);
                                                seconds = timeInSeconds;

                                                String diffTime = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                                                Log.d(TAG, "" + diffTime);

                                                timeOff.setText(diffTime);
                                            }


                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    };

                    t.start();


                } else {
                    timeOff.setText("OFF");
                    timeOff.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

                if (mProduct.isLiked()) {
                    iconLiked.setImageResource(R.drawable.ic_like_filled);
                } else {
                    iconLiked.setImageResource(R.drawable.ic_like);
                }


                containerLiked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mProduct.isLiked()) {
                            mProduct.setLiked(false);
                            mProduct.getLikers().remove(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                            iconLiked.setImageResource(R.drawable.ic_like);
                        } else {
                            mProduct.setLiked(true);
                            mProduct.getLikers().add(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                            iconLiked.setImageResource(R.drawable.ic_like_filled);
                        }
                        liked.setText("" + mProduct.getLikers().size());
                        containerLiked.invalidate();
                    }
                });

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        instance.showShop(new LatLng(mProduct.getTrade().getLatitude(), mProduct.getTrade().getLongitude()));
                        finish();
                    }
                });

                reserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new SweetAlertDialog(DetailsActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Confirm Reservation")
                                .setContentText("Would you want to reserve this product ?")
                                .setConfirmText("Yes!")
                                .setCancelText("No")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Reservation reservation = new Reservation();
                                        reservation.setObject(mProduct);
                                        ProfileManager.getCurrentUserProfile().getMyReservations().add(reservation);
                                        new ProfileManager.SaveProfile().execute(ProfileManager.getCurrentUserProfile());
                                        sDialog.dismiss();
                                        reserved.setVisibility(View.VISIBLE);
                                        new SweetAlertDialog(DetailsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Confirm Reservation")
                                                .setContentText("Confirmed!")
                                                .show();
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                });
            }
        }

    }

    @Override
    protected void onPause() {
        if (mProduct != null && mClone != null) {
//            if (alreadyRemoved){

            MainActivity.mProducts.remove(mClone);
            MainActivity.mProducts.add(mProduct);
//            }
//            else{
//                MainActivity.mProducts.remove(mProduct);
//                MainActivity.mProducts.add(mProduct);
//            }


        }
        if (!t.isInterrupted())
            t.interrupt();
        super.onPause();

    }

    public interface DisplayShop {
        void showShop(LatLng location);
    }
}
