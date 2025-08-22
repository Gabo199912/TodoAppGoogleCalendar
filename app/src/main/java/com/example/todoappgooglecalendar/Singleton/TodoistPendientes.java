package com.example.todoappgooglecalendar.Singleton;

import com.example.todoappgooglecalendar.ModelosApiTodoist.Tareas;

import java.util.List;

public class TodoistPendientes {
    public static List<Tareas> tareasPendientes;

    public static List<Tareas> getTareasPendientes(){
        return tareasPendientes;
    }

    public static void aniadirTarea(Tareas tarea){
        tareasPendientes.add(tarea);
    }
}
