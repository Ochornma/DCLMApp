package org.dclm.live.ui.ondemand.audio;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.dclm.live.R;
import org.dclm.live.databinding.PodCastDisplayFragmentBinding;

import org.dclm.live.ui.ondemand.OnDemand;
import org.dclm.live.util.PodcastNetworkCall;

import java.util.ArrayList;
import java.util.List;

public class PodCastDisplayFragment extends Fragment implements PodcastDisplayAdapter.CheckBox, PodcastDisplayAdapter.ContainerClick, PodcastNetworkCall {

    private PodCastDisplayViewModel mViewModel;
    private PodCastDisplayFragmentBinding binding;
    private int page = 1;
    private String url = "https://api.dclmict.org/v1/sermon/data/?page=";
    private List<OnDemand.Category> categories = new ArrayList<>();
    //private int count;
    private List<OnDemand> onDemands = new ArrayList<>();
    public static List<OnDemand> podcasts2 = new ArrayList<>();
    private List<OnDemand.CheckState> checkStates = new ArrayList<>();
    private List<OnDemand.CheckState> checkStates2 = new ArrayList<>();
    private PodcastDisplayAdapter adapter;
    private RequestQueue mQueue;
    private int languageID;
    private boolean menuCheck;


    public static PodCastDisplayFragment newInstance() {
        return new PodCastDisplayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.pod_cast_display_fragment, container, false);

        languageID = Integer.parseInt(getActivity().getResources().getString(R.string.languageID));
        binding.progressBar.setVisibility(View.VISIBLE);

        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        adapter = new PodcastDisplayAdapter();
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        //checkStates2 = checkStates;
       // categoryJson();
        //jsonParse();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //adapter = new PodcastDisplayAdapter(this, this);
        PodcastFactory podcastFactory = new PodcastFactory(getActivity(), this);
        mViewModel = new ViewModelProvider(this, podcastFactory).get(PodCastDisplayViewModel.class);
        mViewModel.getCategory();

    adapter = new PodcastDisplayAdapter();
    }


    @Override
    public void check(View view, int position) {
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
                if (menuCheck){
                    page = page + 1;
                    mViewModel.getPodcast(url, page, languageID, categories);
                }

               // count = 3160 - ((page - 1) * 15) - 1;
                break;
            case R.id.previous:
                if (page > 1){
                    if (menuCheck){
                        page = page - 1;
                        mViewModel.getPodcast(url, page, languageID, categories);
                    }

                }
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void click(View view, int position) {

    }


    @Override
    public void podcastRecieved(List<OnDemand> onDemands, List<OnDemand.CheckState> checkStates) {
        menuCheck = true;
        binding.progressBar.setVisibility(View.GONE);
        adapter.setList(onDemands, checkStates);
        binding.recycler.setAdapter(adapter);
        adapter.setContainerClick((view, position) -> {
            podcasts2 = new ArrayList<>();
            podcasts2.add(new OnDemand(onDemands.get(position).getTitle(), onDemands.get(position).getDate(), onDemands.get(position).getUrlHigh(),
                    onDemands.get(position).getUrlLow(), onDemands.get(position).getUrlAudio(), onDemands.get(position).getCategory()));
            PodCastDisplayFragmentDirections.ActionPodCastDisplayFragmentToPodcastPlayFragment action = PodCastDisplayFragmentDirections.actionPodCastDisplayFragmentToPodcastPlayFragment("start");
            Navigation.findNavController(view).navigate(action);
        });

        binding.playList.setOnClickListener( v ->{
            podcasts2 = new ArrayList<>();
            for (int i = 0; i < checkStates.size(); i++) {
                if (checkStates.get(i).isCheck()){
                    podcasts2.add(new OnDemand(onDemands.get(i).getTitle(), onDemands.get(i).getDate(), onDemands.get(i).getUrlHigh(),
                            onDemands.get(i).getUrlLow(), onDemands.get(i).getUrlAudio(), onDemands.get(i).getCategory()));
                }
            }
            if(podcasts2.size() == 0){
                podcasts2 = onDemands;
            }
            PodCastDisplayFragmentDirections.ActionPodCastDisplayFragmentToPodcastPlayFragment action = PodCastDisplayFragmentDirections.actionPodCastDisplayFragmentToPodcastPlayFragment("start");
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public void categoryRecieved(List<OnDemand.Category> categories) {
        this.categories = categories;
        mViewModel.getPodcast(url, page, languageID, categories);
    }

    @Override
    public void error(boolean menu) {
        binding.progressBar.setVisibility(View.GONE);
        menuCheck = menu;
    }
}
