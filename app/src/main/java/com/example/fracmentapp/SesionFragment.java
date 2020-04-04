package com.example.fracmentapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SesionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SesionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SesionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText username, contrasenia;
    private Button iniciar;
    private OnFragmentInteractionListener mListener;
    private FragmentLoginInterfacelistener listaner;

    public interface FragmentLoginInterfacelistener{
        void datosdeusuario(String username, String password);
    }

    public SesionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SesionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SesionFragment newInstance(String param1, String param2) {
        SesionFragment fragment = new SesionFragment();
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
        View view = inflater.inflate(R.layout.fragment_sesion, container, false);
        username = (EditText) view.findViewById(R.id.usuario);
        contrasenia = (EditText) view.findViewById(R.id.contrania);
        iniciar = (Button) view.findViewById(R.id.btnEntrar);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") && contrasenia.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Campo Username requerido",Toast.LENGTH_SHORT).show();
                }else{
                    listaner.datosdeusuario(username.getText().toString(), contrasenia.getText().toString());
                }
            }
        });
        return view;
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
            listaner = (FragmentLoginInterfacelistener) activity;
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
