package com.mobilesoft.bonways.uis.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.mobilesoft.bonways.BonWaysApplication;
import com.mobilesoft.bonways.BuildConfig;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.AddShopWizardActivity;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AddProductStep2 extends AbstractStep {

    private static final String TAG = "AddProductStep2";
    private int i = 1;
    Trade mTrade;
    protected static File OUTPUT_DIR = BonWaysApplication.getInstance().getDir(BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE, Context.MODE_PRIVATE);
    public final static String TRADE = "TRADE";
    EditText shopName;
    EditText shopPhone;
    EditText shopWebsite;
    EditText shopRepresenter;
    EditText shopEmail;
    ImageView shopLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_shop_1, container, false);
        shopName = (EditText) v.findViewById(R.id.shopName);
        shopPhone = (EditText) v.findViewById(R.id.shopPhone);
        shopEmail = (EditText) v.findViewById(R.id.shopEmail);
        shopRepresenter = (EditText) v.findViewById(R.id.shopRepresenter);
        shopWebsite = (EditText) v.findViewById(R.id.shopWebsite);
        shopLogo = (ImageView) v.findViewById(R.id.shopLogo);

        shopLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(shopLogo);
            }
        });

        ImagePicker.setMinQuality(200, 200);

        mTrade = new Trade();

        if (savedInstanceState != null) {
            mTrade = savedInstanceState.getParcelable(TRADE);
            if (mTrade != null) {
//                shopName.setText(mTrade.getName());
//                shopPhone.setText(mTrade.getPhone());
//                shopEmail.setText(mTrade.getEmail());
//                shopWebsite.setText(mTrade.getWebsite());
//                shopRepresenter.setText(mTrade.getRepresenterName());
//                Picasso.with(getActivity()).load("file://"+mTrade.getLogoUrl()).into(catLogo);
            }

        }

//        button.setText(Html.fromHtml("Tap <b>" + i + "</b>"));
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((Button) view).setText(Html.fromHtml("Tap <b>" + (++i) + "</b>"));
//                mStepper.getExtras().putInt(TRADE, i);
//            }
//        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(TRADE, mTrade);
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
    }

    @Override
    public void onNext() {
        System.out.println("onNext");

//        if (ProfileManager.getCurrentUserProfile().getUser())
        mTrade.setEmail(shopEmail.getText().toString());
        mTrade.setName(shopName.getText().toString());
        mTrade.setPhone(shopPhone.getText().toString());
        mTrade.setRepresenterName(shopRepresenter.getText().toString());
        mTrade.setWebsite(shopWebsite.getText().toString());
        Log.d(TAG, ""+mTrade);
        AddShopWizardActivity.mTrade = mTrade;



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
        // TODO: 28/01/2017 check condition for progress
        return i > 1;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
        if (bitmap != null) {
            shopLogo.setImageBitmap(bitmap);

            File destination;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            destination = new File(OUTPUT_DIR,
                    "logoshop" + "_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mTrade.setLogoUrl(destination.getAbsolutePath());

        }
    }

    public void onPickImage(View view) {
        ImagePicker.pickImage(this, "Select your logo:");
    }
}
