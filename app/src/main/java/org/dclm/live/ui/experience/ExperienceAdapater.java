package org.dclm.live.ui.experience;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.TestimonyItemBinding;

import java.util.List;

public class ExperienceAdapater extends RecyclerView.Adapter<ExperienceAdapater.ExperienceHolder> {

    private List<Testimony> testimonies;
    @NonNull
    @Override
    public ExperienceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TestimonyItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.testimony_item, parent, false);
        return new ExperienceHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceHolder holder, int position) {
        Testimony testimony = testimonies.get(position);
        holder.binding.setData(testimony);
    }

    @Override
    public int getItemCount() {
        return testimonies.size();
    }

    public void setTestimonies(List<Testimony> testimonies){
        this.testimonies = testimonies;
    }

    public static class ExperienceHolder extends RecyclerView.ViewHolder {
        private TestimonyItemBinding binding;
        public ExperienceHolder(@NonNull TestimonyItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
