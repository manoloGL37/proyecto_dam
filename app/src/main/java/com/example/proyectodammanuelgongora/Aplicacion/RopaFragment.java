package com.example.proyectodammanuelgongora.Aplicacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectodammanuelgongora.Compra.AdaptadorRopa;
import com.example.proyectodammanuelgongora.Compra.ProductoActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class RopaFragment extends Fragment {

    RecyclerView recyclerViewCamisetas, recyclerViewSudaderas;
    ArrayList<Producto> listaCamisetas, listaSudaderas;
    int posicion;
    Producto p;
    DataBase conexion;

    public RopaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ropa, container, false);

        // Inicializar base de datos y conectar
        conexion = new DataBase();
        conexion.conectar();

        // Asignacion de recyclerViews e inicializacion
        recyclerViewCamisetas = view.findViewById(R.id.recyclerview_camisetas);
        recyclerViewCamisetas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewSudaderas = view.findViewById(R.id.recyclerview_sudaderas);
        recyclerViewSudaderas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listaCamisetas = new ArrayList<>();
        listaCamisetas = conexion.verProductos();

        listaSudaderas = new ArrayList<>();
        listaSudaderas = conexion.verProductos();

        // Enviar listas a adaptadores
        AdaptadorRopa adapterCamis = new AdaptadorRopa(listaCamisetas);
        recyclerViewCamisetas.setAdapter(adapterCamis);

        AdaptadorRopa adapterSudaderas = new AdaptadorRopa(listaCamisetas);
        recyclerViewSudaderas.setAdapter(adapterSudaderas);

        // Listener para cuando se pulse sobre un producto
        adapterCamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = recyclerViewCamisetas.getChildAdapterPosition(v);
                int id = listaCamisetas.get(posicion).getIdProducto();
                Intent intent = new Intent(getActivity().getApplicationContext(), ProductoActivity.class);
                intent.putExtra("idProducto", id);
                startActivity(intent);
            }
        });


        return view;
    }
}