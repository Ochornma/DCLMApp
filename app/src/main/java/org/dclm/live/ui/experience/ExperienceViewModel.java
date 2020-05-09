package org.dclm.live.ui.experience;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.dclm.live.util.DemandRepository;
import org.dclm.live.util.TestimonyRecieved;

public class ExperienceViewModel extends ViewModel {
   private DemandRepository repository;


    public ExperienceViewModel(Context context, TestimonyRecieved testimonyRecieved){
        repository = new DemandRepository(context, testimonyRecieved);
    }

    public void parseJson(){
        repository.parseTestimony();
    }
}
