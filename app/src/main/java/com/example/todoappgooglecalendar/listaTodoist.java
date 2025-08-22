package com.example.todoappgooglecalendar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;
import com.example.todoappgooglecalendar.Singleton.TodoistPendientes;
import com.example.todoappgooglecalendar.adaptadores.adaptadorTodoist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaTodoist extends AppCompatActivity {
    public static final String API_TOKEN = ""; //<----------INSERTAR TOKEN AQUI

    Button actualizarLista;

    RecyclerView recyclerTodoist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_todoist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        actualizarLista = findViewById(R.id.actualizarLista);
        recyclerTodoist = findViewById(R.id.recyclerTodoist);
        recyclerTodoist.setLayoutManager(new LinearLayoutManager(this));


        actualizarLista.setOnClickListener(view ->actualizarApi());
    }

    public void actualizarApi(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.todoist.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TodoistApi todoistApi = retrofit.create(TodoistApi.class);
        Call<ApiResponse> call = todoistApi.obtenerTareas("Bearer " + API_TOKEN);


        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                   List<Tareas> tareas = response.body().getResults();
                    adaptadorTodoist adaptador = new adaptadorTodoist(tareas);

                    recyclerTodoist.setAdapter(adaptador);

                    Log.d("CODIGO VERIFICACION", "Tareas: " + response.code());
                }else{
                    Log.e("CODIGO VERIFICACION", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("CODIGO VERIFICACION", "fallo la solicitud: " + t.getMessage());
            }
        });
    }

}