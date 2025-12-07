package com.example.aplicacionactividad2.controller;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.aplicacionactividad2.model.Category;
import com.example.aplicacionactividad2.model.CategoryWithNotes;
import com.example.aplicacionactividad2.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository repository;
    private final LiveData<List<CategoryWithNotes>> allCategoriesWithNotes;
    private final LiveData<List<Category>> allCategories;

    public NoteViewModel(Application application) {
        super(application);
        repository = new NoteRepository(application);
        allCategoriesWithNotes = repository.getAllCategoriesWithNotes();
        allCategories = repository.getAllCategories();
    }

    // LiveData para la Interfaz Principal (Agrupación 1:N)
    public LiveData<List<CategoryWithNotes>> getAllCategoriesWithNotes() {
        return allCategoriesWithNotes;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    // Métodos CRUD para Activities
    public void insertNote(Note note) {
        repository.insertNote(note);
    }

    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }

    // Método para la Consulta Avanzada de Búsqueda
    public LiveData<List<Note>> searchNotes(String query) {
        return repository.searchNotes(query);
    }
}