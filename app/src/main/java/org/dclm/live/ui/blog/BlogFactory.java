package org.dclm.live.ui.blog;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.NetworkCall;

public class BlogFactory implements ViewModelProvider.Factory {

    private Context mcontext;
    private Application mApp;
    private NetworkCall networkCall;
    public BlogFactory(Context context, Application application, NetworkCall networkCall1){
        mcontext = context;
        mApp = application;
        networkCall = networkCall1;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BlogViewModel.class)) {
            return  (T) new BlogViewModel(mcontext, mApp, networkCall);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
