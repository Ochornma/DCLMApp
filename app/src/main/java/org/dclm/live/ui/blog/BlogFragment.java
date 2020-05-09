package org.dclm.live.ui.blog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.dclm.live.R;
import org.dclm.live.databinding.BlogFragmentBinding;
import org.dclm.live.util.NetworkCall;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BlogFragment extends Fragment implements BlogAdapter.Onclick, NetworkCall {

    private BlogViewModel mViewModel;
    private RequestQueue mQueue;
    private BlogFragmentBinding blogFragmentBinding;
    private List<Blog> mblogs;
    private String url = "https://dclm.org/wp-json/wp/v2/posts?page=";
    private int page = 1;
    private BlogAdapter adapter;
    private static final String PREFRENCES = "org.dclm.radio";
    private SharedPreferences sharedPreferences, prefs;
    private SharedPreferences.Editor editor;
    private int count = 0;

    public static BlogFragment newInstance() {
        return new BlogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i("1", "create");

        blogFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.blog_fragment, container, false);
        // blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);

        blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);
        adapter = new BlogAdapter(this);

        blogFragmentBinding.recyclerView.setHasFixedSize(true);
        blogFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        blogFragmentBinding.recyclerView.setAdapter(adapter);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        return blogFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("1", "activity");

        BlogFactory blogFactory = new BlogFactory(getActivity(), getActivity().getApplication(), this);
        mViewModel = new ViewModelProvider(this, blogFactory).get(BlogViewModel.class);
       mViewModel.getAllNotes().observe(getViewLifecycleOwner(), blogs -> {
            adapter.setNotes(blogs);
           mblogs = blogs;
        });
        mViewModel.jsonParse2(page);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.next_page, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                page = page + 1;
                blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);
                //jsonParse();
                mViewModel.jsonParse2(page);
                break;
            case R.id.previous:
                if (page > 1) {
                    page = page - 1;
                    blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);
                    //jsonParse();
                    mViewModel.jsonParse2(page);
                }
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("1", "start");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("1", "start");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("1", "pause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("1", "stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("1", "destroy");
    }

   /* public void jsonParse() {

        sharedPreferences = this.getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        prefs = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        boolean count = prefs.getBoolean("INSERT", false);
        blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);

        if (getActivity() != null) {
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + String.valueOf(page), null,
                    response -> {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                JSONObject jsonObject = object.getJSONObject("title");
                                String title = jsonObject.getString("rendered");
                                String time = object.getString("date_gmt");
                                JSONObject jsonObject1 = object.getJSONObject("content");
                                String content = jsonObject1.getString("rendered");
                                // blogs.add(new Blog(title, time, content));

                                if (!count) {
                                    mViewModel.insert(new Blog(title, time, content));
                                } else {
                                    Blog blog = new Blog(title, time, content);
                                    blog.setId(i + 1);
                                    mViewModel.update(blog);
                                }
                                editor.putBoolean("INSERT", true);
                                editor.apply();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                error.printStackTrace();
                blogFragmentBinding.prrogressBar.setVisibility(View.VISIBLE);
                editor.putBoolean("INSERT", false);
                editor.apply();
                       *//* title.setText(R.string.message);
                        preacher.setText(R.string.ministering);
                        upNext.setText(" ");*//*
            });
            mQueue.add(request);
        }


    }*/

    @Override
    public void click(View view, int position) {
        Blog blog = mblogs.get(position);
        //Log.i("blog", blog.getBody());
        BlogFragmentDirections.ActionBlogFragmentToBlogDetailFragment action = BlogFragmentDirections.actionBlogFragmentToBlogDetailFragment(blog.getBody());
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void blogRecieved(List<Blog> blog) {
        sharedPreferences = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        prefs = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        boolean count = prefs.getBoolean("INSERT", false);
        blogFragmentBinding.prrogressBar.setVisibility(View.GONE);

        for (int i = 0; i < blog.size(); i++) {
            if (!count) {
                mViewModel.insert(new Blog(blog.get(i).getTitle(), blog.get(i).getDate(), blog.get(i).getBody()));
               // Toast.makeText(getActivity(), "Error Code: insert" , Toast.LENGTH_SHORT).show();
            } else {
                Blog blog2 = new Blog(blog.get(i).getTitle(), blog.get(i).getDate(), blog.get(i).getBody());
                blog2.setId(i + 1);
                mViewModel.update(blog2);
               // Toast.makeText(getActivity(), "Error Code: update" , Toast.LENGTH_SHORT).show();
            }

        }
        editor.putBoolean("INSERT", true);
        editor.apply();

    }

    @Override
    public void dataError(int error) {
        //Toast.makeText(getActivity(), "Error Code: " + error, Toast.LENGTH_SHORT).show();
        blogFragmentBinding.prrogressBar.setVisibility(View.GONE);
    }
}
