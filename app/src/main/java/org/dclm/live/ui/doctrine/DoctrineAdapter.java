package org.dclm.live.ui.doctrine;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.DoctrineItemBinding;

public class DoctrineAdapter extends RecyclerView.Adapter<DoctrineAdapter.DoctrineViewHolder> {

    private String topic[];
    private String doctrine[];
    private String body[];
    private ClickListener clickListener;
    private Context context;



    public DoctrineAdapter(String[] topic, String[] doctrine, String[] body, ClickListener clickListener, Context context) {
        this.topic = topic;
        this.doctrine = doctrine;
        this.body = body;
        this.context = context;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public DoctrineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DoctrineItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.doctrine_item, parent, false);
        return new DoctrineViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull DoctrineViewHolder holder, int position) {
        String doctrineNumber = context.getResources().getString(R.string.doctrine) + " " +  String.valueOf(position);
        holder.binding.setHeading(topic[position]);
        if (position % 2 == 0){
            holder.binding.image.setImageResource(R.drawable.bible);
        } else {
            //holder.binding.image.setBackgroundColor(Color.parseColor("#000000"));
            holder.binding.image.setImageResource(R.drawable.nlogo);
        }

        if (position == 0){
            holder.binding.doctrineNumber.setText( " ");
        } else {
            holder.binding.doctrineNumber.setText( doctrineNumber);
        }
        holder.binding.doctrineContainer.setOnClickListener(v -> {
            clickListener.onClicked(holder.binding.doctrineContainer, position);
        });
    }

    @Override
    public int getItemCount() {
        return topic.length;
    }

    public class DoctrineViewHolder extends RecyclerView.ViewHolder {
        private DoctrineItemBinding binding;

        public DoctrineViewHolder(@NonNull DoctrineItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    interface ClickListener{
        void onClicked(View view, int mposition);
    }
}
