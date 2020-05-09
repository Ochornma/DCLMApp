package org.dclm.live.ui.ondemand.audio;

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

public class PodcastDisplayAdapter extends RecyclerView.Adapter<PodcastDisplayAdapter.PodcastHolder> {

   private List<OnDemand> onDemands;
    private List<OnDemand.CheckState> checkStates;
    private CheckBox checkBox;
    private ContainerClick containerClick;

    public PodcastDisplayAdapter(){

    }


    @NonNull
    @Override
    public PodcastDisplayAdapter.PodcastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.display_item, parent, false);
        return new PodcastHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastDisplayAdapter.PodcastHolder holder, int position) {
        OnDemand onDemand = onDemands.get(position);
        OnDemand.CheckState checkState = checkStates.get(position);
        holder.mbinding.setCheck(checkState);
        holder.mbinding.setOnDemand(onDemand);
        holder.mbinding.image.setImageResource(R.drawable.headphone);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " + podcast.getTitle() + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.mbinding.doctrine.setText(Html.fromHtml("<p align=\"justify\">"+
                    " " +podcast.getTitle() + "</p>"));
        }
*/
        holder.mbinding.container.setOnClickListener( v->{
            containerClick.click(holder.mbinding.container, position);
            //setContainerClick(holder.mbinding.container, position);
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

    public class PodcastHolder extends RecyclerView.ViewHolder {
        private DisplayItemBinding mbinding;
        public PodcastHolder(@NonNull DisplayItemBinding binding) {
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
