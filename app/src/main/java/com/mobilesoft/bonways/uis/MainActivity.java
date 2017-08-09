package com.mobilesoft.bonways.uis;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.BackEndService;
import com.mobilesoft.bonways.backend.DummyServer;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.core.models.Consumer;
import com.mobilesoft.bonways.uis.adapters.MainTabAdapter;
import com.mobilesoft.bonways.uis.adapters.SimpleAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, DetailsActivity.DisplayShop {

    private static final String ANONYMOUS = "Anonymous";
    private static final String TAG = "MainActivity";
    public static Product tmpCurrentProduct;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    public static Location mUserLocation;
    public static Set<Product> mProducts;
    public static Set<Category> mCategories = new TreeSet<>();
    public static Set<Trade> mTrade;

    FloatingActionButton addProduct;
    FloatingActionButton addShop;
    FloatingActionMenu mainFab;

    private GoogleApiClient mGoogleApiClient;

    public static DialogPlus dialog;

    ViewPager viewPager;

    protected OnClickListener dialogListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
//            if (view.getId() == R.id.dialog_close) {
//                dialog.dismiss();
//            }
//            if (view.getId() == R.id.dialog_downlaod_artifact) {
//                try {
//
//                    if (ContextCompat.checkSelfPermission(SuperActivity.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            != PackageManager.PERMISSION_GRANTED
//                            ) {
//
//                        Toast.makeText(SuperActivity.this, R.string.permission_alert, Toast.LENGTH_SHORT).show();
//
//                        ActivityCompat.requestPermissions(SuperActivity.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                EXTERNAL_STORAGE);
//
//                    } else {
//                        new DownloadArtifact(SuperActivity.this, "Artifact-" + Math.random()).execute(artifactUrl);
//                    }
//
//
//                } catch (ActivityNotFoundException anfe) {
//                    //display an error message
//                    Toast.makeText(SuperActivity.this, getResources().getString(R.string.errorMessage_capture_image), Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    };
    public static LatLng mShopLocation;
    private Marker marker;
    private BackEndService backEndService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsername = ANONYMOUS;

        DetailsActivity.instance = this;


        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }


        mProducts = new HashSet<>();
        mTrade = new HashSet<>();

        //Online Call
        /*backEndService = BackEndService.retrofit.create(BackEndService.class);

        Call<Product[]> call = backEndService.getProduct();
        call.enqueue(new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                if (response.body() != null) {
                    for (Product p : response.body()) {
                        mProducts.add(p);
                    }
                }
                Log.d(TAG, "onResponse: product:"+mProducts);
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                Log.d(TAG, "onFailure: An Error Occurred -\n"+ Log.getStackTraceString(t));
            }
        });*/
        mProducts.addAll(DummyServer.getAvailableProduct());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadData();

        View headerView = navigationView.getHeaderView(0);
        CircularImageView userPic = (CircularImageView) headerView.findViewById(R.id.imageView);

        Profile profile = ProfileManager.getCurrentUserProfile();
        try {
            Consumer currentConsumer = profile.getConsumer();
            if (currentConsumer != null && currentConsumer.getImageUrl() != null) {
                userPic.setVisibility(View.VISIBLE);
                Picasso.with(this).load(currentConsumer.getImageUrl()).into(userPic);
            }
        } catch (NullPointerException e) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            ProfileManager.delete();
            finish();
            return;
        }


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_map)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_hot)));
        tabLayout.addTab(tabLayout.newTab().setText(getText(R.string.tab_favorite)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final MainTabAdapter adapter = new MainTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mainFab = (FloatingActionMenu) findViewById(R.id.mainFab);
        addProduct = (FloatingActionButton) findViewById(R.id.fab_add_product);
        addShop = (FloatingActionButton) findViewById(R.id.fab_add_shop);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddProductWizardActivity.class));
            }
        });

        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddShopWizardActivity.class));
            }
        });


//        showCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Profile profile = ProfileManager.getCurrentUserProfile();
        /*if (profile != null && profile.getUser() != null) {
            if (profile.getUser().isTrader()) {
                navigationView.getMenu().findItem(R.id.nav_trader).setVisible(false);
                mainFab.setVisibility(View.VISIBLE);
//                navigationView.getMenu().findItem(R.id.nav_shop).setVisible(true);
//                navigationView.getMenu().findItem(R.id.nav_product).setVisible(true);
            } else {
                navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);

            }


        }*/

        if (viewPager != null && tmpCurrentProduct == null) {
            viewPager.setCurrentItem(1);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mUserLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        int i = 0;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (tmpCurrentProduct != null) {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("product", (Parcelable) tmpCurrentProduct);
                startActivity(intent);
                MainActivity.tmpCurrentProduct = null;

            } else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadData() {

        mCategories.addAll(DummyServer.getCategory());


        //Online Call
        /*Call<Category[]> call = backEndService.getCategories();
        call.enqueue(new Callback<Category[]>() {
            @Override
            public void onResponse(Call<Category[]> call, Response<Category[]> response) {
                if (response.body() != null) {
                    for (Category p : response.body()) {
                        mCategories.add(p);
                    }
                }
            }

            @Override
            public void onFailure(Call<Category[]> call, Throwable t) {
                Log.d(TAG, "onFailure: An Error Occurred -\n"+ Log.getStackTraceString(t));
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_category) {
            showCategory();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_reservation) {
            startActivity(new Intent(this, ReservationsActivity.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(this, AccountActivity.class));
        } /*else if (id == R.id.nav_trader) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(getResources().getString(R.string.trader_request_title));
            alertBuilder.setMessage(Html.fromHtml(getResources().getString(R.string.trader_request_explanation)));
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, AddShopWizardActivity.class);
                    startActivityForResult(intent, 1);
                }
            });
            alertBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    //// nothing to do
                }
            });

            final AlertDialog alert = alertBuilder.create();

            alert.show();
            alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

        } */else if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name));
            startActivity(Intent.createChooser(intent, "Share with"));
        } else if (id == R.id.nav_logout) {
            mFirebaseAuth.signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null)
            for (String key : data.getExtras().keySet())
                Toast.makeText(this, key + " : " + data.getExtras().get(key).toString(), Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
// An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    void showCategory() {

        List<Category> categories = new ArrayList<Category>();
        categories.addAll(mCategories);

        SimpleAdapter sa = new SimpleAdapter(this, categories);

        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new GridHolder(2))
                .setOnClickListener(dialogListener)
                .setHeader(R.layout.dialog_header)
                .setAdapter(sa)
                .setCancelable(true)
                .setExpanded(true)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {

                    }
                })
                .create();
        dialog.show();
    }


    @Override
    public void showShop(LatLng location) {
        if (viewPager != null) {
            viewPager.setCurrentItem(0);
            mShopLocation = location;

        }

    }


}
