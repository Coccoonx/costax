package com.mobilesoft.bonways.uis;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.ProgressStepper;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.fragments.AddProductStep1;

public class AddProductWizardActivity extends ProgressStepper {

    private static final String TAG = "AddProductWizard";
    private int i = 1;
    public static Product mProduct;

    @Override
    public void onComplete() {


        super.onComplete();
        Profile profile = ProfileManager.getCurrentUserProfile();
        for (Trade trade : profile.getTrades()) {
            if (trade.getId().equals(mProduct.getTradeId())) {
                trade.getProductslist().add(mProduct);
            }
        }
        profile.getMyProducts().add(mProduct);
        new ProfileManager.SaveProfile().execute(profile);
        Log.d(TAG, ""+mProduct);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mProduct = new Product();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        setErrorTimeout(1500);
        setTitle(getResources().getString(R.string.wizard_add_product_title));
        setStateAdapter();

        addStep(createFragment(new AddProductStep1()));
//        addStep(createFragment(new AddProductStep2()));


        super.onCreate(savedInstanceState);
    }

    private AbstractStep createFragment(AbstractStep fragment) {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }

}
