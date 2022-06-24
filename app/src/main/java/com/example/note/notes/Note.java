package com.example.note.notes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String title;
    String description;
    String time;
    String noteID;
    Uri url;

    public Note(String title, String description, String time, String noteID, Uri url) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.noteID = noteID;
        this.url = url;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public Note() {
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        time = in.readString();
        noteID = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(time);
        dest.writeString(noteID);
    }
}
