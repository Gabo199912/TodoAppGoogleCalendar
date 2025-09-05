package com.example.todoappgooglecalendar;


import android.content.Intent;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoappgooglecalendar.Modelos.TareasModelo;
import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;
import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;
import com.example.todoappgooglecalendar.Singleton.TareasPendientes;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String API_TOKEN = ""; //<----------INSERTAR TOKEN AQUI

    private FirebaseAnalytics analytics;


    private EditText editTextTitulo;
    private EditText editTextDescripcion;

    private TextView textViewCalendario;
    private TextView textViewHora;

    private RadioGroup tipoUrgencia;
    private Button guardar;
    private Button botonTareasCreadas;

    private Button tareaTodoist;

    private Switch switchAnalytics;

    private Button fecha;
    private Button hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        analytics = FirebaseAnalytics.getInstance(this);

        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.METHOD, "inicio de app");
        params.putString("todoist", "Angel Vasquez");
        params.putString(FirebaseAnalytics.Event.TUTORIAL_BEGIN, "inicio de app");

        analytics.logEvent("inicio_app", params);


        //TODOIST
        tareaTodoist = findViewById(R.id.tareaTodoist);

        switchAnalytics = findViewById(R.id.switchAnalytics);



        //TAREAS EN VOLATIL
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);

        textViewCalendario = findViewById(R.id.textViewCalendario);
        textViewHora = findViewById(R.id.textViewHora);


        guardar = findViewById(R.id.guardar);
        fecha = findViewById(R.id.fecha);
        botonTareasCreadas = findViewById(R.id.botonTareasCreadas);


        tipoUrgencia = findViewById(R.id.tipoUrgencia);
        fecha = findViewById(R.id.fecha);
        hora = findViewById(R.id.hora);


        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        crashlytics.setUserId("Usuario angel");
        crashlytics.setCustomKey("pantalla", "MainActivity");


        fecha.setOnClickListener(view -> abrirCalendario());
        hora.setOnClickListener(view -> abrirFecha());
        botonTareasCreadas.setOnClickListener(view -> consultarTareas());


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    agregarDatos();

                }catch (Exception e){
                    FirebaseCrashlytics.getInstance().recordException(e);
                }
            }
        });


        switchAnalytics.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                analiticasActivadas();
            } else {
                Toast.makeText(this, "Analiticas desactivadas", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void analiticasActivadas() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.METHOD, "inicio de app");
        params.putString("todoist", "Angel Vasquez");
        params.putString(FirebaseAnalytics.Event.TUTORIAL_BEGIN, "inicio de app");
        analytics.logEvent("inicio_app", params);

        Toast.makeText(this, "Analiticas activadas", Toast.LENGTH_SHORT).show();

    }
    public void agregarDatos(){


        if (switchAnalytics.isChecked()) {
            Bundle UrgenciaBaja = new Bundle();
            UrgenciaBaja.putString("Tarea_Guardada", "clic Guardar");
            UrgenciaBaja.putString("Urgencia", "Guardar tarea local");
            UrgenciaBaja.putString("Se_Guardo_local_mente", "Guardar");

            analytics.logEvent("clic_urgencia_baja", UrgenciaBaja);
        }

        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        int idUrgencia = tipoUrgencia.getCheckedRadioButtonId();



        String tipoUrgencia = "";

        if(idUrgencia == R.id.bajo){
            tipoUrgencia = "BAJO";


        }else if(idUrgencia == R.id.medio){
            tipoUrgencia = "MEDIO";
        }else if(idUrgencia == R.id.alto){
            tipoUrgencia = "ALTO";
        }else {
            Toast.makeText(this, "Selecciona una urgencia", Toast.LENGTH_SHORT).show();
        }

        TareasModelo tarea = new TareasModelo(titulo, descripcion, tipoUrgencia);

        TareasPendientes.aniadirTarea(tarea);

        Intent intent = new Intent(MainActivity.this, lista_tareas.class);
        startActivity(intent);

    }
    public void nuevaVentana(View view){
        Intent intent = new Intent(this, listaTodoist.class);
        startActivity(intent);
    }
    public void consultarTareas(){

        if (switchAnalytics.isChecked()) {
            // Registra evento de Firebase si las analíticas están activadas
            Bundle tareaCreada = new Bundle();
            tareaCreada.putString("TodoIst_api", "Creo Tarea en todoIst");
            tareaCreada.putString("Status", "Tarea creada");
            tareaCreada.putString("Codigo", "200");

            analytics.logEvent("Llamada_al_api", tareaCreada);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.todoist.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TodoistApi todoistApi = retrofit.create(TodoistApi.class);
        Tareas nuevaTarea = new Tareas(editTextTitulo.getText().toString(), editTextDescripcion.getText().toString(), textViewCalendario.getText().toString());

        Call<ApiResponse> call = todoistApi.crearTarea(nuevaTarea, "Bearer " + API_TOKEN);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    Log.d("TIPO DE RESPUESTA" , "Tareas" +response.code());

                }else{
                    Log.e("TIPO DE RESPUESTA", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("TIPO DE RESPUESTA", "fallo la solicitud: " + t.getMessage());
            }
        });

    }
    public void abrirFecha(){
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Seleccionar hora")
                .build();

        picker.show(getSupportFragmentManager(), picker.toString());


        picker.addOnPositiveButtonClickListener(selection -> {
            int hora = picker.getHour();
            int minuto = picker.getMinute();

            String horaSeleccionada = String.format("%02d:%02d", hora, minuto);
            textViewHora.setText(horaSeleccionada);
            textViewHora.setVisibility(View.VISIBLE);
        });
    }
    public void abrirCalendario(){
        MaterialDatePicker<Long> materialCalendario = MaterialDatePicker.Builder
                .datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();


        materialCalendario.addOnPositiveButtonClickListener(selection -> {

            LocalDate fechaSeleccionada = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fechaSeleccionada = Instant.ofEpochMilli(selection)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate();
            }


            DateTimeFormatter formatoFecha = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            }
            String fechaFormateada = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fechaFormateada = fechaSeleccionada.format(formatoFecha);
            }

            textViewCalendario.setVisibility(View.VISIBLE);
            textViewCalendario.setText(fechaFormateada);

        });
        materialCalendario.show(getSupportFragmentManager(), materialCalendario.toString());
    }
    public void abrirVentana(View view){
        Intent intent = new Intent(this, lista_tareas.class);
        startActivity(intent);
    }
    public void abrirCalendario(View view){

    }
}