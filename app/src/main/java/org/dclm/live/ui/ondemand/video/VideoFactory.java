package org.dclm.live.ui.ondemand.video;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.ui.ondemand.audio.PodCastDisplayViewModel;
import org.dclm.live.util.PodcastNetworkCall;

public class VideoFactory implements ViewModelProvider.Factory {
    private Context mcontext;
    private PodcastNetworkCall nnetworkCall;

    public VideoFactory(Context context, PodcastNetworkCall networkCall){
        mcontext = context;
        nnetworkCall = networkCall;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoDemandViewModel.class)) {
            return  (T) new VideoDemandViewModel(mcontext, nnetworkCall);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
