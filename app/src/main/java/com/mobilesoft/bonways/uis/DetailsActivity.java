package com.mobilesoft.bonways.uis;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.model.LatLng;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.BackEndService;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Comment;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Profile;
import com.mobilesoft.bonways.core.models.Reservation;
import com.mobilesoft.bonways.core.models.User;
import com.mobilesoft.bonways.uis.adapters.CommentAdapter;
import com.mobilesoft.bonways.uis.adapters.SimpleAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    ImageView imageProduct;
    ImageView shopLogo;
    ImageView iconLiked;
    ImageView reserved;
    TextView titleProduct;
    TextView descriptionProduct;
    LinearLayout containerLiked;
    TextView normalPrice;
    TextView promoPrice;
    TextView shopName;
    TextView tradePhone;
    TextView shopDistance;
    TextView percentageDiscount;
    TextView timeOff;
    TextView liked;
    TextView productLeft;
    TextView watched;
    TextView commented;
    TextView category;
    TextView currency;
    TextView labelGoto;
    Product mProduct;
    Product mClone;
    CircularImageView go;
    Button reserve;
    public static DisplayShop instance;
    private TextView timePosted;
    Thread t;
    private LinearLayout containerComments;
    private DialogPlus dialog;
    private RecyclerView list;
    private TextView headerText;
    private ArrayList<Comment> arrayList;
    private CommentAdapter ca;
    private ProgressBar progressComment;
    private BackEndService backEndService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reserved = (ImageView) findViewById(R.id.reserved);

        imageProduct = (ImageView) findViewById(R.id.productImage);
        shopLogo = (ImageView) findViewById(R.id.shop_logo);
        iconLiked = (ImageView) findViewById(R.id.icon_liked);
        titleProduct = (TextView) findViewById(R.id.productTitle);
        descriptionProduct = (TextView) findViewById(R.id.product_description);
        labelGoto = (TextView) findViewById(R.id.label_goto);

        normalPrice = (TextView) findViewById(R.id.normal_price);
        promoPrice = (TextView) findViewById(R.id.promo_price);
        percentageDiscount = (TextView) findViewById(R.id.percentage_discount);
        liked = (TextView) findViewById(R.id.item_liked);
        watched = (TextView) findViewById(R.id.item_watched);
        commented = (TextView) findViewById(R.id.item_comments);
        category = (TextView) findViewById(R.id.category);
        timePosted = (TextView) findViewById(R.id.timeposted);
        timeOff = (TextView) findViewById(R.id.timeleft);
        productLeft = (TextView) findViewById(R.id.productleft);
        shopName = (TextView) findViewById(R.id.shop_name);
        shopDistance = (TextView) findViewById(R.id.shop_distance);
        tradePhone = (TextView) findViewById(R.id.shop_phone);
        currency = (TextView) findViewById(R.id.currency);
//        currency.setRotation(310);
        containerLiked = (LinearLayout) findViewById(R.id.container_social_liked);
        containerComments = (LinearLayout) findViewById(R.id.container_social_comments);
        go = (CircularImageView) findViewById(R.id.button_go);
        reserve = (Button) findViewById(R.id.button_reserve);


        Bundle b = getIntent().getExtras();
        if (b != null) {


            mProduct = b.getParcelable("product");
            if (mProduct != null) {
                backEndService = BackEndService.retrofit.create(BackEndService.class);

                refreshUI(mProduct);
            }
        }

    }

    private void refreshUI(final Product mProduct) {
        mClone = mProduct.clone();
        getSupportActionBar().setTitle(mProduct.getName());

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);

        if (mProduct.getImageUrl().contains("http") || mProduct.getImageUrl().contains("cdn"))
            Picasso.with(this).load(mProduct.getImageUrl()).into(imageProduct);
        else
            Picasso.with(this).load("file://" + mProduct.getImageUrl()).into(imageProduct);

        for (Reservation reservation : ProfileManager.getCurrentUserProfile().getMyReservations()) {
            if (reservation.getObject().getCode().equals(mProduct.getCode())) {
                reserved.setVisibility(View.VISIBLE);
            }
        }

        Picasso.with(this).load(mProduct.getTrade().getLogoUrl()).into(shopLogo);
        titleProduct.setText(mProduct.getName());
        descriptionProduct.setText(mProduct.getDescription());
        shopName.setText(mProduct.getTrade().getName());
        category.setText(mProduct.getCategory().getName());
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        timePosted.setText(format.format(mProduct.getCreatedDate()));
        productLeft.setText("" + mProduct.getUnitQuantity());
        tradePhone.setText(mProduct.getTrade().getPhone());

        if (MainActivity.mUserLocation != null) {
            Location tradeLoc = new Location(mProduct.getTrade().getName());
            tradeLoc.setLatitude(mProduct.getTrade().getLatitude());
            tradeLoc.setLongitude(mProduct.getTrade().getLongitude());
            int distance = (int) MainActivity.mUserLocation.distanceTo(tradeLoc);
            if (distance < 1000) {
                shopDistance.setText(nf.format(distance) + " m");
            } else {
                double d = distance / 1000.0;
                shopDistance.setText(nf.format(d) + " km");
            }
        }

        percentageDiscount.setText("-" + nf.format(mProduct.getDiscountPercentage()) + "%");
        liked.setText("" + mProduct.getLikers().size());

        if (!mProduct.isWatched()) {
            mProduct.setWatched(true);
            mProduct.getWatchers().add(ProfileManager.getCurrentUserProfile().getUser().getEmail());
        }
        watched.setText("" + mProduct.getWatchers().size());
        commented.setText("" + mProduct.getComments().size());

        normalPrice.setText(nf.format(mProduct.getPrice()) + "");
        normalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double promo = mProduct.getPrice() - (mProduct.getPrice() * mProduct.getDiscountPercentage() / 100);
        promoPrice.setText(nf.format(promo) + "");


        Calendar calendar = Calendar.getInstance(Locale.FRENCH);
        Log.d(TAG, "" + calendar.getTime());
        if (mProduct.getDateTimeOff().after(calendar.getTime())) {
            t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // update TextView here!


                                    long diff = mProduct.getDateTimeOff().getTime() - new Date().getTime();
                                    Log.d("TIMER", "" + diff);
                                    int timeInSeconds = Math.abs((int) diff / 1000);
                                    int hours, minutes, seconds;
                                    hours = timeInSeconds / 3600;
                                    if (hours > 24) {
                                        int days = hours / 24;
                                        timeOff.setText(days + " " + getResources().getString(R.string.label_timeleft_value));
                                        if (!t.isInterrupted())
                                            t.interrupt();

                                    } else {
                                        timeInSeconds = timeInSeconds - (hours * 3600);
//                                                if (timeInSeconds < 0) {
//
//                                                    timeInSeconds = (hours * 3600) - timeInSeconds;
//                                                }
                                        Log.d(TAG, "timeInSeconds " + timeInSeconds);

                                        minutes = timeInSeconds / 60;
                                        timeInSeconds = timeInSeconds - (minutes * 60);
                                        seconds = timeInSeconds;

                                        String diffTime = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                                        Log.d(TAG, "" + diffTime);

                                        timeOff.setText(diffTime);
                                    }


                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };

            t.start();


        } else {
            timeOff.setText("OFF");
            timeOff.setTextColor(getResources().getColor(R.color.colorPrimary));

        }

        if (mProduct.isLiked()) {
            iconLiked.setImageResource(R.drawable.ic_like_filled);
        } else {
            iconLiked.setImageResource(R.drawable.ic_like);
        }


        containerLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProduct.isLiked()) {
                    mProduct.setLiked(false);
                    mProduct.getLikers().remove(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                    iconLiked.setImageResource(R.drawable.ic_like);
                } else {
                    mProduct.setLiked(true);
                    mProduct.getLikers().add(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                    iconLiked.setImageResource(R.drawable.ic_like_filled);
                }
                liked.setText("" + mProduct.getLikers().size());
                containerLiked.invalidate();
            }
        });

        containerComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProduct != null)
                    showComments();
            }
        });

        labelGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.showShop(new LatLng(mProduct.getTrade().getLatitude(), mProduct.getTrade().getLongitude()));
                finish();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.showShop(new LatLng(mProduct.getTrade().getLatitude(), mProduct.getTrade().getLongitude()));
                finish();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(DetailsActivity.this);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_reservation, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(DetailsActivity.this);
                alertDialogBuilderUserInput.setView(mView);

                final EditText quantityET = (EditText) mView.findViewById(R.id.userInputDialog);
                final TextView dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);
                final TextView dialogContent = (TextView) mView.findViewById(R.id.dialogContent);

                dialogTitle.setText(R.string.dialog_reservation_title);
                dialogContent.setText(R.string.dialog_reservation_content);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                Reservation reservation = new Reservation();
                                reservation.setObject(mProduct);
                                reservation.setQuantity(Integer.parseInt(quantityET.getText().toString().trim()));
                                ProfileManager.getCurrentUserProfile().getMyReservations().add(reservation);
                                new ProfileManager.SaveProfile().execute(ProfileManager.getCurrentUserProfile());
                                reserved.setVisibility(View.VISIBLE);
                                dialogBox.cancel();

                                new SweetAlertDialog(DetailsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText(getResources().getString(R.string.dialog_reservation_confirmed))
                                        .show();
                            }
                        })

                        .setNegativeButton(android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

                alertDialogAndroid.getButton(alertDialogAndroid.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                alertDialogAndroid.getButton(alertDialogAndroid.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));


            }
        });
    }

    @Override
    protected void onPause() {
        if (mProduct != null && mClone != null) {
//            if (alreadyRemoved){

            MainActivity.mProducts.remove(mClone);
            MainActivity.mProducts.add(mProduct);
//            }
//            else{
//                MainActivity.mProducts.remove(mProduct);
//                MainActivity.mProducts.add(mProduct);
//            }


        }
        if (!t.isInterrupted())
            t.interrupt();
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        } else
            super.onBackPressed();
    }

    void showComments() {

        View globalDialog = getLayoutInflater().inflate(R.layout.dialog_comments, null);

        final EditText composer = (EditText) globalDialog.findViewById(R.id.messageEditText);
        headerText = (TextView) globalDialog.findViewById(R.id.headerText);
        Button sender = (Button) globalDialog.findViewById(R.id.sendButton);
        progressComment = (ProgressBar) globalDialog.findViewById(R.id.progressBarComment);

        list = (RecyclerView) globalDialog.findViewById(R.id.commentsRV);

        AdView mAdView = (AdView) globalDialog.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        ((LinearLayoutManager)lm).setStackFromEnd(true);
        list.setLayoutManager(lm);
        list.setHasFixedSize(true);

        arrayList = new ArrayList<>(mProduct.getComments());
        ca = new CommentAdapter(this, arrayList);

        headerText.setText(getResources().getString(R.string.dialog_comments_title) + " (" + mProduct.getComments().size() + ")");

        list.setAdapter(ca);


        dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(globalDialog))
                .setOnClickListener(dialogListener)
//                .setHeader(R.layout.dialog_comments_header)
//                .setAdapter(sa)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
//                .setExpanded(true)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {

                    }
                })
                .create();
        dialog.show();
    }

    protected OnClickListener dialogListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
//            if (view.getId() == R.id.dialog_close) {
//                dialog.dismiss();
//            }
            if (view.getId() == R.id.sendButton) {
                EditText composer = (EditText) dialog.getHolderView().findViewById(R.id.messageEditText);
                String message = composer.getText().toString().trim();
                if (!message.isEmpty()) {


                    Comment comment = new Comment();
                    comment.setAuthorName(ProfileManager.getCurrentUserProfile().getUser().getDisplayName());
                    comment.setPictureUrl(ProfileManager.getCurrentUserProfile().getUser().getImageUrl());
                    comment.setAuthorId(ProfileManager.getCurrentUserProfile().getUser().getEmail());
                    comment.setContent(message);
                    comment.setDate(new Date().getTime());
                    comment.setProductId(mProduct.getId());


                    pushComment(comment);
                    composer.setText("");


                    if (list != null)
                        list.setAdapter(ca);
                }
            }
        }

        ;
    };

    public void pushComment(Comment comment) {
        if (progressComment!=null)
            progressComment.setVisibility(View.VISIBLE);
        Call<Product> callParish = backEndService.updateProductComment(comment);

        callParish.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.body() != null) {
                    mProduct = response.body();
                    Log.d(TAG, "product = " + mProduct);
                    refreshUI(mProduct);

                    arrayList = new ArrayList<>(mProduct.getComments());
//                    ca.notifyDataSetChanged();
                    ca = new CommentAdapter(DetailsActivity.this, arrayList);
                    list.setAdapter(ca);
                    headerText.setText(getResources().getString(R.string.dialog_comments_title) + " (" + arrayList.size() + ")");

                    if (progressComment!=null)
                        progressComment.setVisibility(View.GONE);

                } else {
                    if (progressComment!=null)
                        progressComment.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, response.message());

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, Log.getStackTraceString(t));
                if (progressComment!=null)
                    progressComment.setVisibility(View.GONE);

            }
        });
    }

    private class PushComment extends AsyncTask<Comment, Void, Comment> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Comment comment) {
            super.onPostExecute(comment);
        }

        @Override
        protected Comment doInBackground(Comment... comments) {

            return null;
        }
    }


    public interface DisplayShop {
        void showShop(LatLng location);
    }
}
