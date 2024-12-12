// SPDX-FileCopyrightText: 2024 Open Mobile Platform LLC <community@omp.ru>
// SPDX-License-Identifier: BSD-3-Clause

package com.example.quickstartpush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ru.omp.aurora.center.services.push.PushSdk;
import ru.omp.aurora.center.services.push.OnCompleteListener;
import ru.omp.aurora.center.services.push.Task;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String applicationId = "app_ct85srhpfb7al1qk8q6g";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "default";
        String channelName = "defaultChannel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        PushSdk.getInstance().getToken(getApplicationContext(), applicationId)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching registration token failed, error: " + task.getResult());
                            return;
                        }

                        String token = task.getResult();
                        Log.d(TAG, "token: " + token);
                        ((TextView)findViewById(R.id.textView)).setText(token);
                    }
                });
    }
}