package com.example.aplicacionactividad2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "notes",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id",
                onDelete = CASCADE
        )
)
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int noteId;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_content")
    private String noteContent;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "category_id", index = true)
    private int categoryId; // Clave foránea

    public Note(String noteTitle, String noteContent, int categoryId) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.categoryId = categoryId;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters y Setters
    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }
    public String getNoteTitle() { return noteTitle; }
    public void setNoteTitle(String noteTitle) { this.noteTitle = noteTitle; }
    public String getNoteContent() { return noteContent; }
    public void setNoteContent(String noteContent) { this.noteContent = noteContent; }
    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}