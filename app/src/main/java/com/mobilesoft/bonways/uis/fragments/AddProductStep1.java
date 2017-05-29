package com.mobilesoft.bonways.uis.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.mobilesoft.bonways.BonWaysApplication;
import com.mobilesoft.bonways.BuildConfig;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.AddProductWizardActivity;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.adapters.ShopSpinnerAdapter;
import com.mvc.imagepicker.ImagePicker;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class AddProductStep1 extends AbstractStep {

    private static final String TAG = "AddProductStep1";
    protected static File OUTPUT_DIR = BonWaysApplication.getInstance().getDir(BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE, Context.MODE_PRIVATE);
    EditText productName;
    EditText productNormalPrice;
    EditText productPromoPrice;
    EditText productQuantity;
    EditText productTimeOff;
    MaterialSpinner productCategory;
    EditText productDescription;
    ImageView productImage;
    SearchableSpinner shopItems;

    Product mProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_product_1, container, false);
        productName = (EditText) v.findViewById(R.id.productName);
        productNormalPrice = (EditText) v.findViewById(R.id.productNormalPrice);
        productPromoPrice = (EditText) v.findViewById(R.id.productPromoPrice);
        productQuantity = (EditText) v.findViewById(R.id.productQuantity);
        productTimeOff = (EditText) v.findViewById(R.id.productTimeOff);
        productCategory = (MaterialSpinner) v.findViewById(R.id.productCategory);
        productDescription = (EditText) v.findViewById(R.id.productDescription);
        productImage = (ImageView) v.findViewById(R.id.productImage);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickImage(productImage);
            }
        });


        ArrayList<String> items = new ArrayList<>();
        for (Category category : MainActivity.mCategories) {
            if (category.getName().equalsIgnoreCase("Tout"))
                continue;
            items.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);


        ImagePicker.setMinQuality(250, 200);

        shopItems = (SearchableSpinner) v.findViewById(R.id.available_shops);

        shopItems.setTitle("Select Shop");
        shopItems.setPositiveButton("OK");


        final List<Trade> shops = ProfileManager.getCurrentUserProfile().getTrades();
        ArrayList<String> shopsList = new ArrayList<>();
        for (Trade trade : shops) {
            shopsList.add(trade.getName());

        }


        ShopSpinnerAdapter shopSpinnerAdapter = new ShopSpinnerAdapter(getActivity(), R.layout.spinner_shop_layout, R.id.text_title, shopsList);

        shopItems.setAdapter(shopSpinnerAdapter);

        shopItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = ((TextView) view.findViewById(R.id.text_subtitle));
                textView.setText(shops.get(position).getAddress());

                if (mProduct != null) {
                    mProduct.setTrade(shops.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        mProduct.setName(productName.getText().toString());
        mProduct.setDescription(productDescription.getText().toString());
        double price = Double.valueOf(productNormalPrice.getText().toString());
        double promoPrice = Double.valueOf(productPromoPrice.getText().toString());
        mProduct.setPrice(price);
        mProduct.setUnitQuantity(Long.valueOf(productQuantity.getText().toString()));

        double discountper = ((price - promoPrice) / price) * 100;
        mProduct.setDiscountPercentage(discountper);
        DateFormat dateFormat = DateFormat.getDateInstance();
        try {
            Date date = dateFormat.parse(productTimeOff.getText().toString());
            Log.d(TAG, "onNext: Date Off: " + date);
            mProduct.setDateTimeOff(date);

        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            mProduct.setDateTimeOff(calendar.getTime());
        }

        String cat = (String) productCategory.getSelectedItem();
        for (Category category : MainActivity.mCategories) {
            if (cat.equals(category.getName())) {
                if (mProduct != null) {
                    mProduct.setCategory(category);
                    break;
                }
            }
        }

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
                if (mProduct==null)
                    mProduct = new Product();
                mProduct.setImageUrl(destination.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void onPickImage(View view) {
        ImagePicker.pickImage(this, "Select your logo:");
    }
}
