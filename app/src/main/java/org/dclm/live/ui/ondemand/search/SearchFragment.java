package org.dclm.live.ui.ondemand.search;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;


import org.dclm.live.R;
import org.dclm.live.databinding.SearchFragmentBinding;
import org.dclm.live.util.SearchNetworkCall;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchNetworkCall {

    private SearchViewModel mViewModel;
    SearchFragmentBinding binding;
    public static boolean topic;
    public static String eventId;
    private int page = 1;
    private RequestQueue mQueue;
    private List<Search> searches;
    private SearchEventAdapter adapter;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        adapter = new SearchEventAdapter();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.searchTopic.setOnClickListener( v -> {
            topic = true;
            Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_searchResultFragment);
        });

        /*binding.searchEvent.setOnClickListener( v -> {
            topic = false;
            jsonParse();
            //Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_searchResultFragment);
        });*/

        return binding.getRoot();
    }

    private void jsonParse() {
        searches = new ArrayList<>();
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.dclmict.org/v1/sermon/event";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = (response.length() - ((page -1) *20)); i > (response.length() -(page * 20)) ; i--) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String id = String.valueOf(jsonObject.getInt("eventId"));
                    String title = jsonObject.getString("eventCode");
                    String subTitle = jsonObject.getString("eventTheme");
                    searches.add(new Search(id, title, subTitle, "", "", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            binding.progressBar.setVisibility(View.INVISIBLE);

            binding.recyclerView.setAdapter(adapter);
        }, error -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
        });
        mQueue.add(jsonArrayRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchFactory searchFactory = new SearchFactory(getActivity(), this);
        mViewModel = new ViewModelProvider(this, searchFactory).get(SearchViewModel.class);
        binding.setPage(page);
        binding.setProgressbar(binding.progressBar);
        binding.setViewmodel(mViewModel);
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
                page = page + 1;
                //jsonParse();
                mViewModel.getEvent(page, binding.progressBar);
                break;
            case R.id.previous:
                if (page > 1){
                    page = page - 1;
                   // jsonParse();
                    mViewModel.getEvent(page, binding.progressBar);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecieved(List<Search> searches) {
        this.searches = searches;
        topic = false;
        binding.progressBar.setVisibility(View.INVISIBLE);
        adapter.setList(searches);
        binding.recyclerView.setAdapter(adapter);
        adapter.setItemClick((view, position) -> {
            topic = false;
            eventId = searches.get(position).getId();
            Log.i("search", searches.get(position).getId());
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultFragment);
        });
    }

    @Override
    public void onError() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}
