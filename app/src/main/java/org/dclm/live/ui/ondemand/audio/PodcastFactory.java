package org.dclm.live.ui.ondemand.audio;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.PodcastNetworkCall;

public class PodcastFactory implements ViewModelProvider.Factory {
    private Context mcontext;
    private PodcastNetworkCall nnetworkCall;

    public PodcastFactory(Context context, PodcastNetworkCall networkCall){
        mcontext = context;
        nnetworkCall = networkCall;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PodCastDisplayViewModel.class)) {
            return  (T) new PodCastDisplayViewModel(mcontext, nnetworkCall);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
