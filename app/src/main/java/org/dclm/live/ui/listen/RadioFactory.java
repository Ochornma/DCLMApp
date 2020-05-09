package org.dclm.live.ui.listen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.SubtitleReceived;

public class RadioFactory implements ViewModelProvider.Factory {
    private Context context;
    private SubtitleReceived subtitleReceived;

    public RadioFactory(Context context, SubtitleReceived subtitleReceived){
        this.context = context;
        this.subtitleReceived = subtitleReceived;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      if (modelClass.isAssignableFrom(RadioViewModel.class)) {
            return  (T) new RadioViewModel(context, subtitleReceived);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
