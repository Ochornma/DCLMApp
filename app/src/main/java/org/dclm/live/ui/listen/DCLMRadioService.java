package org.dclm.live.ui.listen;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.MediaController;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media.session.MediaButtonReceiver;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.dclm.live.util.C;
import org.dclm.live.MainActivity;
import org.dclm.live.R;

public class DCLMRadioService extends Service implements MediaController.MediaPlayerControl {

    private final IBinder binder = new RadioBinder();
    public static boolean checkPlay = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class RadioBinder extends Binder {
        public DCLMRadioService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DCLMRadioService.this;
        }

    }

    public SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private Context context;
    private boolean check;
    private MediaSource mediaSource;
    private SharedPreferences preferences;
    private static final String PREFRENCES = "org.dclm.radio";



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
      preferences = getApplicationContext().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(com.google.android.exoplayer2.C.USAGE_MEDIA)
                .setContentType(com.google.android.exoplayer2.C.CONTENT_TYPE_SPEECH)
                .build();

        player = ExoPlayerFactory.newSimpleInstance(this);
        player.setAudioAttributes(audioAttributes, true);

        {
            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
        }
        mediaSession = new MediaSessionCompat(context, C.MEDIA_SESSION_TAG);
        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(!playWhenReady){
                    RadioFragment.state = false;
                    checkPlay = false;
                    stopForeground(false);
                  /*  stopForeground(false);
                    stopSelf();*/

                } else {
                    RadioFragment.state = true;
                    checkPlay = true;
                    startForeground();
                    //mediaSession.setActive(true);
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }
        });

      /*  playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context, C.PLAYBACK_CHANNEL_ID,
                R.string.app_name, R.string.app_describe, C.PLAYBACK_NOTIFICATION_ID, new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return getString(R.string.app_name);
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("from service", "From Service");
                        Bundle bundle = new Bundle();
                        bundle.putString("link", preferences.getString("language", getString(R.string.select_french_language)));
                        bundle.putString("url", preferences.getString("url", getString(R.string.dclm_api)));
                        // Log.i("linkService", preferences.getString("language", getString(R.string.radio_link)));

                        return new NavDeepLinkBuilder(context)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.radioFragment)
                                .setArguments(bundle)
                                .createPendingIntent();


                        //return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        return getString(R.string.app_describe);
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return getBitmap(context, R.drawable.nlogo);
                    }
                }, new PlayerNotificationManager.NotificationListener() {
                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            stopForeground(STOP_FOREGROUND_REMOVE);
                            //stopForeground(false);
                        } else {
                            stopForeground(false);
                        }
                    }
                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                        *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForeground(notificationId, notification);
                        }*//*
                        startForeground(notificationId, notification);

                    }
                });*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String url = RadioFragment.link;

        check = true;
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(context, getString(R.string.app_name)),
                null /* listener */,
                30 * 1000,
                30 * 1000,
                true //allowCrossProtocolRedirects
        );


        mediaSource = new ProgressiveMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(Uri.parse(url));
        player.prepare(mediaSource);
        startForeground();
        playerNotificationManager.setSmallIcon(R.drawable.nlogo);
        playerNotificationManager.setUseStopAction(true);
        playerNotificationManager.setUseNavigationActions(false);
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setPlayer(player);

        mediaSession.setActive(true);
        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());
        mediaSessionConnector = new MediaSessionConnector(mediaSession);
        mediaSessionConnector.setPlayer(player);
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        player.setPlayWhenReady(true);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (check) {
            mediaSession.release();
            mediaSessionConnector.setPlayer(null);
            playerNotificationManager.setPlayer(null);
            player.release();
        }
        player = null;
        super.onDestroy();
    }


    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }

    public void pausePlayer() {
        if (player.isPlaying()) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            //RadioFragment.state = false;
        }
    }

    public void startPlayer() {
        if (!player.isPlaying()) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
            //RadioFragment.state = true;
        }
    }

    private void startForeground(){
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context, C.PLAYBACK_CHANNEL_ID,
                R.string.app_name, R.string.app_describe, C.PLAYBACK_NOTIFICATION_ID, new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return getString(R.string.app_name);
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Bundle bundle = new Bundle();
                        bundle.putString("link", preferences.getString("language", getString(R.string.select_french_language)));
                        bundle.putString("url", preferences.getString("url", getString(R.string.dclm_api)));

                        return new NavDeepLinkBuilder(context)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.radioFragment)
                                .setArguments(bundle)
                                .createPendingIntent();
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        return getString(R.string.app_describe);
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return getBitmap(context, R.drawable.nlogo);
                    }
                }, new PlayerNotificationManager.NotificationListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {

                        if (dismissedByUser){
                            stopSelf();
                        }
                        stopSelf();
                       // stopForeground(STOP_FOREGROUND_REMOVE);

                       // stopSelf();
                        //new RadioFragment().boundUnbound();


                    }
                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                        if (ongoing){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                startForeground(notificationId, notification);
                            }
                        } else {
                            stopForeground(false);
                        }


                    }
                });
        playerNotificationManager.setSmallIcon(R.drawable.nlogo);
        playerNotificationManager.setUseStopAction(true);
        playerNotificationManager.setUseNavigationActions(false);
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setPlayer(player);
        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());
    }

    @Override
    public void start() {
        startPlayer();
    }



    @Override
    public void pause() {
        pausePlayer();

    }



    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

}
