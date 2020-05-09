package org.dclm.live.ui.ondemand.audio;

import androidx.core.app.ShareCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.util.Util;

import org.dclm.live.R;
import org.dclm.live.databinding.PodcastPlayFragmentBinding;
import org.dclm.live.ui.listen.DCLMRadioService;

import org.dclm.live.ui.ondemand.OnDemand;
import org.dclm.live.ui.ondemand.search.SearchResultFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class PodcastPlayFragment extends Fragment implements PlayerAdapter.Click {
    private static final int PERMISSION_CODE = 123;

    private PodcastService podcastService;
    private PodcastPlayViewModel mViewModel;
    private PodcastPlayFragmentBinding binding;
    public static List<OnDemand> list;
    private ExoPlayer exoPlayer;
    private PlayerAdapter adapter;
    private boolean mbound;
    public static long position;
    public static int position2;
    public static String startState;
    private ImageButton download, share;
    public static boolean destroy = false;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PodcastService.PodcastBinder binder = (PodcastService.PodcastBinder) service;
            podcastService = binder.getService();
            binding.player.setPlayer(binder.getPlayer());
            exoPlayer = binder.getPlayer();
            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mbound){
                binding.player.setPlayer(null);
            }
            mbound = false;
        }
    };


    public static PodcastPlayFragment newInstance() {
        return new PodcastPlayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i("a", "a");

        Intent intent = new Intent(getActivity(), DCLMRadioService.class);
        getActivity().stopService(intent);


        binding = DataBindingUtil.inflate(inflater, R.layout.podcast_play_fragment, container, false);
        //Log.i("hxvvvhs", String.valueOf(list.size()));

        //destroy = false;
       /* if (PodcastService.destroyed){
            list.clear();
        }*/
        //adapter = new PlayerAdapter(this);



        Log.i("1", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.player.setDefaultArtwork(getActivity().getDrawable(R.drawable.background));
        }
        download = binding.getRoot().findViewById(R.id.download);
        share = binding.getRoot().findViewById(R.id.share);
        binding.player.setKeepScreenOn(true);
        binding.player.setUseController(true);
        binding.player.setControllerShowTimeoutMs(0);
        binding.player.setControllerHideOnTouch(false);
        //binding.player.

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("b", "b");
        Intent intent = new Intent(getActivity(), PodcastService.class);
        mViewModel = new ViewModelProvider(this).get(PodcastPlayViewModel.class);
        mViewModel.checkAdapter(false);
        mViewModel.checkButton.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                   /* list.clear();
                    adapter = new PlayerAdapter(list);
                    binding.recycler.setHasFixedSize(true);
                    binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.VERTICAL, false));
                    binding.recycler.setAdapter(adapter);*/
                }
            }
        });
        download.setOnClickListener( v -> {
            download();
        });

        share.setOnClickListener( v1 ->{
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle(R.string.onDemand)
                    .setText(list.get(podcastService.position).getUrlAudio())
                    .setSubject(list.get(podcastService.position).getTitle())
                    .startChooser();
        });
    }

    @Override
    public void onStart() {
        Log.i("c", "c");
        super.onStart();
        Intent intent = new Intent(getContext(), PodcastService.class);
        startState = PodcastPlayFragmentArgs.fromBundle(getArguments()).getTitle();
        if  ( startState != null){
            list = new ArrayList<>();
            list.clear();
            if (startState.equals("search")){
                list = SearchResultFragment.onDemands;
            } else {
                list = PodCastDisplayFragment.podcasts2;
            }
            adapter = new PlayerAdapter(list, this);
            binding.recycler.setHasFixedSize(true);
            binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            binding.recycler.setAdapter(adapter);
            getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
            Util.startForegroundService(getActivity(), intent);
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
    public void onResume() {
        Log.i("d", "d");
      // list = PodCastDisplayFragment.podcasts2;
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("e", "e");
        if (mbound){
            position = exoPlayer.getContentPosition();
            position2 = podcastService.position;
            binding.player.setPlayer(null);
            mbound = false;
            getActivity().unbindService(connection);
        }
    }

    @Override
    public void onDestroy() {
        Log.i("f", "f");
        super.onDestroy();
        if (mbound){
            mbound = false;
            getActivity().unbindService(connection);
        }
    }

    private void download(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                startDownloading();
            }
        } else {
            startDownloading();
        }
    }

    private void startDownloading() {
        String url = list.get(podcastService.position).getUrlHigh();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("DCLM APP");
        request.setDescription(list.get(podcastService.position).getTitle());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, list.get(podcastService.position).getTitle());
        DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading();
            } else {
                Toast.makeText(getActivity(), "Permission Refused...", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void clear(){
        list.clear();
        //adapter = new PlayerAdapter(list, this::click);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
    }
    @Override
    public void clicked(View view, int position) {
        if (podcastService != null){
            podcastService.skipNext(position);
        }

    }
}
