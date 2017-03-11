package com.mobilesoft.bonways.uis;

import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    ImageView imageProduct;
    ImageView shopLogo;
    ImageView iconLiked;
    TextView titleProduct;
    TextView descriptionProduct;
    LinearLayout containerLiked;
    TextView normalPrice;
    TextView promoPrice;
    TextView shopName;
    TextView shopDistance;
    TextView percentageDiscount;
    TextView timeOff;
    TextView liked;
    TextView watched;
    TextView currency;
    Product mProduct;
    Product mClone;
    private boolean alreadyRemoved;

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
//     getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        timeOff = (TextView) findViewById(R.id.timeleft);
        shopName = (TextView) findViewById(R.id.shop_name);
        shopDistance = (TextView) findViewById(R.id.shop_distance);
        currency = (TextView) findViewById(R.id.currency);
        currency.setRotation(310);
        containerLiked = (LinearLayout) findViewById(R.id.container_social_liked);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(1);

            mProduct = b.getParcelable("product");
            if (mProduct != null) {
                mClone = mProduct.clone();
                getSupportActionBar().setTitle(mProduct.getDesignation());
                Picasso.with(this).load(mProduct.getImageUrl()).into(imageProduct);
                Picasso.with(this).load(mProduct.getTrade().getLogoUrl()).into(shopLogo);
                titleProduct.setText(mProduct.getDesignation());
                descriptionProduct.setText(mProduct.getDescription());
                shopName.setText(mProduct.getTrade().getName());

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
                timeOff.setText(mProduct.getDateTimeOff());

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
        super.onPause();

    }
}
