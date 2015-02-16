package com.example.mayur.fragmentprac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayur on 10/2/15.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {
    // describing table schema here
    private static final int DATABASE_VERSION = 2;
    private static final String NOTE_TABLE_NAME = "damnnotes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE = "note";
    private static final String DATABASE_NAME = "shitnotes.db";
    private static final String NOTE_TABLE_CREATE =
            "CREATE TABLE "
                    + NOTE_TABLE_NAME
                    + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_NOTE + " text not null"
                    + ");";

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTE_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w("DB::WARN ~~", "Upgrade version shit happened");
    }

    /**
     * Created by mayur on 11/2/15.
     */

    // An object Note with basic object
    // manipulation api
    public static class Note {
        private long id;
        private String note;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return note;
        }
    }

    /**
     * Created by mayur on 11/2/15.
     */

    // Notes DB crud api
    // Lack of ORM n this shit happens
    public static class NotesDBApi {
        private SQLiteDatabase database;
        private NoteOpenHelper notedbhelper;
        private String[] allColumns = {COLUMN_ID,
                COLUMN_NOTE};

        public NotesDBApi(Context context) {
            notedbhelper = new NoteOpenHelper(context);
        }

        public void open() {
            database = notedbhelper.getWritableDatabase();
        }

        public void close() {
            notedbhelper.close();
        }

        public Note create(String note) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE, note);

            long insertID = database.insert(NoteOpenHelper.NOTE_TABLE_NAME, null, values);
            Cursor cursor = database.query(NoteOpenHelper.NOTE_TABLE_NAME, allColumns,
                    NoteOpenHelper.COLUMN_ID + " = " + insertID, null, null, null, null);

            cursor.moveToFirst();
            Note newNote = cursorToNote(cursor);
            cursor.close();
            return newNote;
        }

        // Add a delete method

        //update method
        public Note update(long noteId, String updated_note) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE, updated_note);

            Cursor cursor = database.query(NoteOpenHelper.NOTE_TABLE_NAME, allColumns,
                    NoteOpenHelper.COLUMN_ID + " = " + noteId, null, null, null, null);
            database.update(NoteOpenHelper.NOTE_TABLE_NAME, values, "_id = ?", new String[]{ String.valueOf(noteId)});

            cursor.moveToFirst();
            Note newNote = cursorToNote(cursor);
            cursor.close();
            return newNote;
        }

        public void delete(Note note) {
            long id = note.getId();
            database.delete(NoteOpenHelper.NOTE_TABLE_NAME, NoteOpenHelper.COLUMN_ID +
            " = " + id, null);
        }

        public List<Note> getAllNotes() {
            List<Note> notes = new ArrayList<Note>();
            Cursor cursor= database.query(NoteOpenHelper.NOTE_TABLE_NAME, allColumns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Note note = cursorToNote(cursor);
                notes.add(note);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return notes;
        }

        private Note cursorToNote(Cursor cursor) {
            Note note = new Note();
            note.setId(cursor.getLong(0));
            note.setNote(cursor.getString(1));
            return note;
        }
























    }
}
