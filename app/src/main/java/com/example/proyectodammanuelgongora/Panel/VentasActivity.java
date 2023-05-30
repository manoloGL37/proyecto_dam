package com.example.proyectodammanuelgongora.Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.MiCuenta.AdaptadorPedidos;
import com.example.proyectodammanuelgongora.Modelos.Pedido;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

    RecyclerView recyclerViewVentas;
    ImageButton btnAtras;
    ArrayList<Pedido> listaVentas;
    int idUser;
    DataBase conexion = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        conexion.conectar();

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        recyclerViewVentas = findViewById(R.id.recyclerview_ventas);
        btnAtras = findViewById(R.id.btn_atras_ventas);

        recyclerViewVentas.setLayoutManager(new GridLayoutManager(this,1));

        listaVentas = new ArrayList<>();
        listaVentas = conexion.verPedidos();

        AdaptadorPedidos adapter = new AdaptadorPedidos(listaVentas);
        recyclerViewVentas.setAdapter(adapter);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PanelActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });

    }
}