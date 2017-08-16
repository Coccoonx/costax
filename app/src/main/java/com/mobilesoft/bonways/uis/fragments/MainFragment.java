package com.mobilesoft.bonways.uis.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.uis.MainActivity;
import com.mobilesoft.bonways.uis.adapters.MainItemAdapter;
import com.mobilesoft.bonways.uis.adapters.OnLoadMoreListener;
import com.mobilesoft.bonways.uis.adapters.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lyonnel Dzotang on 24/01/2017.
 */

public class MainFragment extends Fragment implements SimpleAdapter.FilterByCategory, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private AdView mAdView;
    MainItemAdapter mi;
    RecyclerView recyclerView;
    public static SimpleAdapter.FilterByCategory instance;
    private SliderLayout mSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

//        mAdView = (AdView) v.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        instance = this;

        mSlider = v.findViewById(R.id.slider);

        buildSlider();

        recyclerView = v.findViewById(R.id.recyclerMain);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        return v;
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        final ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(MainActivity.mProducts);
        dataSet.addAll(ProfileManager.getCurrentUserProfile().getMyProducts());


        mi = new MainItemAdapter(getActivity(), recyclerView, dataSet);
        recyclerView.setAdapter(mi);

        mi.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (dataSet.size() <= 20) {
                    dataSet.add(null);
                    mi.notifyItemInserted(dataSet.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dataSet.remove(dataSet.size() - 1);

                            mi.notifyItemRemoved(dataSet.size());

                            dataSet.addAll(ProfileManager.getCurrentUserProfile().getProducts());
                            mi.notifyDataSetChanged();
                            mi.setLoaded();
                        }
                    }, 5000);
                } else {
//                    Toast.makeText(getActivity(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onRefresh() {
//        reLoadData();
//    }

    void buildSlider() {

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("50% discount ...", "http://images.all-free-download.com/images/graphicthumb/discount_banner_vector_graphic_557134.jpg");
        url_maps.put("Surely saving.", "http://www.bienbacsecurity.com.vn/upload_images/sales-off-2016.jpg");
        url_maps.put("Honor to Women!", "http://images.cdn2.stockunlimited.net/preview1300/women-s-day-discount-banner_1989644.jpg");
        url_maps.put("Long Live Discount!", "http://blogs.biztalk360.com/wp-content/uploads/2015/06/Blog-banner.jpg");


        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getDescription(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void process(Category category) {
        Set<Product> filteredProduct = new HashSet<>();

        if (category.getName().equals("Tout")) {
            filteredProduct.addAll(MainActivity.mProducts);
        } else {
            for (Product product : MainActivity.mProducts) {
                if (product.getCategory().equals(category)) {
                    filteredProduct.add(product);
                }

            }
            for (Product product : ProfileManager.getCurrentUserProfile().getMyProducts()) {
                if (product.getCategory().equals(category)) {
                    filteredProduct.add(product);
                }

            }
        }
//                if (!filteredProduct.isEmpty()) {
        ArrayList<Product> dataSet = new ArrayList<>();
        dataSet.addAll(filteredProduct);

        mi.notifyDataSetChanged();

//        mi = new MainItemAdapter(getActivity(), dataSet);
//        recyclerView.setAdapter(mi);
//                }
    }
}
