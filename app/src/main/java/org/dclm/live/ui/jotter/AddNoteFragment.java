package org.dclm.live.ui.jotter;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.AddNoteFragmentBinding;
import org.dclm.live.ui.jotter.db.AddNoteFactory;
import org.dclm.live.ui.jotter.db.Note;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNoteFragment extends Fragment {

    private AddNoteViewModel mViewModel;
    private AddNoteFragmentBinding binding;
    private String  topic, ref, point1, point2, point3, point4, point1ref, point2ref, point3ref, point4ref, date, id, service, speaker, decision;

    public static AddNoteFragment newInstance() {
        return new AddNoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false);
         topic = AddNoteFragmentArgs.fromBundle(getArguments()).getTopic();
         ref = AddNoteFragmentArgs.fromBundle(getArguments()).getReference();
         point1 = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint1();
         point2 = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint2();
         point3 = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint3();
         point4 = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint4();
         point1ref = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint1ref();
         point2ref = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint2ref();
         point3ref = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint3ref();
         point4ref = AddNoteFragmentArgs.fromBundle(getArguments()).getPoint4ref();
         date = AddNoteFragmentArgs.fromBundle(getArguments()).getDate();
         id = AddNoteFragmentArgs.fromBundle(getArguments()).getId();
         speaker = AddNoteFragmentArgs.fromBundle(getArguments()).getSpeaker();
         decision = AddNoteFragmentArgs.fromBundle(getArguments()).getCommitment();
         service = AddNoteFragmentArgs.fromBundle(getArguments()).getService();

        if (id != null){
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
        }
        
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AddNoteFactory factory = new AddNoteFactory(getActivity().getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(AddNoteViewModel.class);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                if(id != null){
                    if(!binding.body.getText().toString().trim().isEmpty() ||  !binding.topic.getText().toString().trim().isEmpty()) {
                        Note note = new Note(date, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                                "", "", "", "","");
                        note.setId(Integer.parseInt(id));
                        mViewModel.update(note);
                    } else if(binding.body.getText().toString().trim().isEmpty() && binding.topic.getText().toString().trim().isEmpty()) {
                        Note note = new Note(date, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                                "", "", "", "","");
                        note.setId(Integer.parseInt(id));
                        mViewModel.delete(note);
                    }
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addNoteFragment_to_jotterFragment);
                    } else {
                    Calendar calendar = Calendar.getInstance();
                   String date1 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    Note note = new Note(date1, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                            "", "", "", "","");
                    if (!binding.topic.getText().toString().trim().isEmpty() || !binding.body.getText().toString().trim().isEmpty()){
                        mViewModel.insert(note);
                    }
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addNoteFragment_to_jotterFragment);

                }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {

        if(!binding.body.getText().toString().trim().isEmpty() ||  !binding.topic.getText().toString().trim().isEmpty()) {
            if (id == null){
                Calendar calendar = Calendar.getInstance();
                String date1 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                Note note = new Note(date1, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                        "", "", "", "", "");
                mViewModel.insert(note);
            } else {
                Note note = new Note(date, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                        "", "", "", "","");
                note.setId(Integer.parseInt(id));
                mViewModel.update(note);
            }

        } else if(binding.body.getText().toString().trim().isEmpty() && binding.topic.getText().toString().trim().isEmpty()) {
            if (id != null) {
                Note note = new Note(date, "", "", binding.topic.getText().toString(), binding.body.getText().toString(), "", "", "", "",
                        "", "", "", "", "");
                note.setId(Integer.parseInt(id));
                mViewModel.delete(note);
            }
        }
        super.onDestroy();
    }
}
