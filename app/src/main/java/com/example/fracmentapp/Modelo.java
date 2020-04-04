package com.example.fracmentapp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Modelo implements Interfaz.Modelo{
    private Interfaz.Presentador presentador;

    public Modelo(Interfaz.Presentador presentador){
        this.presentador=presentador;
    }
    @Override
    public void getToken(String user, String data) {
        System.out.println(user+data);
        String URL = "http://192.168.0.16:8000/users/login/";
        RequestParams params = new RequestParams();
        params.put("username",user);
        params.put("password",data);
        new AsyncHttpClient().post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String token;

                try {
                    JSONObject arr =new JSONObject(new String(responseBody));
                    token = arr.getString("token");
                    System.out.println("Token "+token);
                    System.out.println(token);
                    if(statusCode == 200){
                        presentador.setToken(token, statusCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("error "+error + " Status: " + statusCode);
                if(statusCode == 400){
                    String err = "El usuario o la contraseña son incorrectos";
                    presentador.showError(err, statusCode);
                }
                if(statusCode == 401){
                    String err = "El usuario o la contraseña son incorrectos";
                    presentador.showError(err, statusCode);
                }
                if(statusCode == 404){
                    String err = "Error Not found";
                    presentador.showError(err, statusCode);
                }
                if(statusCode == 500){
                    String err = "500 Internal Server Error";
                    presentador.showError(err, statusCode);
                }
            }
        });
    }

    @Override
    public void getTasks(String token) {
        String URL = "http://192.168.0.16:8000/users/tasks/";

        AsyncHttpClient peticiones = new AsyncHttpClient();
        peticiones.addHeader("Authorization", "Token " + token);
        peticiones.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String results;
                if(statusCode == 200){
                    try {
                        JSONObject arr =new JSONObject(new String(responseBody));
                        results = arr.getString("results");
                        System.out.println(results);
                        JSONArray jarr = new JSONArray(results);
                        presentador.showTasks(jarr);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void Tipo(String token) {
        String URL = "tu direccion api";
    }

    @Override
    public void addingTasks(String fila_tarea, String fila_fecha, String fila_hora, String fila_descripcion,final String token) {
        String URL = "http://192.168.0.16:8000/users/tasks/";
        AsyncHttpClient peticiones = new AsyncHttpClient();
        peticiones.addHeader("Authorization", "Token " + token);
        System.out.println("addproduct: "+token);
        RequestParams params = new RequestParams();
        params.put("task",fila_tarea);
        params.put("day",fila_fecha);
        params.put("hour",fila_hora);
        params.put("note",fila_descripcion);
        peticiones.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 201){
                    System.out.println("addproduct 201: "+token);
                    String t = "EL producto ha sido eliminado exitosamente";
                    presentador.mostrartareaas(token);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void borrar(final String token, String id) {
        String URL = "http://192.168.0.16:8000/users/tasks/";
        AsyncHttpClient peticion = new AsyncHttpClient();
        peticion.addHeader("Authorization", "Token " + token);
        System.out.println("addproduct: "+token);
        RequestParams params = new RequestParams();
        peticion.delete(URL + id + "/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 204) {
                    String t = "EL producto ha sido actualizado exitosamente";
                    presentador.actualizar(token);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
