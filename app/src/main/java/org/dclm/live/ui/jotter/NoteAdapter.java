package org.dclm.live.ui.jotter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.dclm.live.R;
import org.dclm.live.databinding.NoteItemBinding;
import org.dclm.live.ui.jotter.db.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {
    private List<Note> notes = new ArrayList<>();
    private List<Note> filteredNote = new ArrayList<>();
    private OnDeleteClick onDeleteClick;
    private Onclick onclick;

    public NoteAdapter(OnDeleteClick deleteClick, Onclick onclick){
        this.onclick = onclick;
        this.onDeleteClick = deleteClick;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NoteItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.note_item, parent, false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.mbinding.setNote(notes.get(position));
        if (!notes.get(position).getTopic().trim().isEmpty()){
            holder.mbinding.topic.setText(notes.get(position).getTopic().trim());
        }else {
            holder.mbinding.topic.setText(notes.get(position).getDescription().trim());
        }
        holder.mbinding.item.setOnClickListener(v -> {
            onclick.click(holder.mbinding.item, position);
        });

        holder.mbinding.deleteOne.setOnClickListener( v ->{
            onDeleteClick.onDeleteClicked(notes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }



    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding mbinding;
        public NoteViewHolder(@NonNull NoteItemBinding binding) {
            super(binding.getRoot());
            this.mbinding = binding;
        }
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        filteredNote.addAll(notes);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().trim().toLowerCase();
                notes.clear();

                if (charString.isEmpty()) {
                    notes.addAll(filteredNote);
                } else {
                    for (Note row : filteredNote) {

                        if (row.getTopic().toLowerCase().trim().contains(charString.toLowerCase())) {
                            notes.add(row);
                        }
                    }

                }

                FilterResults hasilFilter = new FilterResults();
                hasilFilter.values = notes;
                return hasilFilter;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    interface Onclick{
        void click(View view, int position);
    }

    public interface OnDeleteClick{
        void onDeleteClicked(Note note);
    }
}
