package com.mobilesoft.bonways.uis;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.model.LatLng;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.backend.BackEndService;
import com.mobilesoft.bonways.core.managers.ProfileManager;
import com.mobilesoft.bonways.core.models.Comment;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.Reservation;
import com.mobilesoft.bonways.core.models.Trade;
import com.mobilesoft.bonways.uis.adapters.CommentAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
    private RelativeTimeTextView timePosted;
    Thread t;
    private LinearLayout containerComments;
    private DialogPlus dialog;
    private RecyclerView list;
    private TextView headerText;
    private ArrayList<Comment> arrayList;
    private CommentAdapter ca;
    private ProgressBar progressComment;
    private BackEndService backEndService;
    Trade tradeTmp = new Trade();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reserved =  findViewById(R.id.reserved);

        imageProduct =  findViewById(R.id.productImage);
        shopLogo = findViewById(R.id.shop_logo);
        iconLiked =  findViewById(R.id.icon_liked);
        titleProduct =  findViewById(R.id.productTitle);
        descriptionProduct =  findViewById(R.id.product_description);
        labelGoto =  findViewById(R.id.label_goto);

        normalPrice =  findViewById(R.id.normal_price);
        promoPrice =  findViewById(R.id.promo_price);
        percentageDiscount =  findViewById(R.id.percentage_discount);
        liked =  findViewById(R.id.item_liked);
        watched =  findViewById(R.id.item_watched);
        commented =  findViewById(R.id.item_comments);
        category =  findViewById(R.id.category);
        timePosted = findViewById(R.id.timeposted);
        timeOff =  findViewById(R.id.timeleft);
        productLeft =  findViewById(R.id.productleft);
        shopName =  findViewById(R.id.shop_name);
        shopDistance =  findViewById(R.id.shop_distance);
        tradePhone =  findViewById(R.id.shop_phone);
        currency =  findViewById(R.id.currency);
//        currency.setRotation(310);
        containerLiked =  findViewById(R.id.container_social_liked);
        containerComments =  findViewById(R.id.container_social_comments);
        go = findViewById(R.id.button_go);
        reserve = findViewById(R.id.button_reserve);


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

        if (mProduct.getImageUrl() != null && (mProduct.getImageUrl().contains("http") || mProduct.getImageUrl().contains("cdn")))
            Picasso.with(this).load(mProduct.getImageUrl()).placeholder(R.drawable.nopreview).into(imageProduct);
        else
            Picasso.with(this).load("file://" + mProduct.getImageUrl()).placeholder(R.drawable.nopreview).into(imageProduct);

//        for (Reservation reservation : ProfileManager.getCurrentUserProfile().getMyReservations()) {
//            if (reservation.getObject().getCode().equals(mProduct.getCode())) {
//                reserved.setVisibility(View.VISIBLE);
//            }
//        }
        ArrayList<Trade> trades = new ArrayList<>();
//        trades.addAll(DummyServer.getTrade());
        trades.addAll(ProfileManager.getCurrentUserProfile().getTrades());

        for (Trade t : trades) {
            if (mProduct.getTradeId().equals(t.getId())) {
                tradeTmp = t;
                break;
            }
        }

        Picasso.with(this).load(tradeTmp.getLogoUrl()).into(shopLogo);
        titleProduct.setText(mProduct.getName());
        descriptionProduct.setText(mProduct.getDescription());
        shopName.setText(tradeTmp.getName());
        if (mProduct.getCategory() != null)
            category.setText(mProduct.getCategory().getName());
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if (mProduct.getCreatedDate() != null)
//            timePosted.setText(format.format(mProduct.getCreatedDate()));
            timePosted.setReferenceTime(new Date().getTime());
        productLeft.setText("" + mProduct.getUnitQuantity());
        tradePhone.setText(tradeTmp.getPhone());

        if (MainActivity.mUserLocation != null) {
            Location tradeLoc = new Location(tradeTmp.getName());
            tradeLoc.setLatitude(tradeTmp.getLatitude());
            tradeLoc.setLongitude(tradeTmp.getLongitude());
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
            mProduct.getWatchers().add(ProfileManager.getCurrentUserProfile().getConsumer().getEmail());
        }
        watched.setText("" + mProduct.getWatchers().size());
        commented.setText("" + mProduct.getComments().size());

        normalPrice.setText(nf.format(mProduct.getPrice()) + "");
        normalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double promo = mProduct.getPrice() - (mProduct.getPrice() * mProduct.getDiscountPercentage() / 100);
        promoPrice.setText(nf.format(promo) + "");


        Calendar calendar = Calendar.getInstance(Locale.FRENCH);
        Log.d(TAG, "" + calendar.getTime());
        if (new Date(mProduct.getTimeStart()).after(calendar.getTime())) {
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


                                    long diff = mProduct.getTimeEnd() - new Date().getTime();
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
                    mProduct.getLikers().remove(ProfileManager.getCurrentUserProfile().getConsumer().getEmail());
                    iconLiked.setImageResource(R.drawable.ic_like);
                } else {
                    mProduct.setLiked(true);
                    mProduct.getLikers().add(ProfileManager.getCurrentUserProfile().getConsumer().getEmail());
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
                instance.showShop(new LatLng(tradeTmp.getLatitude(), tradeTmp.getLongitude()));
                finish();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.showShop(new LatLng(tradeTmp.getLatitude(), tradeTmp.getLongitude()));
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
                final TextView dialogTitle =  mView.findViewById(R.id.dialogTitle);
                final TextView dialogContent =  mView.findViewById(R.id.dialogContent);

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

            // todo :: Handle for likes, views and comments
            MainActivity.mProducts.remove(mClone);
            MainActivity.mProducts.add(mProduct);
//            }
//            else{
//                MainActivity.mProducts.remove(mProduct);
//                MainActivity.mProducts.add(mProduct);
//            }


        }
        if (t!=null && !t.isInterrupted())
            t.interrupt();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (t != null && t.isInterrupted()) {
            t.start();
        }
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
        headerText =  globalDialog.findViewById(R.id.headerText);
        Button sender = (Button) globalDialog.findViewById(R.id.sendButton);
        progressComment = (ProgressBar) globalDialog.findViewById(R.id.progressBarComment);

        list = (RecyclerView) globalDialog.findViewById(R.id.commentsRV);

        AdView mAdView = (AdView) globalDialog.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        ((LinearLayoutManager) lm).setStackFromEnd(true);
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
                    comment.setAuthorName(ProfileManager.getCurrentUserProfile().getConsumer().getDisplayName());
                    comment.setPictureUrl(ProfileManager.getCurrentUserProfile().getConsumer().getImageUrl());
                    comment.setAuthorId(ProfileManager.getCurrentUserProfile().getConsumer().getEmail());
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
        if (progressComment != null)
            progressComment.setVisibility(View.VISIBLE);

        /*Call<Product> callParish = backEndService.updateProductComment(comment);
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

                    if (progressComment != null)
                        progressComment.setVisibility(View.GONE);

                } else {
                    if (progressComment != null)
                        progressComment.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, response.message());

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, Log.getStackTraceString(t));
                if (progressComment != null)
                    progressComment.setVisibility(View.GONE);

            }
        });*/
    }

    public interface DisplayShop {
        void showShop(LatLng location);
    }
}
