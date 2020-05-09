package org.dclm.live.ui.home;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import org.dclm.live.R;

public class HomeViewModel extends ViewModel {


    public HomeViewModel() {

    }

    public void getBottom(ScrollView scrollView, LinearLayout layout){
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())){
                layout.setVisibility(View.GONE);
                Log.i("end", "end");
            } else {
                layout.setVisibility(View.VISIBLE);
            }
        });
    }

public void navigateListen(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_radioFragment);
    }

    public void navigateWatch(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_watchFragment);
    }

    public void navigateExperience(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_experienceFragment);
    }

    public void navigateJotter(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_jotterFragment);
    }

    public void navigateDoctrine(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_doctrineFragment);
    }




}