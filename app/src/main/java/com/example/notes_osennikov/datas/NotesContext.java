package com.example.notes_osennikov.datas;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.notes_osennikov.domains.models.Note;

import java.util.ArrayList;

public class NotesContext {

    public static ArrayList<Note> AllNotes() {
        ArrayList<Note> allNotes = new ArrayList<>();

        Cursor cursor = DbContext.sqliteDatabase.query(
                "Notes",
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (!cursor.moveToFirst()) {
            cursor.close();
            return allNotes;
        }

        do {
            Note note = new Note();
            note.id = cursor.getInt(0);
            note.title = cursor.getString(1);
            note.text = cursor.getString(2);
            note.date = cursor.getString(3);
            note.color = cursor.getString(4);
            allNotes.add(note);
        } while (cursor.moveToNext());
        cursor.close();
        return allNotes;
    }
    public static void Save(Note note, boolean update) {
        ContentValues CV = new ContentValues();
        CV.put("Title", note.title);
        CV.put("Text", note.text);
        CV.put("Date", note.date);
        CV.put("Color", note.color);
        if (!update) {
            DbContext.sqliteDatabase.insert(
                    "Notes",
                    null,
                    CV
            );
        } else {
            // ОБНОВЛЕНИЕ СУЩЕСТВУЮЩЕЙ ЗАПИСИ
            DbContext.sqliteDatabase.update(
                    "Notes",
                    CV,
                    "Id = ?",
                    new String[]{String.valueOf(note.id)}
            );
        }
    }

    public static void Delete(Note note) {
        DbContext.sqliteDatabase.delete(
                "Notes",
                "Id = ?",
                new String[]{String.valueOf(note.id)}
        );
    }
}
