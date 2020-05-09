package org.dclm.live.ui.jotter.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.ui.jotter.AddNoteViewModel;


public class AddNoteFactory implements ViewModelProvider.Factory {

    private Application application;

    public AddNoteFactory(Application mApplication) {
        application = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddNoteViewModel.class)) {
            return (T) new AddNoteViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
