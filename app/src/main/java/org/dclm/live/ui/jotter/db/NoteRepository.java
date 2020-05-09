package org.dclm.live.ui.jotter.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.dclm.live.ui.blog.Blog;
import org.dclm.live.ui.blog.BlogDAO;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    private BlogDAO blogDAO;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Blog>> allBlogs;

    public NoteRepository(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDAO = noteDataBase.noteDAO();
        blogDAO = noteDataBase.blogDAO();
        allNotes = noteDAO.getAllNotes();
        allBlogs = blogDAO.getAllBlogs();
    }


    public void insert(Note note) {

        new insertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note) {

        new updateNoteAsyncTask(noteDAO).execute(note);
    }

    public void insertBlog(Blog blog) {

        new insertBlogAsyncTask(blogDAO).execute(blog);
    }

    public void updateBlog(Blog blog) {

        new updateBlogAsyncTask(blogDAO).execute(blog);
    }

    public void delete(Note note) {

        new deleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteAll() {

        new deleteAllNoteAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public LiveData<List<Blog>> getAllBlogs(){
        return allBlogs;
    }

    private static class insertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private insertNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class insertBlogAsyncTask extends AsyncTask<Blog, Void, Void> {

        private BlogDAO blogDAO;

        private insertBlogAsyncTask(BlogDAO blogDAO){
            this.blogDAO = blogDAO;
        }

        @Override
        protected Void doInBackground(Blog... blogs) {
            blogDAO.insert(blogs[0]);
            return null;
        }
    }


    private static class updateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDAO noteDAO;

        private updateNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class updateBlogAsyncTask extends AsyncTask<Blog, Void, Void>{

        private BlogDAO blogDAO;

        private updateBlogAsyncTask(BlogDAO blogDAO){
            this.blogDAO =blogDAO;
        }

        @Override
        protected Void doInBackground(Blog... blogs) {
           blogDAO.update(blogs[0]);
            return null;
        }
    }

    private static class deleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDAO noteDAO;

        private deleteNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDAO noteDAO;

        private deleteAllNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNotes();
            return null;
        }
    }
}

