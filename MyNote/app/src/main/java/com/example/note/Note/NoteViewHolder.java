package com.example.note.Note;

import android.service.quicksettings.Tile;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    View myView;
    TextView Title, Time;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        myView = itemView;

        Title = myView.findViewById(R.id.viewTitle);
        Time = myView.findViewById(R.id.viewTime);

    }

    public void setTitle(TextView title) {
        Title = title;
    }

    public void setTime(TextView time) {
        Time = time;
    }
}
