package org.dclm.live.ui.ondemand.video;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.DisplayItemBinding;
import org.dclm.live.ui.ondemand.OnDemand;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private List<OnDemand> onDemands;
    private List<OnDemand.CheckState> checkStates;
    private CheckBox checkBox;
    private ContainerClick containerClick;

    public VideoAdapter(CheckBox checkBox, ContainerClick containerClick){
        this.checkBox = checkBox;
        this.containerClick = containerClick;
    }
    public VideoAdapter(){

    }
    @NonNull
    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.display_item, parent, false);
        return new VideoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoHolder holder, int position) {
        OnDemand onDemand = onDemands.get(position);
        OnDemand.CheckState checkState = checkStates.get(position);
        holder.mbinding.setCheck(checkState);
        holder.mbinding.setOnDemand(onDemand);
        holder.mbinding.image.setImageResource(R.drawable.video);
        holder.mbinding.image.setBackgroundColor(Color.parseColor("#e3dede"));
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " + podcast.getTitle() + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " +podcast.getTitle() + "</p>"));
        }*/
        holder.mbinding.checkbox.setOnClickListener( v -> {
            //checkBox.check(holder.mbinding.checkbox, position);
            Log.i("check", String.valueOf(position));
            Log.i("check12", String.valueOf(checkState.getPosition()));
            //notifyDataSetChanged();
        });

        holder.mbinding.container.setOnClickListener( v->{
          containerClick.click(holder.mbinding.container, position);
        });

    }

    public void setContainerClick(ContainerClick containerClick){
        this.containerClick = containerClick;
    }

    public void setList(List<OnDemand> onDemands, List<OnDemand.CheckState> checkStates){
        this.onDemands = onDemands;
        this.checkStates = checkStates;
    }
    @Override
    public int getItemCount() {
        return onDemands.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private DisplayItemBinding mbinding;
        public VideoHolder(@NonNull DisplayItemBinding binding) {
            super(binding.getRoot());
            this.mbinding = binding;
        }
    }

    interface CheckBox{
        void check(View view, int position);
    }

    interface ContainerClick{
        void click(View view, int position);
    }
}
