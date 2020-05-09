package org.dclm.live.ui.watch;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media.session.MediaButtonReceiver;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

import org.dclm.live.MainActivity;
import org.dclm.live.R;

public class WatchService extends Service {
    public SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediasession;
    private MediaSessionConnector mediaSessionConnector;
    private Context context;
    private AudioAttributes audioAttributes;
    private MediaSource mediaSource;
    private static final String PREFRENCES = "org.dclm.radio";
    private boolean check;


    private final IBinder binder = new VideoBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        player.setPlayWhenReady(true);
        loadMedia();
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(com.google.android.exoplayer2.C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_SPEECH)
                .build();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory();
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);


    }
    private void startForeground(){
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context, org.dclm.live.util.C.PLAYBACK_CHANNEL_ID_VIDEO,
                R.string.app_name, R.string.app_describe, org.dclm.live.util.C.PLAYBACK_NOTIFICATION_ID_VIDEO, new PlayerNotificationManager.MediaDescriptionAdapter() {
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


                        return new NavDeepLinkBuilder(context)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.watchFragment)
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
                        return getBitmap(context, R.drawable.baba_video);
                    }
                }, new PlayerNotificationManager.NotificationListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {

                        if (dismissedByUser){
                            stopSelf();
                        }
                        stopSelf();
                        //stopForeground(STOP_FOREGROUND_REMOVE);


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
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setUseNavigationActions(false);
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setPlayer(player);
        playerNotificationManager.setMediaSessionToken(mediasession.getSessionToken());
    }

    private void loadMedia() {

        String videoUrl = WatchFragment.link;
        check = true;


        player.setAudioAttributes(audioAttributes, true);

            RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
            //String url = getString(R.string.video_url);
            MediaSource videoSource = new ProgressiveMediaSource.Factory(rtmpDataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl));

            player.prepare(videoSource);
            player.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if(!playWhenReady){
                        stopForeground(false);

                    } else {
                        startForeground();
                    }
                }

            });

            mediasession = new MediaSessionCompat(context, "DCLM CHANNEL");
            mediasession.setActive(true);

            mediaSessionConnector = new MediaSessionConnector(mediasession);
            mediaSessionConnector.setPlayer(player);
        startForeground();
        playerNotificationManager.setSmallIcon(R.drawable.nlogo);
        playerNotificationManager.setUseStopAction(true);
        playerNotificationManager.setUseNavigationActions(false);
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setPlayer(player);
        mediasession = new MediaSessionCompat(context, org.dclm.live.util.C.MEDIA_SESSION_TAG_VIDEO);
        mediasession.setActive(true);
        playerNotificationManager.setMediaSessionToken(mediasession.getSessionToken());
        mediaSessionConnector = new MediaSessionConnector(mediasession);
        mediaSessionConnector.setPlayer(player);
        MediaButtonReceiver.handleIntent(mediasession, new Intent(this, WatchService.class));
        player.setPlayWhenReady(true);
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }

    public class VideoBinder extends Binder {
        public ExoPlayer getService() {
            // Return this instance of LocalService so clients can call public methods
            return player;
        }
    }

    @Override
    public void onDestroy() {
        if (check) {
            mediasession.release();
            mediaSessionConnector.setPlayer(null);
            playerNotificationManager.setPlayer(null);
            player.release();
        }
        player = null;
        super.onDestroy();
    }
}
