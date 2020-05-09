package org.dclm.live.ui.notification;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.NotificationFragmentBinding;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    private NotificationFragmentBinding binding;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.notification_fragment, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("firebase", Context.MODE_PRIVATE);
        String event = sharedPreferences.getString("title", "DCLM");
        String description = sharedPreferences.getString("message", "Achieving Heaven's Goal");
        binding.event.setText(event);
        binding.description.setText(description);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        // TODO: Use the ViewModel
    }

}
