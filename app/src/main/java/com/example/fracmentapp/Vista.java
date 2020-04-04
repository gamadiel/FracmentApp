package com.example.fracmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fracmentapp.Interfaz;

import org.json.JSONArray;

public class Vista extends AppCompatActivity implements
        SesionFragment.FragmentLoginInterfacelistener, Interfaz.Vista, InicioFragment.FragmentInicioLIstener{

    private Interfaz.Presentador presentador;
    private SesionFragment sesionFragment;
    private InicioFragment inicioFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sesionFragment = new SesionFragment();
        inicioFragment = new InicioFragment();

        presentador = new Presentador(this);
        getSupportFragmentManager().beginTransaction().add(R.id.views_fragments,sesionFragment).commit();
    }
     public void datosdeusuario(String username, String password){
         System.out.println("datosdeusuario"+username+password);
        presentador.getToken(username, password);
     }

     public void addTask(String fila_tarea,String fila_fecha,String fila_hora,String fila_descripcion, String token){
         presentador.addingTasks( fila_tarea, fila_fecha, fila_hora, fila_descripcion,  token);
     }

    @Override
    public void delete(String token, String id) {
        presentador.borrar(token, id);
    }

    @Override
    public void setToken(String result, int statusCode) {
        System.out.println(result);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("token",result);
        inicioFragment.setArguments(bundle);
        System.out.println("setToken ==:> "+ result);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.views_fragments,inicioFragment);
        transaction.commit();

    }

    @Override
    public void showError(String error, int statusCode) {

    }

    @Override
    public void shoeTasks(JSONArray jarr) {
        System.out.println(jarr);
        inicioFragment.graficar(jarr);
    }

    @Override
    public void mostrartareaas(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token",token);
        inicioFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().detach(inicioFragment).attach(inicioFragment) .commit();
    }

    @Override
    public void actualizar(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token",token);
        inicioFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().detach(inicioFragment).attach(inicioFragment) .commit();
    }

    @Override
    public void getAllTasks(String credencial) {
        presentador.getTasks(credencial);
    }
}

