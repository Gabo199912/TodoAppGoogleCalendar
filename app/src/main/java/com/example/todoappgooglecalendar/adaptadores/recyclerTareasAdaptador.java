package com.example.todoappgooglecalendar.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappgooglecalendar.Modelos.TareasModelo;
import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;
import com.example.todoappgooglecalendar.R;

import java.util.List;

public class recyclerTareasAdaptador extends RecyclerView.Adapter<recyclerTareasAdaptador.ViewHolder> {
    public List<TareasModelo> listaTareas;

    public recyclerTareasAdaptador(List<TareasModelo> listaTareasApi) {
        this.listaTareas = listaTareasApi;
    }

    public void actualizarLista(List<TareasModelo> listaTareas) {
        this.listaTareas = listaTareas;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitulo;
        private TextView textViewDescripcion;
        private TextView textViewUrgencia;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewUrgencia = itemView.findViewById(R.id.textViewUrgencia);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tareas, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TareasModelo tarea = listaTareas.get(position);
        holder.textViewTitulo.setText(tarea.getTitulo());
        holder.textViewDescripcion.setText(tarea.getDescripcion());
        holder.textViewUrgencia.setText(tarea.getTipoUrgencia());

    }


    @Override
    public int getItemCount() {
        return listaTareas.size();
    }
}
