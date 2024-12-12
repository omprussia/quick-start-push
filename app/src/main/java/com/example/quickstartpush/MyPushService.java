// SPDX-FileCopyrightText: 2024 Open Mobile Platform LLC <community@omp.ru>
// SPDX-License-Identifier: BSD-3-Clause

package com.example.quickstartpush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import ru.omp.aurora.center.services.push.PushMessage;
import ru.omp.aurora.center.services.push.PushService;

public class MyPushService extends PushService {
    private static final String TAG = "MyPushService";
    private static int notificationId = 1;

    @Override
    public void onMessageReceived(PushMessage message) {
        Log.d(TAG, "onMessageReceived: " + message.getMessage());
        showNotification(message);
    }

    void showNotification(PushMessage message) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setColor(Color.parseColor("#27e82f"))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(Color.parseColor("#018be8"))
                .setVibrate(new long[0])
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage());

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
}
