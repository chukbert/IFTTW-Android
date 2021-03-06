package com.example.ifttw;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.HashMap;


public class SensorService extends Service implements SensorEventListener, ActionModule {

    int mStartMode; // indicates how to behave if the service is killed
    IBinder mBinder; // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used

    public SensorManager mSensorManager;
    public Sensor mSensorProximity;
    public TextView mTextSensorProximity;

    public static final String CHANNEL_ID = "SensorServiceChannel";
    public static HashMap <Integer, Bundle>  listAction = new HashMap<Integer, Bundle>();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Sensor service was Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int idRoutine = intent.getIntExtra("idRoutine", 0);
        int actionType = intent.getIntExtra("actionType", 0);

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Sensor Service")
                .setContentText("Sensor proximity is active.")
                .setSmallIcon(R.drawable.ic_logo)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(-1, notification);
        //do heavy work on a background thread
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(this, "Sensor service Started", Toast.LENGTH_LONG).show();
        //stopSelf();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentValue = event.values[0];
        Log.d("value" , Float.toString(currentValue));
        if (currentValue < 4) {
            for (Integer idRoutine : listAction.keySet()) {
                if (listAction.get(idRoutine).getInt("actionType") == 1) {
                    String title = listAction.get(idRoutine).getString("title");
                    String description = listAction.get(idRoutine).getString("description");
                    pushNotification(this, idRoutine, title, description);
                } else if (listAction.get(idRoutine).getInt("actionType") == 2) {
                    turnOnWifi(this, idRoutine);
                } else if (listAction.get(idRoutine).getInt("actionType") == 3) {
                    turnOffWifi(this, idRoutine);
                } else if (listAction.get(idRoutine).getInt("actionType") == 4) {
                    sendRequest(this, idRoutine);
                }
            }
        } else {
            // do Nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Sensor Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void pushNotification(Context context, int id, String title, String detail) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("idRoutine", id);
        intent.putExtra("actionType", 1);
        intent.putExtra("title", title);
        intent.putExtra("description", detail);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            notifyPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnOnWifi(Context context, int id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("idRoutine", id);
        intent.putExtra("actionType", 2);

        final PendingIntent turnOnPendingIntent = PendingIntent.getBroadcast(
                context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        try {
            turnOnPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnOffWifi(Context context, int id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("idRoutine", id);
        intent.putExtra("actionType", 3);

        final PendingIntent turnOffPendingIntent = PendingIntent.getBroadcast(
                context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        try {
            turnOffPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendRequest(Context context, int id) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("idRoutine", id);
        intent.putExtra("actionType", 4);

        final PendingIntent sendRequestPendingIntent = PendingIntent.getBroadcast(
                context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        try {
            sendRequestPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}