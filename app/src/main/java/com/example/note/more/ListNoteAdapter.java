package com.example.note.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.note.R;
import com.example.note.notes.Note;

import java.util.List;

public class ListNoteAdapter extends ArrayAdapter<Note> {
    public ListNoteAdapter(@NonNull Context context, @NonNull List<Note> noteList) {
        super(context, 0, noteList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_note, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView time = (TextView) convertView.findViewById(R.id.txtTime);
        title.setText(note.getTitle());
        time.setText(note.getTime());
        return convertView;
    }
}
