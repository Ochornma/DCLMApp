package org.dclm.live.ui.blog;

import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.BlogItemBinding;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogHolder> {

    private List<Blog> blogs = new ArrayList<>();
    private Onclick onclick;


    public BlogAdapter(Onclick onclick){
        this.onclick = onclick;
    }
    @NonNull
    @Override
    public BlogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BlogItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.blog_item, parent, false);
        return new BlogHolder(binding);
    }

    public void setNotes(List<Blog> blogs){
       this.blogs = blogs;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull BlogHolder holder, int position) {
        holder.binding.setBlog(blogs.get(position));
        Blog blog = blogs.get(position);
        holder.binding.image.setImageResource(R.drawable.blog_one);
        holder.binding.blogContainerOne.setOnClickListener(v ->{
            onclick.click(holder.binding.blogContainerOne, position);
        });
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public class BlogHolder extends RecyclerView.ViewHolder {

        private BlogItemBinding binding;
        public BlogHolder(@NonNull BlogItemBinding blogItemBinding) {
            super(blogItemBinding.getRoot());
            this.binding = blogItemBinding;
        }
    }

    interface Onclick{
        void click(View view, int position);
    }
}
