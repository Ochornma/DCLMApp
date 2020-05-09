package org.dclm.live.ui.jotter;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import org.dclm.live.R;
import org.dclm.live.databinding.JotterFragmentBinding;
import org.dclm.live.ui.jotter.db.Note;
import org.dclm.live.ui.jotter.db.NoteFactory;

import java.util.List;

public class JotterFragment extends Fragment implements SearchView.OnQueryTextListener, NoteAdapter.Onclick, NoteAdapter.OnDeleteClick {

    private NoteViewModel mViewModel;
    private JotterFragmentBinding binding;
    private NoteAdapter noteAdapter;
    private List<Note> mnotes;

    public static JotterFragment newInstance() {
        return new JotterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.jotter_fragment, container, false);
        noteAdapter = new NoteAdapter(this, this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(noteAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.recycler);

        binding.newNote.setOnClickListener( v ->{
            JotterFragmentDirections.ActionJotterFragmentToAddNoteFragment action =
                    JotterFragmentDirections.actionJotterFragmentToAddNoteFragment(null, null, null, null, null,
                            null, null, null, null, null, null,
                    null, null, null, null);
            Navigation.findNavController(v).navigate(action);
        });

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NoteFactory noteFactory = new NoteFactory(getActivity().getApplication());
        mViewModel = new ViewModelProvider(this, noteFactory).get(NoteViewModel.class);
        mViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            mnotes = notes;
            noteAdapter.setNotes(notes);
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        noteAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void click(View view, int position) {
        Note note = mnotes.get(position);
        JotterFragmentDirections.ActionJotterFragmentToViewNoteFragment action =
                JotterFragmentDirections.actionJotterFragmentToViewNoteFragment(String.valueOf(note.getId()), note.getTopic(), note.getDate(), note.getSpeaker(),
                        note.getDescription(), note.getPoint1(), note.getPoint2(), note.getPoint3(), note.getPoint4(),
                        note.getDecision(), note.getPoint1_description(), note.getPoint2_description(), note.getPoint3_description(), note.getPoint4_description(), note.getService());
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onDeleteClicked(Note note) {
        mViewModel.delete(note);
    }


}
