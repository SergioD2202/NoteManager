package com.SD.notepad;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.MyViewHolder>{

    //TODO:adapt the last modified view

private List <NotesBuilder> notesList;
private OnNoteListener mOnNoteListener;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView title, content;
    OnNoteListener onNoteListener;

    public MyViewHolder(View view, OnNoteListener onNoteListener) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        content = (TextView) view.findViewById(R.id.content);
        this.onNoteListener = onNoteListener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    onNoteListener.onNoteClick(getAdapterPosition());
    }
}

    public NotesAdapter(List <NotesBuilder> notesList, OnNoteListener onNoteListener) {
        this.notesList = notesList;
        this.mOnNoteListener = onNoteListener;
    }

    public void setNotesList(List<NotesBuilder> notesList) {
    this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotesBuilder note = notesList.get(position);
        String points="...";
        holder.title.setText(note.getTitle());
        if (note.getContent().length()<=50) holder.content.setText(note.getContent());
        else holder.content.setText(note.getContent().substring(0,50)+points);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
