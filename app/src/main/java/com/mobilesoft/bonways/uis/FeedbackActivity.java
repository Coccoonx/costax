package com.mobilesoft.bonways.uis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesoft.bonways.R;
import com.mobilesoft.bonways.core.models.Feedback;

public class FeedbackActivity extends AppCompatActivity {

    private static final String TAG = "FeedbackActivity";
    Button feedbackSend;
    EditText feedbackMessage;
    EditText feedbackTitle;
    String message;
    String title;
    Feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        feedbackTitle = (EditText) findViewById(R.id.feedbackTitle);
        feedbackMessage = (EditText) findViewById(R.id.feedbackMessage);
        feedbackSend = (Button) findViewById(R.id.feedbackSend);

        feedbackMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendFeedback();
                    handled = true;
                }
                return handled;
            }
        });



        feedbackSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }


        });


    }

    private void sendFeedback() {
        message = feedbackMessage.getText().toString().trim();
        title = feedbackTitle.getText().toString().trim();

        if ((!message.equals("")) && (!title.equals(""))) {
            feedback = new Feedback();
            feedback.setDescription(message);
            feedback.setTitle(title);
            Log.d(TAG, "Feedback " + feedback);
            /*Call<Feedback> feedbackCall = service.sendFeedback(Credentials.authorization, feedback);
            feedbackCall.enqueue(new Callback<Feedback>() {
                @Override
                public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                    if(response.body()!=null) {
                        Toast.makeText(FeedbackActivity.this, getResources().getString(R.string.feedback_sent), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else{
                        APIError error = ErrorHandler.parseError(response);
                        LocalizedException exception = new LocalizedException(error.getMessage());
                        Toast.makeText(FeedbackActivity.this, exception.getMessage(FeedbackActivity.this), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Feedback> call, Throwable t) {
                    if(t instanceof IOException || t instanceof UnknownHostException)
                        Toast.makeText(FeedbackActivity.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
                    else {
                        LocalizedException exception = new LocalizedException(t.getMessage());
                        Toast.makeText(FeedbackActivity.this, exception.getMessage(FeedbackActivity.this), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/



        } else
            Toast.makeText(FeedbackActivity.this, getResources().getString(R.string.com_facebook_internet_permission_error_title), Toast.LENGTH_SHORT).show();
    }

}
