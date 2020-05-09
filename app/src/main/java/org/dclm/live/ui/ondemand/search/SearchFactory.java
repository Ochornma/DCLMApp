package org.dclm.live.ui.ondemand.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.dclm.live.util.SearchNetworkCall;

public class SearchFactory implements ViewModelProvider.Factory {
    private Context mcontext;
    private SearchNetworkCall searchNetworkCall;

    public SearchFactory(Context context, SearchNetworkCall networkCall){
        mcontext = context;
        searchNetworkCall = networkCall;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return  (T) new SearchViewModel(mcontext, searchNetworkCall);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
