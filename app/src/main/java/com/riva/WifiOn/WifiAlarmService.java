package com.riva.WifiOn;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Riva on 1/30/2016.
 */
public class WifiAlarmService extends Service {

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("WifiAlarmService", "WifiAlarmService STARTED");

        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, NotificationReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        return START_STICKY;
    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
