package org.dclm.live.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.dclm.live.MainActivity;
import org.dclm.live.R;

public class MyFirebaseNotification extends FirebaseMessagingService {


    private static final String NOTIFICATION_CHANNEL_ID = "Event";
    private String default_notification_channel_id = "Event";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        Intent notificationIntent = new Intent(getApplicationContext() , MainActivity. class ) ;
        notificationIntent.putExtra( "NotificationMessage" , "I am from Notification" ) ;
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        //PendingIntent resultIntent = PendingIntent. getActivity (getApplicationContext() , 0 , notificationIntent , 0 ) ;
     /*  NavDeepLinkBuilder(context)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.radioFragment)
                .setArguments(bundle)
                .createPendingIntent();*/
        SharedPreferences sharedPreferences = getSharedPreferences("firebase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("title", remoteMessage.getData().get("title"));
        editor.putString("message", remoteMessage.getData().get("message"));
        editor.apply();
        Log.i("title", remoteMessage.getData().get("title"));
        Log.i("message", remoteMessage.getData().get("message"));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() ,
                default_notification_channel_id )
                .setSmallIcon(R.drawable.nlogo)
                .setContentTitle( "DCLM" )
                .setContentText("DCLM NOTIFICATION")
                .setContentIntent(new NavDeepLinkBuilder(getApplicationContext())
                        .setGraph(R.navigation.mobile_navigation)
                        .setDestination(R.id.radioFragment)
                        //.setArguments(bundle)
                        .createPendingIntent());
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "DCLM" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }
}
