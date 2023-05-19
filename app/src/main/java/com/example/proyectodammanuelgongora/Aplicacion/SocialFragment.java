package com.example.proyectodammanuelgongora.Aplicacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Social.AdaptadorSocial;
import com.example.proyectodammanuelgongora.Social.PublicacionActivity;

import java.util.ArrayList;


public class SocialFragment extends Fragment {

    Button btnNuvPubli;
    RecyclerView recyclerViewPublicaciones;
    DataBase conexion;
    ArrayList<Publicacion> listaPublicaciones = new ArrayList<Publicacion>();

    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);

        // Inicializar base de datos y conectar
        conexion = new DataBase();
        conexion.conectar();

        // Asignacion de recyclerView e inicializacion
        recyclerViewPublicaciones = view.findViewById(R.id.recyclerview_social);
        recyclerViewPublicaciones.setLayoutManager(new GridLayoutManager(getActivity(),1));

        listaPublicaciones = new ArrayList<>();
        listaPublicaciones = conexion.verPublicaciones();

        // Enviar lista a adaptador
        AdaptadorSocial adapterPublicaciones = new AdaptadorSocial(getActivity(), listaPublicaciones);
        recyclerViewPublicaciones.setAdapter(adapterPublicaciones);

        btnNuvPubli = view.findViewById(R.id.btn_agregar_publi);

        // Boton para nueva publicacion
        btnNuvPubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PublicacionActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}