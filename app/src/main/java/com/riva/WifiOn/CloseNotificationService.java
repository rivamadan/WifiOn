package com.riva.WifiOn;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Riva on 1/30/2016.
 */
public class CloseNotificationService extends IntentService {

    public CloseNotificationService() {
        super("CloseNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        Log.i("CloseNotification", "CANCEL notification");
    }

}
