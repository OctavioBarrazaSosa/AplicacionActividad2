package com.example.aplicacionactividad2.model;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class CategoryWithNotes {
    @Embedded
    public Category category;

    @Relation(
            parentColumn = "category_id",
            entityColumn = "category_id"
    )
    public List<Note> notes;

    // Getters y Setters
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
}