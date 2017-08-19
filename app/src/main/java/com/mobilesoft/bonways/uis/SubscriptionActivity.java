package com.mobilesoft.bonways.uis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.uis.adapters.BundleItemAdapter;

public class SubscriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BundleItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerBundle);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        adapter = new BundleItemAdapter(this, DummyServer.buildBundles(this));

        recyclerView.setAdapter(adapter);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
