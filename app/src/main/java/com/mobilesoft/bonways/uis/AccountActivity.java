package com.mobilesoft.bonways.uis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Consumer;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {

    CircularImageView profilePicture;
    TextView username;
    TextView email;
    LinearLayout subscription;
    LinearLayout myProducts;
    LinearLayout publishProducts;

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
        myProducts = (LinearLayout) findViewById(R.id.shops);
        publishProducts = (LinearLayout) findViewById(R.id.products);

        final Profile profile = ProfileManager.getCurrentUserProfile();
        try {
            Consumer currentConsumer = profile.getConsumer();
            if (currentConsumer != null && currentConsumer.getImageUrl() != null) {
                Picasso.with(this).load(currentConsumer.getImageUrl()).into(profilePicture);
                username.setText(currentConsumer.getDisplayName());
                email.setText(currentConsumer.getEmail());
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
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ShopActivity.class));
            }
        });
        publishProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile.getTrades().size() != 0)
                    startActivity(new Intent(AccountActivity.this, AddProductWizardActivity.class));
                else{
                    Toast.makeText(AccountActivity.this, getResources().getString(R.string.need_shop_first), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AccountActivity.this, AddShopWizardActivity.class));
                }

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

}
