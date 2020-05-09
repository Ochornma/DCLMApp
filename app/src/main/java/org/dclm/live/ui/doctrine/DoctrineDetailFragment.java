package org.dclm.live.ui.doctrine;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.DoctrineDetailFragmentBinding;

public class DoctrineDetailFragment extends Fragment {

    private DoctrineDetailViewModel mViewModel;
    private DoctrineDetailFragmentBinding binding;

    public static DoctrineDetailFragment newInstance() {
        return new DoctrineDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.doctrine_detail_fragment, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            binding.body.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        Doctrines doctrines = new Doctrines(DoctrineDetailFragmentArgs.fromBundle(getArguments()).getContent(), DoctrineDetailFragmentArgs.fromBundle(getArguments()).getHeading(),
                DoctrineDetailFragmentArgs.fromBundle(getArguments()).getBody());
        binding.setDoctrine(doctrines);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DoctrineDetailViewModel.class);
    }

}
