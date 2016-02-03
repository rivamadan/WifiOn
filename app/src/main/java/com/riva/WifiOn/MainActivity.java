package com.riva.WifiOn;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    static final String REMINDER_STATE = "reminderState";
    public static final String WATCHER_BROADCAST = "com.riva.WifiOn.android.action.broadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.wifiToggle);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            boolean reminderState = savedInstanceState.getBoolean(REMINDER_STATE);
            toggle.setChecked(reminderState);
        }

        boolean dismissed = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dismissed = getIntent().getExtras().getBoolean("notification");
        }
        System.out.println(dismissed);
        if (dismissed) {
            Toast.makeText(getApplicationContext(), "Reminder Off!", Toast.LENGTH_LONG).show();
            toggle.setChecked(false);

            Intent intent = new Intent(WATCHER_BROADCAST);
            sendBroadcast(intent);
            ((Global) getApplicationContext()).setChecked(false);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(1);
            Log.i("MainActivity", "CANCEL notification");
        }

        if (!((Global) getApplicationContext()).isChecked()) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.reminder_off_title));

            Notification note = mBuilder.build();
            note.flags |= Notification.FLAG_ONGOING_EVENT;
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(2, note);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Reminder On!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WATCHER_BROADCAST);
                    sendBroadcast(intent);
                    ((Global) getApplicationContext()).setChecked(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Reminder Off!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WATCHER_BROADCAST);
                    sendBroadcast(intent);
                    ((Global) getApplicationContext()).setChecked(false);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REMINDER_STATE, ((Global) getApplicationContext()).isChecked());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

}
