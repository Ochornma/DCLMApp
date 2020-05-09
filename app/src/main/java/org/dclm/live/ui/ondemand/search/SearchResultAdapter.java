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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultHolder> {

    private List<Search> list;
    private itemClick itemClick;
    private highClick highClick;
    private lowClick lowClick;
    private audioClick audioClick;
    private listenClick listenClick;
    private watchClick watchClick;
    private shareClick shareClick;

    public SearchResultAdapter (){

    }
    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_item, parent, false);
        return new ResultHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        Search search = list.get(position);
        holder.binding.setSearch(search);
        holder.binding.display.setVisibility(View.VISIBLE);
       //holder.binding.low.setT

        holder.itemView.setOnClickListener(v->{
            itemClick.click(holder.itemView, position);
        });
        holder.binding.audio.setOnClickListener(v->{
            audioClick.click(holder.binding.audio, position);
        });
        holder.binding.video.setOnClickListener(v->{
            watchClick.click(holder.binding.video, position);
        });
        holder.binding.high.setOnClickListener(v->{
            highClick.click(holder.binding.high, position);
        });
        holder.binding.low.setOnClickListener(v->{
            lowClick.click(holder.binding.low, position);
        });
        holder.binding.onDemand.setOnClickListener(v->{
            listenClick.click(holder.binding.onDemand, position);
        });

        holder.binding.share.setOnClickListener( v -> {
            shareClick.click(holder.binding.share, position);
        });
    }

    public void setAudioClick( itemClick mitemClick, highClick mhighClick,  lowClick mlowClick, audioClick maudioClick, listenClick mlistenClick, watchClick mwatchClick, shareClick mshareClick){
        this.itemClick = mitemClick;
        this.highClick = mhighClick;
        this.lowClick = mlowClick;
        this.audioClick = maudioClick;
        this.listenClick = mlistenClick;
        this.watchClick = mwatchClick;
        this.shareClick = mshareClick;
    }

    public void setList(List<Search> searches){
        this.list = searches;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ResultHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        ResultHolder(@NonNull SearchItemBinding mbinding) {
            super(mbinding.getRoot());
            binding = mbinding;

        }
    }

    interface itemClick{
        void click(View view, int position);
    }

    interface highClick{
        void click(View view, int position);
    }

    interface lowClick{
        void click(View view, int position);
    }

    interface audioClick{
        void click(View view, int position);
    }

    interface listenClick{
        void click(View view, int position);
    }

    interface watchClick{
        void click(View view, int position);
    }

    interface shareClick{
        void click(View view, int position);
    }
}
