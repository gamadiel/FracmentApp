package com.example.fracmentapp;

import org.json.JSONArray;

public interface Interfaz {

    interface Vista{
        void setToken(String result, int statusCode);
        void showError (String error, int statusCode);
        void shoeTasks(JSONArray jarr);
        void mostrartareaas(String token);
        void actualizar(String token);
    }

    interface Presentador{
        void setToken(String result, int statusCode);
        void getToken(String user, String pass);
        void getTasks(String token);
        void Tipo_Usuario(String token);
        void showError(String error, int statusCode);
        void showTasks(JSONArray jarr);
        void addingTasks(String fila_tarea,String fila_fecha,String fila_hora,String fila_descripcion, String token);
        void mostrartareaas(String token);
        void borrar(String token, String id);
        void actualizar(String token);
    }

    interface Modelo{
        void getToken(String user, String data);
        void getTasks(String token);
        void Tipo(String token);
        void addingTasks(String fila_tarea,String fila_fecha,String fila_hora,String fila_descripcion, String token);
        void borrar(String token, String id);
    }
}
