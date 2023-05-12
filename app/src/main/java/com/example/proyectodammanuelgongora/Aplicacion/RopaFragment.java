package com.example.proyectodammanuelgongora.Aplicacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectodammanuelgongora.Compra.AdaptadorRopa;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class RopaFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Producto> listaProductos;
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

        conexion = new DataBase();
        conexion.conectar();

        recyclerView = view.findViewById(R.id.recyclerview_ropa);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listaProductos = new ArrayList<>();
        listaProductos = conexion.verProductos();

        Log.e("Productos", listaProductos.toString());

        AdaptadorRopa adapter = new AdaptadorRopa(listaProductos);
        recyclerView.setAdapter(adapter);


        return view;
    }
}