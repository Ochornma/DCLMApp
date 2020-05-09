package org.dclm.live.ui.jotter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.dclm.live.ui.jotter.db.Note;
import org.dclm.live.ui.jotter.db.NoteRepository;

import java.util.List;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void insert(Note note){

        repository.insert(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

}
