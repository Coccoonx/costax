package com.mobilesoft.bonways.uis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.ProgressStepper;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.fragments.AddShopStep1;
import com.mobilesoft.bonways.uis.fragments.AddShopStep2;

public class AddShopWizardActivity extends ProgressStepper {

    private static final String TAG = "AddShopWizardActivity";
    private int i = 1;
    public static Trade mTrade;

    @Override
    public void onComplete() {

        super.onComplete();
        Log.d(TAG, ""+mTrade);
        Profile profile = ProfileManager.getCurrentUserProfile();
        if (!profile.getConsumer().isTrader()) {
            profile.getConsumer().setTrader(true);
        }
        profile.getTrades().add(mTrade);
        new ProfileManager.SaveProfile().execute(profile);
        Intent intent = new Intent(AddShopWizardActivity.this, ShopActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mTrade = new Trade();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        setErrorTimeout(1500);
        setTitle(getResources().getString(R.string.wizard_add_shop_title));
        setStateAdapter();

        addStep(createFragment(new AddShopStep1()));
        addStep(createFragment(new AddShopStep2()));


        super.onCreate(savedInstanceState);
    }

    private AbstractStep createFragment(AbstractStep fragment) {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }

}
