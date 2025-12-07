package com.example.aplicacionactividad2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicacionactividad2.R;
import com.example.aplicacionactividad2.model.CategoryWithNotes;
import com.example.aplicacionactividad2.model.Note;

import java.util.List;

public class CategoryWithNotesAdapter extends RecyclerView.Adapter<CategoryWithNotesAdapter.CategoryViewHolder> {

    private List<CategoryWithNotes> categoriesWithNotes;

    public void setCategoriesWithNotes(List<CategoryWithNotes> categoriesWithNotes) {
        this.categoriesWithNotes = categoriesWithNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_with_notes, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (categoriesWithNotes != null) {
            CategoryWithNotes current = categoriesWithNotes.get(position);
            holder.categoryName.setText(current.getCategory().getCategoryName());

            // Lógica para mostrar el resumen de las notas de la categoría
            int noteCount = current.getNotes().size();
            String summary = "Notas: " + noteCount;
            if (noteCount > 0) {
                // Muestra el título de la última nota para el resumen
                Note lastNote = current.getNotes().get(noteCount - 1);
                summary += " | Última: " + lastNote.getNoteTitle();
            }
            holder.notesSummary.setText(summary);
        }
    }

    @Override
    public int getItemCount() {
        return categoriesWithNotes != null ? categoriesWithNotes.size() : 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName;
        private final TextView notesSummary;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            notesSummary = itemView.findViewById(R.id.notes_summary);
        }
    }
}