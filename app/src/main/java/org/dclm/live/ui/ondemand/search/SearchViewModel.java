package org.dclm.live.ui.ondemand.search;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;


import androidx.lifecycle.ViewModel;

import org.dclm.live.util.DemandRepository;
import org.dclm.live.util.SearchNetworkCall;

public class SearchViewModel extends ViewModel {
    private DemandRepository repository;

    public SearchViewModel(Context context, SearchNetworkCall networkCall){
        repository = new DemandRepository(context, networkCall);
    }

    public void getEvent(int page, ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        repository.getEvents(page);
    }
}
