package org.dclm.live.ui.ondemand.search;

import androidx.core.app.ShareCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.dclm.live.R;
import org.dclm.live.databinding.SearchResultFragmentBinding;
import org.dclm.live.ui.ondemand.OnDemand;

import org.dclm.live.util.SearchNetworkCall;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class SearchResultFragment extends Fragment implements SearchNetworkCall {

    private static final int PERMISSION_CODE = 123;
    private SearchResultViewModel mViewModel;
    private SearchResultFragmentBinding binding;
    private SearchResultAdapter adapter;
    private String url = "https://api.dclmict.org/v1/sermon/query/?page=";
    private int page = 1;
    private String languageId;
    private RequestQueue mQueue;
    private List<Search> searches;
    private String urlDownload;
    private String titleDownload;
    private String eventId = SearchFragment.eventId;
    public static List<OnDemand> onDemands;


    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_result_fragment, container, false);
        languageId = getActivity().getResources().getString(R.string.languageID);
        adapter = new SearchResultAdapter();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchResultFactory factory = new SearchResultFactory(getActivity(), this);
        mViewModel = new ViewModelProvider(this, factory).get(SearchResultViewModel.class);
        binding.setPage(String.valueOf(page));
        binding.setProgressbar(binding.progressBar);
        binding.setUrl(url);
        binding.setEditText(binding.searchText);
        //binding.setTitle();
        binding.setLanguage(languageId);
        binding.setViewmodel(mViewModel);
        if (!SearchFragment.topic){
            mViewModel.getEvent(page, binding.progressBar, url, SearchFragment.eventId, languageId);
        }
        /*binding.search.setOnClickListener(v -> {
            mViewModel.
        });*/
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.next_page, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.more:
                if (!SearchFragment.topic){
                    page = page + 1;
                    mViewModel.getEvent(page, binding.progressBar, url, SearchFragment.eventId, languageId);
                }
                break;
            case R.id.previous:
                if (page > 1){
                    if (!SearchFragment.topic){
                        page = page - 1;
                        mViewModel.getEvent(page, binding.progressBar, url, SearchFragment.eventId, languageId);
                    }

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void download(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                startDownloading();
            }
        } else {
            startDownloading();
        }
    }

    private void startDownloading() {
        //String urlDownload = list.get(podcastService.position).getUrlHigh();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("DCLM APP");
        request.setDescription(titleDownload);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, titleDownload);
        DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading();
            } else {
                Toast.makeText(getActivity(), "Permission Refused...", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onRecieved(List<Search> searches) {
        this.searches = searches;
        binding.progressBar.setVisibility(View.GONE);
        adapter.setList(searches);
        binding.recyclerView.setAdapter(adapter);
        adapter.setAudioClick( (view, position) ->{

        }, (view, position) ->{
            urlDownload = searches.get(position).getUrlHigh();
            titleDownload =  searches.get(position).getTitle();
            download();
        }, (view, position) ->{
            urlDownload = searches.get(position).getUrlLow();
            titleDownload =  searches.get(position).getTitle();
            download();
        }, (view, position) ->{
            urlDownload = searches.get(position).getAudioUrl();
            titleDownload =  searches.get(position).getTitle();
            download();
        }, (view, position) ->{
            onDemands = new ArrayList<>();
            onDemands.add(new OnDemand(searches.get(position).getTitle(), searches.get(position).getSubtitle(), searches.get(position).getUrlHigh(),
                    searches.get(position).getUrlLow(), searches.get(position).getAudioUrl(), " "));
            SearchResultFragmentDirections.ActionSearchResultFragmentToPodcastPlayFragment action = SearchResultFragmentDirections.actionSearchResultFragmentToPodcastPlayFragment("search");
            Navigation.findNavController(view).navigate(action);
        }, (view, position) ->{
            onDemands = new ArrayList<>();
            onDemands.add(new OnDemand(searches.get(position).getTitle(), searches.get(position).getSubtitle(), searches.get(position).getUrlHigh(),
                    searches.get(position).getUrlLow(), searches.get(position).getAudioUrl(), " "));
            SearchResultFragmentDirections.ActionSearchResultFragmentToVideoPlayFragment action = SearchResultFragmentDirections.actionSearchResultFragmentToVideoPlayFragment("search");
            Navigation.findNavController(view).navigate(action);
        }, (view, position) ->{
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle(R.string.onDemand)
                    .setText("mp3: " + searches.get(position).getAudioUrl() + "\n720p: " + searches.get(position).getUrlHigh()  + "\n480p: " + searches.get(position).getUrlLow())
                    .setSubject(searches.get(position).getTitle())
                    .startChooser();
        });
    }

    @Override
    public void onError() {

    }
}
