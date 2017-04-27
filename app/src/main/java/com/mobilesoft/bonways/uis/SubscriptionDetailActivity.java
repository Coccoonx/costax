package com.mobilesoft.bonways.uis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Reservation;
import com.mobilesoft.bonways.core.models.SubscriptionType;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SubscriptionDetailActivity extends AppCompatActivity {

    public TextView name;
    public TextView description;
    public TextView price;
    public TextView howToDescription;
    public TextView validity;
    public ImageView logo;
    private Button submit;
    private EditText activationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.item_bundle_name);
        description = (TextView) findViewById(R.id.item_bundle_description);
        price = (TextView) findViewById(R.id.item_bundle_price);
        validity = (TextView) findViewById(R.id.item_bundle_validity);
        howToDescription = (TextView) findViewById(R.id.item_how_to_description);
        logo = (ImageView) findViewById(R.id.bundle_image);

        activationCode = (EditText)findViewById(R.id.activationCode);
        submit = (Button)findViewById(R.id.submit);

        howToDescription.setText(Html.fromHtml(getResources().getString(R.string.how_to_subs_description)));
        
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            SubscriptionType type =  bundle.getParcelable("subscription");
            getSupportActionBar().setTitle(type.getName()+" - Details");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(1);
            name.setText(type.getName());
            description.setText(type.getDescription());
            double promo = type.getCost();
            price.setText(nf.format(promo) + " F");
            validity.setText( type.getValidity()+" " + getResources().getString(R.string.label_timeleft_value));
            if (type.getImageUrl() != null && (type.getImageUrl().contains("http") || type.getImageUrl().contains("cdn"))) {
                Picasso.with(this).load(type.getImageUrl())
                        .placeholder(R.mipmap.ic_launcher).into(logo);
            } else
                Picasso.with(this).load(type.getImageInt())
                        .placeholder(R.mipmap.ic_launcher).into(logo);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = activationCode.getText().toString().trim();
                if (!code.isEmpty()) {
                    new SweetAlertDialog(SubscriptionDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Confirm Activation")
                            .setContentText("Is the code correct ?")
                            .setConfirmText("Yes!")
                            .setCancelText("No")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    new SweetAlertDialog(SubscriptionDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success !!")
                                            .setContentText("Activation Succeed.")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    onBackPressed();
                                                }
                                            })
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
                    //TODO Connect with backend and get the Subscribed object
//                    ProfileManager.getCurrentUserProfile().getUser().setSubscriptionType();
                }
            }
        });
        

       
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
