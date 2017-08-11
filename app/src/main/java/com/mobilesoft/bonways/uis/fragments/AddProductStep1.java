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
import android.widget.Toast;

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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ganfra.materialspinner.MaterialSpinner;


public class AddProductStep1 extends AbstractStep implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddProductStep1";
    protected static File OUTPUT_DIR = BonWaysApplication.getInstance().getDir(BuildConfig.APPLICATION_ID + BuildConfig.VERSION_CODE, Context.MODE_PRIVATE);
    EditText productName;
    EditText productNormalPrice;
    EditText productPromoPrice;
    EditText productQuantity;
    EditText productTimeOff;
    EditText productTimeStart;
    MaterialSpinner productCategory;
    EditText productDescription;
    ImageView productImage;
    SearchableSpinner shopItems;

    long dateStart;
    long dateEnd;

    Product mProduct;
    SimpleDateFormat dateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_product_1, container, false);
        productName = v.findViewById(R.id.productName);
        productNormalPrice = v.findViewById(R.id.productNormalPrice);
        productPromoPrice = v.findViewById(R.id.productPromoPrice);
        productQuantity = v.findViewById(R.id.productQuantity);
        productTimeOff = v.findViewById(R.id.productTimeOff);
        productTimeStart = v.findViewById(R.id.productTimeStart);
        productCategory = v.findViewById(R.id.productCategory);
        productDescription = v.findViewById(R.id.productDescription);
        productImage = v.findViewById(R.id.productImage);

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

        shopItems = v.findViewById(R.id.available_shops);

        shopItems.setTitle("Select Shop");
        shopItems.setPositiveButton("OK");


        final List<Trade> shops = new ArrayList<>();
        shops.addAll(ProfileManager.getCurrentUserProfile().getTrades());
        ArrayList<String> shopsList = new ArrayList<>();
        for (Trade trade : shops) {
            shopsList.add(trade.getName());

        }

        final Calendar now = Calendar.getInstance(Locale.getDefault());

        Date date = new Date();

        //// TODO: 11/08/2017 Manage date warning
        Log.d(TAG, "onCreateView: " + Locale.getDefault().getISO3Language());
        String lan = Locale.getDefault().toString();
        Log.d(TAG, "onCreateView: Lan : " + lan);
        if (lan.contains("fr"))
            dateFormat = new SimpleDateFormat("dd/MM/yyyy dd HH:mm");
        else
            dateFormat = new SimpleDateFormat("yyyy/dd/MM hh:mm a");


        dateStart = now.getTimeInMillis();
        productTimeStart.setText(dateFormat.format(date));

        productTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddProductStep1.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setTitle(getResources().getString(R.string.wzp_product_start_on));
                dpd.show(getActivity().getFragmentManager(), "START_DATE");
            }
        });

        now.add(Calendar.DAY_OF_MONTH, 7);

        dateEnd = now.getTimeInMillis();

        productTimeOff.setText(dateFormat.format(now.getTime()));

        productTimeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddProductStep1.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle(getResources().getString(R.string.wzp_product_end_on));
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.show(getActivity().getFragmentManager(), "END_DATE");
            }
        });


        ShopSpinnerAdapter shopSpinnerAdapter = new ShopSpinnerAdapter(getActivity(), R.layout.spinner_shop_layout, R.id.text_title, shopsList);

        shopItems.setAdapter(shopSpinnerAdapter);

        shopItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView =  view.findViewById(R.id.text_subtitle);
                textView.setText(shops.get(position).getAddress());

                if (mProduct != null) {
                    mProduct.setTradeId(shops.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (savedInstanceState != null) {

        }

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

    String incomingDate;
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(mStepper, view.getTag() + " : " + year + "/" + dayOfMonth + "/" + (monthOfYear + 1), Toast.LENGTH_SHORT).show();

        String month = (monthOfYear+1)<10 ? "0"+(monthOfYear+1) : ""+(monthOfYear+1);
        incomingDate = year + "/" + month + "/" + dayOfMonth;

        long tmp;

        switch (view.getTag()) {
            case "START_DATE":
                tmp  = dateStart;
                try {
                    Date date = format.parse(incomingDate);
                    Log.d(TAG, "onDateSet: Date: " + date);
                    dateStart = date.getTime();
                    Log.d(TAG, "onDateSet: start: " + dateStart);

                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(AddProductStep1.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            true
                    );
                    tpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                    tpd.show(getActivity().getFragmentManager(), "START_TIME");

                } catch (ParseException e) {
                    dateStart = tmp;
                    e.printStackTrace();
                }
                break;
            case "END_DATE":
                tmp  = dateEnd;

                try {
                    Date date = format.parse(incomingDate);
                    Log.d(TAG, "onDateSet: Date: " + date);
                    dateEnd = date.getTime();
                    Log.d(TAG, "onDateSet: start: " + dateEnd);

                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(AddProductStep1.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            true
                    );
                    tpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                    tpd.show(getActivity().getFragmentManager(), "END_TIME");

                } catch (ParseException e) {
                    dateEnd = tmp;
                    e.printStackTrace();
                }
                break;
        }

    }

    SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Toast.makeText(mStepper, "" + hourOfDay + "h " + minute + "m" + second, Toast.LENGTH_SHORT).show();

        if (!incomingDate.isEmpty()) {
            incomingDate += " "+hourOfDay+":"+minute;
            Log.d(TAG, "onTimeSet: incoming: " + incomingDate);
        } else
            return;
        long tmp;
        switch (view.getTag()) {
            case "START_TIME":
                tmp = dateStart;
                try {
                    Date date = format2.parse(incomingDate);
                    Log.d(TAG, "onTimeSet: Date: " + date);
                    dateStart = date.getTime();
                    Log.d(TAG, "onTimeSet: start: " + dateStart);

                } catch (ParseException e) {
                    dateStart = tmp;
                    Log.getStackTraceString(e);
                }finally {
                    // TODO: 11/08/2017 remove log
                    Date date = new Date(dateStart);
                    Log.d(TAG, "onTimeSet: Date Fin: " +date );
                    mProduct.setTimeStart(dateStart);
                    productTimeStart.setText(dateFormat.format(date));
                }

                break;
            case "END_TIME":
                 tmp = dateEnd;
                try {
                    Date date = format2.parse(incomingDate);
                    Log.d(TAG, "onTimeSet: Date: " + date);
                    dateEnd = date.getTime();
                    Log.d(TAG, "onTimeSet: start: " + dateEnd);

                } catch (ParseException e) {
                    dateEnd = tmp;
                    Log.getStackTraceString(e);
                }finally {
                    // TODO: 11/08/2017 remove log
                    Date date = new Date(dateEnd);
                    Log.d(TAG, "onTimeSet: Date Fin: " +date );
                    mProduct.setTimeEnd(dateEnd);
                    productTimeOff.setText(dateFormat.format(date));
                }
                break;
        }

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
        mProduct.setTimeEnd(dateEnd);
        mProduct.setTimeStart(dateStart);

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
                if (mProduct == null)
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
