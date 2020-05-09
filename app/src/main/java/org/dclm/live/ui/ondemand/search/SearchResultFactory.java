package org.dclm.live.ui.ondemand.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.SearchNetworkCall;

public class SearchResultFactory implements ViewModelProvider.Factory {
    private Context mcontext;
    private SearchNetworkCall searchNetworkCall;

    public SearchResultFactory(Context context, SearchNetworkCall networkCall){
        mcontext = context;
        searchNetworkCall = networkCall;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchResultViewModel.class)) {
            return  (T) new SearchResultViewModel(mcontext, searchNetworkCall);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
