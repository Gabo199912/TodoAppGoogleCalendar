package com.example.todoappgooglecalendar.ModelosApiTodoist;

public class Tareas {
    private String id;
    private String content;
    private String description;

    private String date;

    private boolean completed;
    private int priority;



    public Tareas(String id, String content, boolean completed, int priority, String description) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.priority = priority;
        this.description = description;
    }

    public Tareas(String content, String description) {
        this.content = content;
        this.description = description;
    }

    public Tareas(String content, String description, String date) {
        this.content = content;
        this.description = description;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
