package org.dclm.live.ui.watch;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayer;


import org.dclm.live.R;
import org.dclm.live.databinding.WatchFragmentBinding;
import org.dclm.live.ui.ondemand.video.VideoService;

import static android.content.Context.BIND_AUTO_CREATE;

public class WatchFragment extends Fragment {

    private WatchViewModel mViewModel;
    private WatchFragmentBinding binding;
    private boolean bonded;
    private WatchService watchService;
    private ExoPlayer exoPlayer;
    public static String link;

    public static WatchFragment newInstance() {
        return new WatchFragment();
    }

private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        WatchService.VideoBinder binder = (WatchService.VideoBinder) service;
        exoPlayer = binder.getService();
        binding.player.setPlayer(binder.getService());
        bonded = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binding.player.setPlayer(null);
    }
};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.watch_fragment, container, false);
        link =  getActivity().getResources().getString(R.string.video_url);
        Intent intent = new Intent(getActivity(), VideoService.class);
        getActivity().stopService(intent);
        if (link.equals("rtmp://dclmict.org/live/app")){
            binding.selectEnglish.setVisibility(View.VISIBLE);
            binding.selectFrench.setVisibility(View.INVISIBLE);
        } else if (link.equals("rtmp://dclmict.org/live/french")){
            binding.selectFrench.setVisibility(View.VISIBLE);
            binding.selectEnglish.setVisibility(View.INVISIBLE);
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WatchViewModel.class);
        Intent intent = new Intent(getContext(), WatchService.class);
        binding.english.setOnClickListener(v -> {
            link = "rtmp://dclmict.org/live/app";
            getActivity().stopService(new Intent(getActivity(), WatchService.class));
            getActivity().unbindService(connection);
            getActivity().startService(new Intent(getActivity(), WatchService.class));
            getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
            binding.player.setPlayer(exoPlayer);
            binding.selectEnglish.setVisibility(View.VISIBLE);
            binding.selectFrench.setVisibility(View.INVISIBLE);
        });
        binding.french.setOnClickListener(v -> {
            link = "rtmp://dclmict.org/live/french";
            getActivity().stopService(new Intent(getActivity(), WatchService.class));
            getActivity().unbindService(connection);
            getActivity().startService(new Intent(getActivity(), WatchService.class));
           getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
            binding.player.setPlayer(exoPlayer);
            binding.selectFrench.setVisibility(View.VISIBLE);
            binding.selectEnglish.setVisibility(View.INVISIBLE);
        });
    }

    /*public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }*/

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getContext(), WatchService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
        binding.player.setPlayer(exoPlayer);
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.player.setPlayer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bonded){
            bonded = false;
            getActivity().unbindService(connection);
        }
    }
}
