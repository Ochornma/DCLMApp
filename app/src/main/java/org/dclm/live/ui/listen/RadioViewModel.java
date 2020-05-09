package org.dclm.live.ui.listen;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.exoplayer2.util.Util;

import org.dclm.live.MainActivity;
import org.dclm.live.R;
import org.dclm.live.util.DemandRepository;
import org.dclm.live.util.SubtitleReceived;

public class RadioViewModel extends ViewModel {

    public MutableLiveData<Boolean> checkButton;
    public MutableLiveData<String> link;
    private DemandRepository repository;


    public RadioViewModel(Context context, SubtitleReceived subtitleReceived) {
        checkButton = new MutableLiveData<>();
        link = new MutableLiveData<>();
        repository = new DemandRepository(context, subtitleReceived);
    }

    public void playPause(boolean state) {
        state = RadioFragment.state;
        checkButton.postValue(state);
    }

    public void navigateLanguage(View view) {
        Navigation.findNavController(view).navigate(R.id.action_radioFragment_to_languageSelection);
    }


    void jsonParse(String url) {
     repository.jsonParse(url);
    }
}

