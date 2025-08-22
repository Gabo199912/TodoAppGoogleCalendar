package com.example.todoappgooglecalendar.ModelosApiTodoist;

import java.util.List;

public class ApiResponse {
    private List<Tareas> results;
    private String next_cursor;



    public List<Tareas> getResults() {
        return results;
    }

    public void setResults(List<Tareas> results) {
        this.results = results;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }
}
