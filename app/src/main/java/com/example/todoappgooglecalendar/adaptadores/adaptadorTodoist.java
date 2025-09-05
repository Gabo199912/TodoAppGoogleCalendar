package com.example.todoappgooglecalendar.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;
import com.example.todoappgooglecalendar.R;
import com.example.todoappgooglecalendar.TodoistApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class adaptadorTodoist extends RecyclerView.Adapter<adaptadorTodoist.ViewApi> {
    public List<Tareas> listaTareasTodoist;

    public adaptadorTodoist(List<Tareas> listaTareasTodoist) {
        this.listaTareasTodoist = listaTareasTodoist;
    }

    public static class ViewApi extends RecyclerView.ViewHolder {

        private TextView textViewTituloTodoist;
        private TextView textViewDescripcionTodoist;
        private TextView textViewUrgenciaTodoist;

        private Button botonEliminar;


        public ViewApi(@NonNull View itemView) {
            super(itemView);

            textViewTituloTodoist = itemView.findViewById(R.id.textViewTituloTodoist);
            textViewDescripcionTodoist = itemView.findViewById(R.id.textViewDescripcionTodoist);
            textViewUrgenciaTodoist = itemView.findViewById(R.id.textViewUrgenciaTodoist);

            botonEliminar = itemView.findViewById(R.id.botonEliminar);
        }
    }

    @NonNull
    @Override
    public ViewApi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tareas_todoist, parent, false);
        return new ViewApi(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewApi holder, int position) {
        Tareas tarea = listaTareasTodoist.get(position);
        holder.textViewTituloTodoist.setText(tarea.getContent());
        holder.textViewDescripcionTodoist.setText(tarea.getDescription());



        switch (tarea.getPriority()) {
            case 1:
                holder.textViewUrgenciaTodoist.setText("Alta");
                break;
            case 2:
                holder.textViewUrgenciaTodoist.setText("Media");
                break;
            case 3:
                holder.textViewUrgenciaTodoist.setText("Baja");
                break;
            default:
                holder.textViewUrgenciaTodoist.setText("Normal");
                break;
        }




       /* holder.botonEliminar.setOnClickListener(view -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.todoist.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TodoistApi todoistApi = retrofit.create(TodoistApi.class);
            Call<ApiResponse> call = todoistApi.eliminarTarea()

        });*/

    }
    @Override
    public int getItemCount() {
        if (listaTareasTodoist == null) {
        return 0; // Devuelve 0 si la lista es nula
        }
        return listaTareasTodoist.size();
    }


}
