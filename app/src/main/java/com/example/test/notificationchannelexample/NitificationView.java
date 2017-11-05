package com.example.test.notificationchannelexample;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sumon.chatterjee on 05/11/17.
 */

public class NitificationView extends AppCompatActivity {
    String title;
    String text;
    TextView txttitle;
    TextView txttext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);

        // Retrive the data from MainActivity.java
        Intent i = getIntent();

        title = i.getStringExtra("title");
        text = i.getStringExtra("text");

        // Locate the TextView
        txttitle = (TextView) findViewById(R.id.title);
        txttext = (TextView) findViewById(R.id.text);

        // Set the data into TextView
        txttitle.setText(title);
        txttext.setText(text);
    }
}
