package com.example.todoappgooglecalendar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappgooglecalendar.Modelos.TareasModelo;
import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;
import com.example.todoappgooglecalendar.Singleton.TareasPendientes;
import com.example.todoappgooglecalendar.adaptadores.recyclerTareasAdaptador;

import java.util.List;

public class lista_tareas extends AppCompatActivity {



    private RecyclerView recyclerTareas;

    private recyclerTareasAdaptador recyclerAdaptador;

    private List<TareasModelo> listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerTareas = findViewById(R.id.recyclerTareas);
        listaTareas = TareasPendientes.getListaTareas();
        recyclerAdaptador = new recyclerTareasAdaptador(listaTareas);

        recyclerTareas.setLayoutManager(new LinearLayoutManager(this));
        recyclerTareas.setAdapter(recyclerAdaptador);









    }
}