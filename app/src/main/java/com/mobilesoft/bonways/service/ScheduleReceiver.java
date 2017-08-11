package com.mobilesoft.bonways.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Lyonnel Dzotang on 10/08/2017.
 */

public class ScheduleReceiver extends BroadcastReceiver {

        public static final int REQUEST_CODE = 12345;
        public static final String ACTION = "com.mobilesoft.bonways.service";

        // Triggered by the Alarm periodically (starts the service to run task)
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, PullService.class);
            context.startService(i);
        }
}
