package com.example.aplicacionactividad2.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;

@Dao
public interface NoteDao {
    // Operaciones CRUD básicas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    // Consulta Avanzada de Agrupación (Relación 1:N)
    @Transaction
    @Query("SELECT * FROM categories")
    LiveData<List<CategoryWithNotes>> getAllCategoriesWithNotes();

    // Consulta Avanzada de Búsqueda (Uso de LIKE)
    @Query("SELECT * FROM notes WHERE note_title LIKE :searchQuery OR note_content LIKE :searchQuery")
    LiveData<List<Note>> searchNotesByText(String searchQuery);
}