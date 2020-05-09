package org.dclm.live.ui.ondemand.audio;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.PodcastFragmentBinding;

/*import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;*/

public class PodcastFragment extends Fragment {

    private PodcastViewModel mViewModel;
    private PodcastFragmentBinding binding;


    public static PodcastFragment newInstance() {
        return new PodcastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.podcast_fragment, container, false);

        binding.leader.setOnClickListener( v -> {
           navigate(v, "leader");
        });
        binding.study.setOnClickListener( v -> {
            navigate(v, "study");
        });
        binding.sunday.setOnClickListener( v -> {
            navigate(v, "sunday");
        });
        binding.workers.setOnClickListener( v -> {
            navigate(v, "worker");
        });
        binding.power.setOnClickListener( v -> {
            navigate(v, "power");
        });
        binding.special.setOnClickListener( v -> {
            navigate(v, "special");
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PodcastViewModel.class);
       mViewModel.getBottom(binding.scrollView, binding.moreLayout);
    }

private void navigate(View view, String service){
    /*PodcastFragmentDirections.ActionPodcastFragmentToPodCastDisplayFragment action =
            PodcastFragmentDirections.actionPodcastFragmentToPodCastDisplayFragment(service);
    Navigation.findNavController(view).navigate(action);*/
}

}
