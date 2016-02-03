package com.riva.WifiOn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Riva on 1/30/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NotificationReceiver", "Notification STARTED");

        Intent wifiOnIntent = new Intent(context, WifiStart.class);
        PendingIntent pWifiOnIntent = PendingIntent.getService(context, 0, wifiOnIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent dismissIntent = new Intent(context, MainActivity.class);
        dismissIntent.putExtra("notification", true);
        PendingIntent pDismissIntent = PendingIntent.getActivity(context, 0, dismissIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent closeIntent = new Intent(context, CloseNotificationService.class);
        PendingIntent pCloseIntent = PendingIntent.getService(context, 0, closeIntent, PendingIntent.FLAG_CANCEL_CURRENT );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat_action_report_problem)
                        .setContentTitle(context.getString(R.string.notification_title))
                        .setContentText(context.getString(R.string.notification_message))
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_message)))
                        .addAction(R.drawable.ic_stat_ic_wifi, "ON", pWifiOnIntent)
                        .addAction(R.drawable.ic_stat_action_alarm, "LATER", pCloseIntent)
                        .addAction(R.drawable.ic_stat_ic_clear_black, "STOP", pDismissIntent);

        Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, note);

    }

}
