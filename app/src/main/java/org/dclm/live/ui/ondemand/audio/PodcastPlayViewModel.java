package org.dclm.live.ui.ondemand.audio;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PodcastPlayViewModel extends ViewModel {

    public MutableLiveData<Boolean> checkButton;


    public PodcastPlayViewModel(){
        checkButton = new MutableLiveData<>();
    }

    public void checkAdapter(boolean destroy){
        destroy =PodcastPlayFragment.destroy;
        checkButton.postValue(destroy);
    }
}
