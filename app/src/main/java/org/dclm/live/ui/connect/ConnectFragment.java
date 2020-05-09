package org.dclm.live.ui.connect;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.dclm.live.R;
import org.dclm.live.databinding.ConnectFragmentBinding;

public class ConnectFragment extends Fragment {

    private ConnectFragmentBinding binding;
    private String socialMedia;

    public static ConnectFragment newInstance() {
        return new ConnectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.connect_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ConnectFactory connectFactory = new ConnectFactory(getActivity());
        ConnectViewModel mViewModel = new ViewModelProvider(this, connectFactory).get(ConnectViewModel.class);
        binding.setViewmodel(mViewModel);
    }


    }

