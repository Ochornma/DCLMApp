package org.dclm.live.ui.ondemand.audio;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.dclm.live.util.DemandRepository;
import org.dclm.live.ui.ondemand.OnDemand;
import org.dclm.live.util.PodcastNetworkCall;

import java.util.List;

public class PodCastDisplayViewModel extends ViewModel {
   private DemandRepository repository;

    public PodCastDisplayViewModel(Context context, PodcastNetworkCall networkCall){
        repository = new DemandRepository(context, networkCall);
    }

    public void getPodcast(String url, int page, int languageId, List<OnDemand.Category> categories){
        repository.jsonParse(url, page, languageId, categories);
    }

    public void getCategory(){
        repository.categoryJson();
    }
}
