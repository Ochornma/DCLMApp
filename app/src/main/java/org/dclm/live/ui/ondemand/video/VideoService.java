package org.dclm.live.ui.ondemand.video;

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
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media.session.MediaButtonReceiver;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.dclm.live.util.C;
import org.dclm.live.MainActivity;
import org.dclm.live.R;

public class VideoService extends Service implements MediaController.MediaPlayerControl {

    private final IBinder binder = new VideoBinder();
    public static boolean checkPlay = false;


    public class VideoBinder extends Binder{
        public VideoService getVideoService(){
            return VideoService.this;
        }

        public ExoPlayer getPlayer(){
            return player;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private Context context;
    private boolean check;
    private MediaSource mediaSource;
    private SharedPreferences preferences;
    private static final String PREFRENCES = "org.dclm.radio_podcast";
    public int position;
    public long currentPosition;
    public static boolean destroyed = false;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("crwate", "create");
        destroyed = false;

        context = this;
        preferences = getApplicationContext().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(com.google.android.exoplayer2.C.USAGE_MEDIA)
                .setContentType(com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC)
                .build();

        player = ExoPlayerFactory.newSimpleInstance(this);
        player.setAudioAttributes(audioAttributes, true);

        {
            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
        }
        mediaSession = new MediaSessionCompat(context, C.MEDIA_SESSION_TAG_VIDEO_DEMAND);
        player.addListener(new Player.EventListener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(!playWhenReady){
                    stopForeground(false);
                } else {
                    // RadioFragment.state = true;
                    checkPlay = true;
                    startForeground();
                    mediaSession.setActive(true);
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                int latestWindowIndex = player.getCurrentWindowIndex();
                if (latestWindowIndex != position) {
                    // item selected in playlist has changed, handle here
                    position = latestWindowIndex;
                    // ...
                }
            }
        });

    }

    public void startPlay(int mposition, long mcurrentPosition){
        player.prepare(mediaSource);
        player.seekTo(mposition, mcurrentPosition );
        player.setPlayWhenReady(true);
    }

    public void skipNext(int mposition){
        if (mediaSource != null){
            player.prepare(mediaSource);
            player.seekTo(mposition, com.google.android.exoplayer2.C.TIME_UNSET);
            player.setPlayWhenReady(true);
        }

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("unbind", "unbind");
        position =  player.getCurrentWindowIndex();
        currentPosition = player.getContentPosition();
        //stopForeground(false);
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        Log.i("rebind", "rebind");
        super.onRebind(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String[] url = PodcastPlayFragment.list;
        Log.i("start", "start");

        Log.i("play button2", "pressed");
        //Log.i("hxvvvhs", String.valueOf(list.size()));

        check = true;
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(context, getString(R.string.app_name)),
                null /* listener */,
                30 * 1000,
                30 * 1000,
                true //allowCrossProtocolRedirects
        );

        MediaSource[] mediaSources = new MediaSource[VideoPlayFragment.list.size()];
        for (int i = 0; i < mediaSources.length; i++) {

            Log.i("play button1", "pressed");
            mediaSources[i] = new ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(Uri.parse(VideoPlayFragment.list.get(i).getUrlLow()));
        }
        mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);

        startPlay(position, currentPosition);

        playerNotificationManager.setSmallIcon(R.drawable.nlogo);
        playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setFastForwardIncrementMs(2);
        playerNotificationManager.setRewindIncrementMs(2);
        playerNotificationManager.setUseNavigationActions(true);
        //playerNotificationManager.setUseStopAction(false);
        playerNotificationManager.setPlayer(player);
        mediaSession.setActive(true);
        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());
        //mediaSessionConnector = new MediaSessionConnector(mediaSession);
        //mediaSessionConnector.setPlayer(player);
        MediaButtonReceiver.handleIntent(mediaSession, intent);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("cdestroy", "destroy");
        destroyed = true;
        position =  player.getCurrentWindowIndex();
        currentPosition = player.getContentPosition();
        if (check) {
            mediaSession.release();
            //mediaSessionConnector.setPlayer(null);
            playerNotificationManager.setPlayer(null);
            player.release();
        }
        player = null;


        super.onDestroy();
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }



    @Override
    public void start() {
        if (player.isPlaying()) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @Override
    public void pause() {
        if (!player.isPlaying()) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
            //RadioFragment.state = true;
        }
    }

    private void startForeground(){
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(context, C.PLAYBACK_CHANNEL_ID_VIDEO_DEMAND,
                R.string.app_name, R.string.app_describe, C.PLAYBACK_NOTIFICATION_ID_VIDEO_DEMAND, new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {

                        if (VideoPlayFragment.list != null && VideoPlayFragment.list.size() != 0){
                            return VideoPlayFragment.list.get(player.getCurrentWindowIndex()).getTitle();
                        }
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
                        bundle.putString("state", null);
                        // Log.i("linkService", preferences.getString("language", getString(R.string.radio_link)));

                        return new NavDeepLinkBuilder(context)
                                .setGraph(R.navigation.mobile_navigation)
                                .setDestination(R.id.videoPlayFragment)
                                .setArguments(bundle)
                                .createPendingIntent();
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        //
                        if (VideoPlayFragment.list != null && VideoPlayFragment.list.size() != 0){
                            return VideoPlayFragment.list.get(player.getCurrentWindowIndex()).getCategory();
                        }
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
                       // stopForeground(STOP_FOREGROUND_REMOVE);


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
