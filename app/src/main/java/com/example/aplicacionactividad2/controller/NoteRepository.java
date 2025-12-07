package com.example.aplicacionactividad2.controller;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.aplicacionactividad2.model.AppDatabase;
import com.example.aplicacionactividad2.model.Category;
import com.example.aplicacionactividad2.model.CategoryWithNotes;
import com.example.aplicacionactividad2.model.Note;
import com.example.aplicacionactividad2.model.NoteDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final NoteDao noteDao;
    private final LiveData<List<CategoryWithNotes>> allCategoriesWithNotes;
    private final LiveData<List<Category>> allCategories;

    // Ejecutor para asegurar que Room corre en un hilo de fondo (off the main thread)
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allCategoriesWithNotes = noteDao.getAllCategoriesWithNotes();
        allCategories = noteDao.getAllCategories();
    }

    // Métodos para la Vista Principal
    public LiveData<List<CategoryWithNotes>> getAllCategoriesWithNotes() {
        return allCategoriesWithNotes;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    // Operaciones CRUD
    public void insertNote(Note note) {
        databaseWriteExecutor.execute(() -> noteDao.insertNote(note));
    }

    public void insertCategory(Category category) {
        databaseWriteExecutor.execute(() -> noteDao.insertCategory(category));
    }

    // Consulta Avanzada de Búsqueda
    public LiveData<List<Note>> searchNotes(String query) {
        String searchQuery = "%" + query + "%"; // Formato para la consulta LIKE
        return noteDao.searchNotesByText(searchQuery);
    }
}