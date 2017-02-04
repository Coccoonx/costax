package com.mobilesoft.bonways.uis.fragments;

import android.app.DatePickerDialog;
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
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.AddProductWizardActivity;
import com.mobilesoft.bonways.uis.AddShopWizardActivity;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AddProductStep1 extends AbstractStep {

    private static final String TAG = "AddProductStep1";
    protected static File OUTPUT_DIR = BonWaysApplication.getInstance().getDir(BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE, Context.MODE_PRIVATE);
    EditText productName;
    EditText productNormalPrice;
    EditText productPromoPrice;
    EditText productQuantity;
    EditText productTimeOff;
    EditText productCategory;
    EditText productShop;
    ImageView productImage;

    Product mProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_product_1, container, false);
        productName = (EditText) v.findViewById(R.id.productName);
        productNormalPrice = (EditText) v.findViewById(R.id.productNormalPrice);
        productPromoPrice = (EditText) v.findViewById(R.id.productPromoPrice);
        productQuantity = (EditText) v.findViewById(R.id.productQuantity);
        productTimeOff = (EditText) v.findViewById(R.id.productTimeOff);
        productCategory = (EditText) v.findViewById(R.id.productCategory);
        productShop = (EditText) v.findViewById(R.id.productShop);
        productImage = (ImageView) v.findViewById(R.id.productImage);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(productImage);
            }
        });

        ImagePicker.setMinQuality(250, 200);


        if (savedInstanceState != null) {

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
    public void onResume() {
        super.onResume();
        if (mProduct == null)
            mProduct = new Product();
    }

    @Override
    public void onStepVisible() {

    }

    @Override
    public void onNext() {

        mProduct.setDesignation(productName.getText().toString());
        mProduct.setPrice(Double.valueOf(productNormalPrice.getText().toString()));
        mProduct.setUnitQuantity(Long.valueOf(productQuantity.getText().toString()));
//       // TODO: 29/01/2017 mProduct.setCategory();
        // // TODO: 29/01/2017  Update product shop mProduct.setTrade();
        mProduct.setDiscount(Double.valueOf(productPromoPrice.getText().toString()));
        mProduct.setDateTimeOff(productTimeOff.getText().toString());

//        mStepper.getExtras().putParcelable("product", mProduct);
        AddProductWizardActivity.mProduct = mProduct;
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
        return true;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
        if (bitmap != null) {
            productImage.setImageBitmap(bitmap);


            File destination;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            destination = new File(OUTPUT_DIR,
                    "productImage" + "_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mProduct.setImageUrl(destination.getAbsolutePath());

        }
    }

    public void onPickImage(View view) {
        ImagePicker.pickImage(this, "Select your logo:");
    }
}
