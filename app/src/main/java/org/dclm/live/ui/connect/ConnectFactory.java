package org.dclm.live.ui.connect;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ConnectFactory implements ViewModelProvider.Factory {
    private Context mcontext;

    public ConnectFactory(Context context){
        mcontext = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ConnectViewModel.class)) {
            return  (T) new ConnectViewModel(mcontext);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
