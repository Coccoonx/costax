package com.mobilesoft.bonways.uis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Product;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageProduct;
    ImageView shopLogo;
    TextView titleProduct;
    TextView descriptionProduct;

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
        titleProduct = (TextView) findViewById(R.id.productTitle);
        descriptionProduct = (TextView) findViewById(R.id.productDescription);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            Product product = b.getParcelable("product");
            if (product != null) {
                getSupportActionBar().setTitle(product.getDesignation());
                Picasso.with(this).load(product.getImageUrl()).into(imageProduct);
                Picasso.with(this).load(product.getTrade().getLogoUrl()).into(shopLogo);
                titleProduct.setText(product.getDesignation());
                descriptionProduct.setText(product.getDescription());
            }
        }

    }

}
