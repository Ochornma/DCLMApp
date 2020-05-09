package org.dclm.live.ui.ondemand.audio;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.lifecycle.ViewModel;

public class PodcastViewModel extends ViewModel {
    public void getBottom(ScrollView scrollView, LinearLayout layout){
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getChildAt(0).getBottom() <=
                        (scrollView.getHeight() + scrollView.getScrollY())){
                    layout.setVisibility(View.GONE);
                    Log.i("end", "end");
                } else {
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
