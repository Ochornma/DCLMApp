package org.dclm.live.ui.ondemand.video;

import androidx.annotation.RequiresApi;
import androidx.core.app.ShareCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.Util;

import org.dclm.live.R;
import org.dclm.live.databinding.VideoPlayFragmentBinding;
import org.dclm.live.ui.ondemand.OnDemand;
import org.dclm.live.ui.ondemand.search.SearchResultFragment;

import org.dclm.live.ui.watch.WatchService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class VideoPlayFragment extends Fragment implements VideoPlayAdapter.Click {

    private static final int PERMISSION_CODE = 123;

    private VideoService videoService;
    private VideoPlayFragmentBinding binding;
    public static List<OnDemand> list;
    private ExoPlayer exoPlayer;
    private VideoPlayAdapter adapter;
    private boolean mbound;
    public static long position;
    public static int position2;
    public static String startState;
    private ImageButton download, share;
    private LinearLayout highLow;
    private Button high, low, cancel;
    public static boolean destroy = false;
    private String quality;
    private ActionBar actionBar;

    private VideoPlayViewModel mViewModel;

    public static VideoPlayFragment newInstance() {
        return new VideoPlayFragment();
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VideoService.VideoBinder binder = (VideoService.VideoBinder) service;
            videoService = binder.getVideoService();
            exoPlayer = binder.getPlayer();
            binding.player.setPlayer(binder.getPlayer());
            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mbound){
                binding.player.setPlayer(exoPlayer);
            }
            mbound = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActivity().getActionBar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.video_play_fragment, container, false);
        Intent intent = new Intent(getActivity(), WatchService.class);
        getActivity().stopService(intent);

        Log.i("1", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.player.setDefaultArtwork(getActivity().getDrawable(R.drawable.background));
        }
        cancel = binding.getRoot().findViewById(R.id.cancel);
        download = binding.getRoot().findViewById(R.id.download);
        share = binding.getRoot().findViewById(R.id.share);
        highLow = binding.getRoot().findViewById(R.id.high_low);
        highLow.setVisibility(View.GONE);
        high = binding.getRoot().findViewById(R.id.high);
        low = binding.getRoot().findViewById(R.id.low);
        binding.player.setKeepScreenOn(true);
        binding.player.setUseController(true);
        binding.player.setControllerShowTimeoutMs(0);
        binding.player.setControllerHideOnTouch(false);



        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pictureInPic(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            int width = p.x;
            int height = p.y;
           Rational ratio = new Rational( width, height);
            PictureInPictureParams.Builder
                    pip_Builder
                    = new PictureInPictureParams
                    .Builder();
            pip_Builder.setAspectRatio(ratio).build();
            getActivity().enterPictureInPictureMode(pip_Builder.build());
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VideoPlayViewModel.class);

        download.setOnClickListener(v -> {
            if (highLow.getVisibility() == View.GONE){
                highLow.setVisibility(View.VISIBLE);
            } else {
                highLow.setVisibility(View.GONE);
            }
        });

        cancel.setOnClickListener(v -> {
            if (highLow.getVisibility() == View.GONE){
                highLow.setVisibility(View.VISIBLE);
            } else {
                highLow.setVisibility(View.GONE);
            }
        });

        share.setOnClickListener(v ->{
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle(R.string.onDemand)
                    .setText("720p: " + list.get(videoService.position).getUrlHigh() +"\n480p: " +  list.get(videoService.position).getUrlLow())
                    .setSubject(list.get(videoService.position).getTitle())
                    .startChooser();
        });

        high.setOnClickListener( v ->{
            download(list.get(videoService.position).getUrlHigh());
            quality = list.get(videoService.position).getUrlHigh();
            highLow.setVisibility(View.GONE);
        });

        low.setOnClickListener( v ->{
            download(list.get(videoService.position).getUrlLow());
            quality = list.get(videoService.position).getUrlLow();
            highLow.setVisibility(View.GONE);
        });

    }

    @Override
    public void onStart() {
        Log.i("3", "3");
        super.onStart();
        Intent intent = new Intent(getContext(), VideoService.class);
        startState = VideoPlayFragmentArgs.fromBundle(getArguments()).getState();

        if  ( startState != null){
            list = new ArrayList<>();
            if (startState.equals("search")){
                list = SearchResultFragment.onDemands;
            } else {
                list = VideoDemandFragment.podcasts2;
            }
            adapter = new VideoPlayAdapter(list, this);
           /* binding.recycler.setHasFixedSize(true);
            binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            binding.recycler.setAdapter(adapter);*/
            Util.startForegroundService(getActivity(), intent);
            getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
            binding.player.setPlayer(exoPlayer);
            mbound = true;
        } else {
            getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
            // podcastService.startPlay();
            binding.player.setPlayer(exoPlayer);
            mbound = true;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPic();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i("5", "5");
        if (mbound){
            position = exoPlayer.getContentPosition();
            position2 = videoService.position;
            binding.player.setPlayer(null);
            //mbound = false;
            //getActivity().unbindService(connection);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pictureInPic();
            } else {
                mbound = false;
                getActivity().unbindService(connection);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mbound){
            mbound = false;
            getActivity().unbindService(connection);
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode){

           //actionBar.hide();
            //binding.linear.setVisibility(View.GONE);
           fullScreen();
            download.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            highLow.setVisibility(View.GONE);
            highLow.setVisibility(View.GONE);
            //binding.dragUp.setVisibility(View.GONE);
            high.setVisibility(View.GONE);
            low .setVisibility(View.GONE);
            binding.player.hideController();
            binding.player.setUseController(false);
            //binding.linear.setVisibility(View.GONE);

           // binding.recycler.setVisibility(View.GONE);
        }else {
            //actionBar.show();
           //binding.linear.setVisibility(View.VISIBLE);
           notFullScreen();
            download.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            highLow.setVisibility(View.VISIBLE);
          //  binding.dragUp.setVisibility(View.VISIBLE);
            highLow.setVisibility(View.VISIBLE);
            high.setVisibility(View.VISIBLE);
            low .setVisibility(View.VISIBLE);
            binding.player.showController();
            binding.player.setUseController(true);
           //binding.recycler.setVisibility(View.VISIBLE);
        }
    }

    public void download(String Url){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                startDownloading(Url);
            }
        } else {
            startDownloading(Url);
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void notFullScreen(){
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

           /* toolbar1.setVisibility(View.VISIBLE);
            bootom.setVisibility(View.VISIBLE);*/
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.player.getLayoutParams();
            params.width =  params.MATCH_PARENT;
            params.height = (int) (getActivity().getApplicationContext().getResources().getDisplayMetrics().density);
            binding.player.setLayoutParams(params);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void fullScreen(){
       // UIUtil.hideKeyboard(getActivity());
       // fullscreenButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_exit));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        //toolbar1.setVisibility(View.GONE);

       // bootom.setVisibility(View.GONE);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.player.getLayoutParams();
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height =  ViewGroup.LayoutParams.MATCH_PARENT;
        binding.player.setLayoutParams(params);

    }
    private void startDownloading(String url) {
        //String url = list.get(videoService.position).getUrlHigh();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("DCLM APP");
        request.setDescription(list.get(videoService.position).getTitle());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, list.get(videoService.position).getTitle());
        DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading(quality);
            } else {
                Toast.makeText(getActivity(), "Permission Refused...", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void clicked(View view, int position) {

    }
}
