package com.example.test.notificationchannelexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import static com.example.test.notificationchannelexample.NotificationHelper.PRIMARY_CHANNEL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTI_PRIMARY1 = 1100;
    private static final int NOTI_PRIMARY2 = 1101;
    private static final int NOTI_SECONDARY1 = 1200;
    private static final int NOTI_SECONDARY2 = 1201;
    private NotificationHelper noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noti = new NotificationHelper(this);
        findViewById(R.id.main_primary_send1).setOnClickListener(this);
        findViewById(R.id.main_primary_send2).setOnClickListener(this);
        findViewById(R.id.main_primary_config).setOnClickListener(this);
        findViewById(R.id.main_secondary_send1).setOnClickListener(this);
        findViewById(R.id.main_secondary_send2).setOnClickListener(this);
        findViewById(R.id.main_secondary_config).setOnClickListener(this);


    }

    public void sendNotification(int id, String title) {
        Notification.Builder nb = null;
        switch (id) {
            case NOTI_PRIMARY1:
                nb = noti.getNotification1(title, "First Notification for primary channel");
                break;

            case NOTI_PRIMARY2:
               // nb = noti.getNotification1(title, "Second Notification for Primary Channel");
                customNotification();
                break;

            case NOTI_SECONDARY1:
                nb = noti.getNotification2(title, "First Notification for Seconday channl");
                break;

            case NOTI_SECONDARY2:
                nb = noti.getNotification2(title, "Second Notification for Secondary Channel");
                break;
        }
        if (nb != null) {
            noti.notify(id, nb);
        }
    }


    /**
     * Send Intent to load system Notification Settings for this app.
     */
    public void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.
     *
     * @param channel Name of channel to configure
     */
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }

    // custom notification with remote view
    private void customNotification() {

        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_notification);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, NitificationView.class);
        // Send data to NotificationView Class
        intent.putExtra("title", "Custom Notification");
        intent.putExtra("text", "");
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                // Set Ticker Message
                .setTicker("Hiiiiii !!!")
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                .setChannelId(PRIMARY_CHANNEL)

                // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.imagenotileft,R.mipmap.ic_launcher);
        remoteViews.setImageViewResource(R.id.imagenotiright,R.mipmap.ic_launcher);

        remoteViews.setTextViewText(R.id.title,"Notification with remote Views");
        remoteViews.setTextViewText(R.id.text,"check this");

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_primary_send1:
                sendNotification(NOTI_PRIMARY1, "primary1 channel text");
                break;
            case R.id.main_primary_send2:
                sendNotification(NOTI_PRIMARY2, "primary2 channel text");
                break;
            case R.id.main_primary_config:
                goToNotificationSettings(PRIMARY_CHANNEL);
                break;

            case R.id.main_secondary_send1:
                sendNotification(NOTI_SECONDARY1, "seconday1 channel text");
                break;
            case R.id.main_secondary_send2:
                sendNotification(NOTI_SECONDARY2, "seconday 2 channel text");
                break;
            case R.id.main_secondary_config:
                goToNotificationSettings(NotificationHelper.SECONDARY_CHANNEL);
                break;
            case R.id.btnA:
                goToNotificationSettings();
                break;
            default:
                break;
        }
    }
}
