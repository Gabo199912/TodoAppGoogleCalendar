package com.example.todoappgooglecalendar.Singleton;

import com.example.todoappgooglecalendar.Modelos.TareasModelo;
import com.example.todoappgooglecalendar.ModelosApiTodoist.ApiResponse;

import java.util.ArrayList;
import java.util.List;

public class TareasPendientes {
    public static List<TareasModelo>  listaTareas = new ArrayList<>();

    public static List<TareasModelo> getListaTareas(){
        return listaTareas;
    }

    public static void aniadirTarea(TareasModelo tarea){
        listaTareas.add(tarea);
    }
}
