package com.example.notes.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.Models.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesHandler extends SQLiteHelper{
    private SQLiteHelper dbhelper;

    public NotesHandler(@Nullable Context context) {
        super(context);
        dbhelper = new SQLiteHelper(context);
    }

    public int addNote(Note note){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbhelper.KEY_NAME,note.getTitre());
        values.put(dbhelper.KEY_CONTENT,note.getContenu());
        long insertid = db.insert(dbhelper.TABLE_NOTES,null,values);
        db.close();
        return (int)insertid;
    }

    public Note getNote(int id){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(dbhelper.TABLE_NOTES, new String[] { dbhelper.KEY_ID,
                        dbhelper.KEY_NAME, dbhelper.KEY_CONTENT }, dbhelper.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note= new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return note;
    }
    public List<Note> getAllNotes() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        List<Note> notelist = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM " + dbhelper.TABLE_NOTES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2));
                notelist.add(note);
            } while (cursor.moveToNext());
        }
        return notelist;
    }
    public void deleteNote(Note note) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(dbhelper.TABLE_NOTES, dbhelper.KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }
    public int updateNote(Note note){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbhelper.KEY_NAME,note.getTitre());
        values.put(dbhelper.KEY_CONTENT,note.getContenu());
        long updateId=db.update(dbhelper.TABLE_NOTES,values,
                dbhelper.KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        return (int) updateId;
    }
}
