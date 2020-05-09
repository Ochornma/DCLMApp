package org.dclm.live.ui.ondemand.video;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.DisplayItemBinding;
import org.dclm.live.ui.ondemand.OnDemand;

import java.util.ArrayList;
import java.util.List;


public class VideoPlayAdapter extends RecyclerView.Adapter<VideoPlayAdapter.PlayerHolder> {

    private List<OnDemand> onDemands = new ArrayList<>();
    private Click click;

    public VideoPlayAdapter(List<OnDemand> onDemands, Click click){
        this.onDemands = onDemands;
        this.click = click;

    }

    @NonNull
    @Override
    public VideoPlayAdapter.PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.display_item, parent, false);
        return new PlayerHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPlayAdapter.PlayerHolder holder, int position) {
        OnDemand onDemand = onDemands.get(position);
        holder.mbinding.checkbox.setVisibility(View.GONE);
        holder.mbinding.doctrine.setTextColor(Color.parseColor("#000000"));
        holder.mbinding.doctrineNumber.setTextColor(Color.parseColor("#000000"));
        holder.mbinding.divider.setBackgroundColor(Color.parseColor("#000000"));
        holder.mbinding.image.setImageResource(R.drawable.ic_personal_video);
        holder.mbinding.setOnDemand(onDemand);
        holder.mbinding.setCheck(new OnDemand.CheckState(position,false));
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " + podcast.getTitle() + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " +podcast.getTitle() + "</p>"));
        }*/
        holder.mbinding.container.setOnClickListener( v ->{
            //click.clicked(holder.mbinding.container, position);
        });
    }

    @Override
    public int getItemCount() {
        return onDemands.size();
    }

    static class PlayerHolder extends RecyclerView.ViewHolder {
        private DisplayItemBinding mbinding;

        PlayerHolder(@NonNull DisplayItemBinding binding) {
            super(binding.getRoot());
            this.mbinding = binding;
        }
    }

    interface Click{
        void clicked(View view, int position);
    }
}
