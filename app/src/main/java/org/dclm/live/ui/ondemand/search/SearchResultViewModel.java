package org.dclm.live.ui.ondemand.search;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import org.dclm.live.util.DemandRepository;
import org.dclm.live.util.SearchNetworkCall;

public class SearchResultViewModel extends ViewModel {

    private DemandRepository repository;
    private Context context;

    public SearchResultViewModel(Context context, SearchNetworkCall networkCall) {
        repository = new DemandRepository(context, networkCall);
        this.context = context;
    }

    public void getEvent(int page, ProgressBar progressBar, String url, String eventID, String languageID) {
        progressBar.setVisibility(View.VISIBLE);
        repository.jsonEvent(eventID, languageID, url, page);
    }

    public void getTopicResult(String page, ProgressBar progressBar, String url, EditText editText, String languageID) {
        progressBar.setVisibility(View.VISIBLE);
        String check = "\'s";
        //String mtopic = topic.replace(check, "&rsquo;s");
        repository.jsonTopic(getTitle(editText).replace("\'s", "&rsquo;s"), languageID, url, Integer.parseInt(page));
        Toast.makeText(context, getTitle(editText).replace(check, "&rsquo;s"), Toast.LENGTH_SHORT).show();
    }


    public String getTitle(EditText editText) {
        return editText.getText().toString();
    }
}

