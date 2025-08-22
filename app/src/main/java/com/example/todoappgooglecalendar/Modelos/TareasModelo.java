package com.example.todoappgooglecalendar.Modelos;

public class TareasModelo {
    private String titulo;
    private String descripcion;
    private String fecha;
    private String hora;
    private String tipoUrgencia;

    public TareasModelo() {
    }

    public TareasModelo(String titulo, String descripcion, String tipoUrgencia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipoUrgencia = tipoUrgencia;
    }

    public TareasModelo(String titulo, String descripcion, String fecha, String hora, String tipoUrgencia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoUrgencia = tipoUrgencia;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoUrgencia() {
        return tipoUrgencia;
    }

    public void setTipoUrgencia(String tipoUrgencia) {
        this.tipoUrgencia = tipoUrgencia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
