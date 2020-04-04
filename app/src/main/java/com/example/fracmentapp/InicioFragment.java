package com.example.fracmentapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText fila_tarea, fila_fecha, fila_hora, fila_descripcion;
    private Button btn_edit, btn_add;
    private TableLayout table_tasks;
    private String credencial;
    private OnFragmentInteractionListener mListener;
    private FragmentInicioLIstener lIstener;

    public interface FragmentInicioLIstener{
        void getAllTasks(String credencial);
        void addTask(String fila_tarea,String fila_fecha,String fila_hora,String fila_descripcion, String token);
        void delete(String token, String id);
    }
    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        fila_tarea = (EditText) vista.findViewById(R.id.fila_tarea);
        fila_fecha=(EditText) vista.findViewById(R.id.fila_fecha);
        fila_hora = (EditText) vista.findViewById(R.id.fila_hora);
        fila_descripcion = (EditText) vista.findViewById(R.id.fila_descripcion);
        btn_add = (Button) vista.findViewById(R.id.btn_add);
        btn_edit = (Button) vista.findViewById(R.id.btn_edit);
        table_tasks = (TableLayout) vista.findViewById(R.id.table_tasks);
        Bundle bundle = getArguments();
        credencial = bundle.getString("token");
        lIstener.getAllTasks(credencial);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fila "+fila_tarea.getText().toString());
                if(fila_tarea.getText().toString().equals("") || fila_fecha.getText().toString().equals("") ||
                        fila_hora.getText().toString().equals("") || fila_descripcion.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Campos requeridos",Toast.LENGTH_SHORT).show();
                }else {
                    lIstener.addTask(fila_tarea.getText().toString(),fila_fecha.getText().toString(),fila_hora.getText().toString(), fila_descripcion.getText().toString(),credencial);
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return vista;
    }

    public void graficar(JSONArray jarr){
        for(int i = 0; i < jarr.length(); ++i) {
            try {
                JSONObject jobj = jarr.getJSONObject(i);
                String []cadena = {jobj.getString("task"), jobj.getString("day"),jobj.getString("hour"),jobj.getString("note")};
                TableRow row = new TableRow(getContext());
                TextView textView;
                for (int j=0; j < 4; j++){
                    System.out.println(cadena[j]);
                    textView = new TextView(getContext());
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(15,15,15,15);
                    textView.setText(cadena[j]);
                    row.addView(textView);
                }
                Button edit = new Button(getContext());
                edit.setText("Edit");
                int id = Integer.parseInt(jobj.getString("id"));
                edit.setId(id);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("v.getid is:- " + v.getId());
                        String puntero = String.valueOf(v.getId());
                        btn_add.setEnabled(false);
                        btn_edit.setEnabled(true);
                        //listener_home.editProducts(Token, puntero);
                    }
                });
                row.addView(edit);
                Button delete = new Button(getContext());
                delete.setText("Delete");
                int id_ = Integer.parseInt(jobj.getString("id"));
                delete.setId(id_);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("v.getid is:- " + v.getId());
                        String id = String.valueOf(v.getId());
                        lIstener.delete(credencial, id);
                    }
                });
                row.addView(delete);
                table_tasks.addView(row);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            lIstener = (FragmentInicioLIstener) activity;
        }catch (RuntimeException a){
            throw new RuntimeException(activity.toString()
                    + " must implement FragmentLoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
