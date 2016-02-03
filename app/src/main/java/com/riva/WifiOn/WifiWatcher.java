package com.riva.WifiOn;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Riva on 1/30/2016.
 */
public class WifiWatcher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Check that the network connection is available. If yes, start your service. If not, stop your service.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() != ConnectivityManager.TYPE_WIFI && ((Global) context.getApplicationContext()).isChecked()) {
                //start service
                Log.i("WifiWatcher", "WifiAlarmService STARTED from WifiWatcher");
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(2);

                Intent serviceIntent = new Intent(context, WifiAlarmService.class);
                context.startService(serviceIntent);

            } else if (info.getType() != ConnectivityManager.TYPE_WIFI && !((Global) context.getApplicationContext()).isChecked()) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(context.getString(R.string.reminder_off_title));

                Notification note = mBuilder.build();
                note.flags |= Notification.FLAG_ONGOING_EVENT;
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(2, note);

                Intent serviceIntent = new Intent(context, WifiAlarmService.class);
                context.stopService(serviceIntent);

                Intent alarmIntent = new Intent(context, NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            } else {
                //stop service
                Log.i("WifiWatcher", "WifiAlarmService STOPPED from WifiWatcher");
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(2);

                Intent serviceIntent = new Intent(context, WifiAlarmService.class);
                context.stopService(serviceIntent);

                Intent alarmIntent = new Intent(context, NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }
        }
    }
}
