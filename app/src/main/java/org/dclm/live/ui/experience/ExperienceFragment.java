package org.dclm.live.ui.experience;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.FragmentExperienceBinding;
import org.dclm.live.util.TestimonyRecieved;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperienceFragment extends Fragment implements TestimonyRecieved {

    public ExperienceFragment() {
        // Required empty public constructor
    }

    private ExperienceViewModel viewModel;
    private FragmentExperienceBinding binding;
    private ExperienceAdapater adapater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false);
        adapater = new ExperienceAdapater();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.progressBar.setVisibility(View.VISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ExperienceFactory factory = new ExperienceFactory(getActivity(), this);
        viewModel = new ViewModelProvider(this, factory).get(ExperienceViewModel.class);
        viewModel.parseJson();
        binding.setViewmodel(viewModel);
    }

    @Override
    public void onRecieved(List<Testimony> testimonies) {
        binding.progressBar.setVisibility(View.GONE);
        adapater.setTestimonies(testimonies);
        binding.recyclerView.setAdapter(adapater);
    }
}
