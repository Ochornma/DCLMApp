package org.dclm.live.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;

import org.dclm.live.R;
import org.dclm.live.databinding.FragmentHomeBinding;
import org.dclm.live.ui.listen.RadioFragment;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String PREFRENCES = "org.dclm.radio";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.listen.setOnClickListener( v ->{
            RadioFragment.whereFrom = true;
            SharedPreferences  prefs = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
            HomeFragmentDirections.ActionHomeFragmentToRadioFragment action = HomeFragmentDirections.actionHomeFragmentToRadioFragment( prefs.getString("link", getActivity().getResources().getString(R.string.radio_link)), prefs.getString("url", getActivity().getResources().getString(R.string.dclm_api)));
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getBottom(binding.scrollView, binding.moreLayout);
        binding.setViewmodel(homeViewModel);

    }
}
