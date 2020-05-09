package org.dclm.live.ui.jotter;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.ViewNoteFragmentBinding;

public class ViewNoteFragment extends Fragment {

    private ViewNoteViewModel mViewModel;
    private ViewNoteFragmentBinding binding;

    public static ViewNoteFragment newInstance() {
        return new ViewNoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.view_note_fragment, container, false);
        String topic = ViewNoteFragmentArgs.fromBundle(getArguments()).getTopic();
        String ref = ViewNoteFragmentArgs.fromBundle(getArguments()).getReference();
        String point1 = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint1();
        String point2 = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint2();
        String point3 = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint3();
        String point4 = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint4();
        String point1ref = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint1ref();
        String point2ref = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint2ref();
        String point3ref = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint3ref();
        String point4ref = ViewNoteFragmentArgs.fromBundle(getArguments()).getPoint4ref();
        String date = ViewNoteFragmentArgs.fromBundle(getArguments()).getDate();
        String id = ViewNoteFragmentArgs.fromBundle(getArguments()).getId();
        String speaker = ViewNoteFragmentArgs.fromBundle(getArguments()).getSpeaker();
        String decision = ViewNoteFragmentArgs.fromBundle(getArguments()).getCommitment();
        String service = ViewNoteFragmentArgs.fromBundle(getArguments()).getService();

        String body = ref;
        if (!speaker.trim().isEmpty()){
            body = body + "\n" + speaker;
        }
        if (!service.trim().isEmpty()){
            body = body + "\n" + service;
        }
        if (!speaker.trim().isEmpty()){
            body = body + "\n" + speaker;
        }
        if (!point1.trim().isEmpty()){
            body = body + "\n" + point1;
        }
        if (!point1ref.trim().isEmpty()){
            body = body + "\n" + point1ref;
        }
        if (!point2.trim().isEmpty()){
            body = body + "\n" + point2;
        }
        if (!point2ref.trim().isEmpty()){
            body = body + "\n" + point2ref;
        }
        if (!point3.trim().isEmpty()){
            body = body + "\n" + point3;
        }
        if (!point3ref.trim().isEmpty()){
            body = body + "\n" + point3ref;
        }
        if (!point4.trim().isEmpty()){
            body = body + "\n" + point4;
        }
        if (!point4ref.trim().isEmpty()){
            body = body + "\n" + point4ref;
        }
        if (!decision.trim().isEmpty()){
            body = body + "\n" + decision;
        }

        binding.body.setText(body);
        binding.topic.setText(topic);
        binding.edit.setOnClickListener(v -> {
            ViewNoteFragmentDirections.ActionViewNoteFragmentToAddNoteFragment action =
                    ViewNoteFragmentDirections.actionViewNoteFragmentToAddNoteFragment(id, topic, date, speaker, ref, point1,
                            point2, point3, point4, decision, point1ref, point2ref, point3ref, point4ref, service);
            Navigation.findNavController(v).navigate(action);
        });

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewNoteViewModel.class);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.share:
                ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle(R.string.share)
                        .setText(binding.body.getText().toString())
                        .setSubject(binding.topic.getText().toString())
                        .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}
