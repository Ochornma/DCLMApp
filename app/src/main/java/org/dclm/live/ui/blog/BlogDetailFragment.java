package org.dclm.live.ui.blog;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dclm.live.R;
import org.dclm.live.databinding.BlogDetailFragmentBinding;

public class BlogDetailFragment extends Fragment {

    private BlogDetailViewModel mViewModel;
    private BlogDetailFragmentBinding binding;


    public static BlogDetailFragment newInstance() {
        return new BlogDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.blog_detail_fragment, container, false);
        String blog = BlogDetailFragmentArgs.fromBundle(getArguments()).getBody();
       binding.webview.getSettings().setJavaScriptEnabled(true);
       binding.webview.loadDataWithBaseURL(null, blog, "text/html", "UTF-8", null);
       return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BlogDetailViewModel.class);

    }

}
