package com.mobilesoft.bonways.uis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.User;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {

    CircularImageView profilePicture;
    TextView username;
    TextView email;
    LinearLayout subscription;
    LinearLayout shops;
    LinearLayout products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilePicture = (CircularImageView) findViewById(R.id.profilePicture);
        username = (TextView) findViewById(R.id.account_username);
        email = (TextView) findViewById(R.id.account_email);
        subscription = (LinearLayout) findViewById(R.id.subscription);
        shops = (LinearLayout) findViewById(R.id.shops);
        products = (LinearLayout) findViewById(R.id.products);

        Profile profile = ProfileManager.getCurrentUserProfile();
        try {
            User currentUser = profile.getUser();
            if (currentUser != null && currentUser.getImageUrl() != null) {
                Picasso.with(this).load(currentUser.getImageUrl()).into(profilePicture);
                username.setText(currentUser.getDisplayName());
                email.setText(currentUser.getEmail());
            }
        } catch (NullPointerException e) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            ProfileManager.delete();
            finish();
            return;
        }

        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, SubscriptionActivity.class));

            }
        });
        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ShopActivity.class));
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ProductActivity.class));

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

}
