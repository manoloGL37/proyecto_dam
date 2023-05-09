package com.example.proyectodammanuelgongora.Aplicacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.R;

public class DatosUsuarioFragment extends Fragment {

    Button btnDatos;

    public DatosUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_datos_usuario, container, false);

        btnDatos = vista.findViewById(R.id.btn_user_datos);
        
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(vista.getContext(), "Aqui se iran a los datos del usuario", Toast.LENGTH_SHORT).show();
            }
        });

        return vista;
    }
}