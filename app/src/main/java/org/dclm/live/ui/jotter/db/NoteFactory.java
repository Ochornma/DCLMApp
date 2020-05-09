package org.dclm.live.ui.jotter.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.ui.jotter.NoteViewModel;


public class NoteFactory implements ViewModelProvider.Factory {

    private Application application;

    public NoteFactory(Application mApplication) {
        application = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NoteViewModel.class)) {
            return (T) new NoteViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

