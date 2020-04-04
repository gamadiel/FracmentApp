package com.example.fracmentapp;

import org.json.JSONArray;

public class Presentador implements Interfaz.Presentador{
    private Interfaz.Vista vista;
    private Interfaz.Modelo modelo;

    public Presentador(Interfaz.Vista vista){
        this.vista = vista;
        modelo = new Modelo(this);
    }

    @Override
    public void setToken(String result, int statusCode) {
        if(vista!=null){
            vista.setToken(result, statusCode);
        }
    }

    @Override
    public void getToken(String user, String pass) {
        System.out.println("getToken"+user+pass);
        if (vista!=null){
            modelo.getToken(user, pass);
        }
    }

    @Override
    public void getTasks(String token) {
        if (vista!=null){
            modelo.getTasks(token);
        }
    }

    @Override
    public void Tipo_Usuario(String token) {
        if (vista!=null){
            modelo.Tipo(token);
        }
    }

    @Override
    public void showError(String error, int statusCode) {
        if(vista!=null){
            vista.showError(error,statusCode);
        }
    }

    @Override
    public void showTasks(JSONArray jarr) {
        if (vista!= null){
            vista.shoeTasks(jarr);
        }
    }

    @Override
    public void addingTasks(String fila_tarea, String fila_fecha, String fila_hora, String fila_descripcion, String token) {
        if (vista!= null){
            modelo.addingTasks(fila_tarea, fila_fecha, fila_hora, fila_descripcion, token);
        }
    }

    @Override
    public void mostrartareaas(String token) {
        if (vista!=null){
            vista.mostrartareaas(token);
        }
    }

    @Override
    public void borrar(String token, String id) {
        if (vista!=null){
            modelo.borrar(token, id);
        }
    }

    @Override
    public void actualizar(String token) {
        if (vista!=null){
            vista.actualizar(token);
        }
    }
}
