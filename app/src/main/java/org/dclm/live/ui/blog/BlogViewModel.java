package org.dclm.live.ui.blog;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import org.dclm.live.ui.jotter.db.NoteRepository;
import org.dclm.live.util.DemandRepository;
import org.dclm.live.util.NetworkCall;


import java.util.List;


public class BlogViewModel extends AndroidViewModel {
    private List<Blog> blogs1;
    //private BlogRepository repository;
    private DemandRepository repository;
    private NoteRepository mrepository;
    private LiveData<List<Blog>> allBlogs;


    public BlogViewModel(Context context, Application application, NetworkCall networkCall) {
        super(application);
        repository = new DemandRepository(context, networkCall);
        mrepository = new NoteRepository(application);
      //blogs1 = repository.theBlogs();
      allBlogs = mrepository.getAllBlogs();


    }

    public LiveData<List<Blog>> getAllNotes() {
        return allBlogs;
    }

    public void update(Blog blog){
        mrepository.updateBlog(blog);
    }

    public void insert(Blog blog){

        mrepository.insertBlog(blog);
    }

    public void jsonParse2(int page){
        repository.getBlogs(page);
    }

}
