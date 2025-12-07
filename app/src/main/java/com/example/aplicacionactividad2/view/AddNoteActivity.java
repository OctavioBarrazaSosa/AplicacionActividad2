package com.example.aplicacionactividad2.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.aplicacionactividad2.R;
import com.example.aplicacionactividad2.controller.NoteViewModel;
import com.example.aplicacionactividad2.model.Category;
import com.example.aplicacionactividad2.model.Note;
import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private List<Category> allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Inicialización de la lógica (Controller)
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Inicialización de Vistas (View)
        EditText editTextCategoryName = findViewById(R.id.editTextCategoryName);
        EditText editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        EditText editTextNoteContent = findViewById(R.id.editTextNoteContent);
        Spinner spinnerCategories = findViewById(R.id.spinnerCategories);
        Button buttonAddCategory = findViewById(R.id.buttonAddCategory);
        Button buttonSaveNote = findViewById(R.id.buttonSaveNote);

        // Llenar el Spinner de Categorías
        noteViewModel.getAllCategories().observe(this, categories -> {
            allCategories = categories;
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getCategoryName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, categoryNames);
            spinnerCategories.setAdapter(adapter);
        });

        // Lógica para Agregar una Categoría (Operación CRUD)
        buttonAddCategory.setOnClickListener(v -> {
            String categoryName = editTextCategoryName.getText().toString().trim();
            if (!categoryName.isEmpty()) {
                Category newCategory = new Category(categoryName);
                noteViewModel.insertCategory(newCategory);
                editTextCategoryName.setText("");
                Toast.makeText(this, "Categoría agregada con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El nombre de la categoría no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica para Guardar una Nota (Operación CRUD)
        buttonSaveNote.setOnClickListener(v -> {
            String noteTitle = editTextNoteTitle.getText().toString().trim();
            String noteContent = editTextNoteContent.getText().toString().trim();

            if (noteTitle.isEmpty() || noteContent.isEmpty() || allCategories == null || allCategories.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos y asegúrate de tener categorías creadas.", Toast.LENGTH_LONG).show();
                return;
            }

            int selectedCategoryIndex = spinnerCategories.getSelectedItemPosition();
            // Obtener la ID de la categoría seleccionada para la clave foránea
            int categoryId = allCategories.get(selectedCategoryIndex).getCategoryId();

            // Crear y guardar la Nota
            Note newNote = new Note(noteTitle, noteContent, categoryId);
            noteViewModel.insertNote(newNote);

            Toast.makeText(this, "Nota guardada con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad
        });
    }
}