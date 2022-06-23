package com.example.note.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String title;
    String description;
    String time;
    String NoteID;

    public Note() {
    }

    public Note(String title, String description, String time, String noteID) {
        this.title = title;
        this.description = description;
        this.time = time;
        NoteID = noteID;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        time = in.readString();
        NoteID = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoteID() {
        return NoteID;
    }

    public void setNoteID(String noteID) {
        NoteID = noteID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(time);
        dest.writeString(NoteID);
    }
}
