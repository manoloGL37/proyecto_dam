package com.example.proyectodammanuelgongora.Aplicacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
    int idUser;

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

        idUser = getArguments().getInt("idActivityInicio");

        // Asignacion de recyclerViews e inicializacion
        recyclerViewCamisetas = view.findViewById(R.id.recyclerview_camisetas);
        recyclerViewCamisetas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewSudaderas = view.findViewById(R.id.recyclerview_sudaderas);
        recyclerViewSudaderas.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listaCamisetas = new ArrayList<>();
        listaCamisetas = conexion.verProductos("Camiseta");

        listaSudaderas = new ArrayList<>();
        listaSudaderas = conexion.verProductos("Sudadera");

        // Enviar listas a adaptadores
        AdaptadorRopa adapterCamis = new AdaptadorRopa(getContext(), listaCamisetas, idUser);
        recyclerViewCamisetas.setAdapter(adapterCamis);

        AdaptadorRopa adapterSudaderas = new AdaptadorRopa(getContext(), listaSudaderas, idUser);
        recyclerViewSudaderas.setAdapter(adapterSudaderas);

        // Listener para cuando se pulse sobre un producto
        adapterCamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = recyclerViewCamisetas.getChildAdapterPosition(v);
                int idProd = listaCamisetas.get(posicion).getIdProducto();
                //Intent intent = new Intent(getActivity().getApplicationContext(), ProductoActivity.class);
                ArrayList<Integer> ids = new ArrayList<>();
                ids.add(idProd);
                ids.add(idUser);
                Log.e("Array que se manda", ids.toString());
                //intent.putIntegerArrayListExtra("ids", ids);
                //startActivity(intent);
            }
        });


        return view;
    }
}