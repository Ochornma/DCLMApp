package org.dclm.live.ui.doctrine;

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
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.DoctrineFragmentBinding;

public class DoctrineFragment extends Fragment implements DoctrineAdapter.ClickListener {

    private DoctrineViewModel mViewModel;
    private String[] topicText;
    private String[] doctrineText;
    private String[] bodyText;
    private DoctrineFragmentBinding binding;


    public static DoctrineFragment newInstance() {
        return new DoctrineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.doctrine_fragment, container, false);

        topicText = getActivity().getResources().getStringArray(R.array.heading);
        doctrineText = getActivity().getResources().getStringArray(R.array.doctrine);
        bodyText = getActivity().getResources().getStringArray(R.array.body);
        DoctrineAdapter adapter = new DoctrineAdapter(topicText,doctrineText, bodyText, this, getActivity());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DoctrineViewModel.class);
    }

    @Override
    public void onClicked(View view, int mposition) {
        Log.i("number", String.valueOf(mposition));

        DoctrineFragmentDirections.ActionDoctrineFragmentToDoctrineDetailFragment action = DoctrineFragmentDirections.actionDoctrineFragmentToDoctrineDetailFragment(topicText[mposition]
        , doctrineText[mposition], bodyText[mposition]);
        Navigation.findNavController(view).navigate(action);
    }
}
