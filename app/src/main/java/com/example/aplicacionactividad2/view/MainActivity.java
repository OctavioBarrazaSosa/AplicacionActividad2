package com.example.aplicacionactividad2.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicacionactividad2.R;
import com.example.aplicacionactividad2.controller.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private CategoryWithNotesAdapter categoryAdapter; // Para la vista 1:N
    private NoteAdapter searchAdapter; // Para los resultados de búsqueda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicialización de Vistas y ViewModel (Controller)
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        recyclerView = findViewById(R.id.recyclerViewNotes);
        EditText searchEditText = findViewById(R.id.editTextSearch);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicialización de Adaptadores
        categoryAdapter = new CategoryWithNotesAdapter();
        searchAdapter = new NoteAdapter();

        // Vista inicial: Mostramos las categorías y sus notas (Consulta 1:N)
        recyclerView.setAdapter(categoryAdapter);

        // 2. Observación de la consulta avanzada de AGRUPACIÓN (1:N)
        noteViewModel.getAllCategoriesWithNotes().observe(this, categoriesWithNotes -> {
            categoryAdapter.setCategoriesWithNotes(categoriesWithNotes);
        });

        // 3. Lógica de Búsqueda Dinámica (Consulta Avanzada LIKE)
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();

                if (query.isEmpty()) {
                    // Si el campo de búsqueda está vacío, volvemos a la vista 1:N
                    recyclerView.setAdapter(categoryAdapter);
                } else {
                    // Si hay texto, cambiamos al adaptador de búsqueda
                    recyclerView.setAdapter(searchAdapter);

                    // Ejecutamos la consulta avanzada LIKE
                    noteViewModel.searchNotes(query).observe(MainActivity.this, notes -> {
                        searchAdapter.setNotes(notes);
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // 4. Navegación a la interfaz secundaria
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }
}