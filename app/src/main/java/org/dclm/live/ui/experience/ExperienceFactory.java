package org.dclm.live.ui.experience;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.TestimonyRecieved;

public class ExperienceFactory implements ViewModelProvider.Factory {
    private Context context;
    private TestimonyRecieved testimonyRecieved;

    public ExperienceFactory(Context context, TestimonyRecieved testimonyRecieved){
        this.context = context;
        this.testimonyRecieved = testimonyRecieved;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ExperienceViewModel.class)) {
            return  (T) new ExperienceViewModel(context, testimonyRecieved);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
