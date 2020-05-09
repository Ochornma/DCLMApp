package org.dclm.live.ui.ondemand.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.SearchItemBinding;

import java.util.List;

public class SearchEventAdapter extends RecyclerView.Adapter<SearchEventAdapter.SearchEventHolder> {

    private List<Search> list;
    private itemClick click;

    public SearchEventAdapter(){

    }
    @NonNull
    @Override
    public SearchEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_item, parent, false);
        return new SearchEventHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEventHolder holder, int position) {
        Search search = list.get(position);
        holder.binding.setSearch(search);
        holder.binding.display.setVisibility(View.GONE);
        holder.itemView.setOnClickListener( v -> {
            click.click(holder.itemView, position);
        });
    }

    public void setItemClick(itemClick click){
      this.click = click;
    }

    public void setList(List<Search> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SearchEventHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        SearchEventHolder(@NonNull SearchItemBinding mbinding) {
            super(mbinding.getRoot());
            binding = mbinding;

        }
    }

    interface itemClick{
        void click(View view, int position);
    }
}
