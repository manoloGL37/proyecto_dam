package com.example.proyectodammanuelgongora.MiCuenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Pedido;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class MisPedidosActivity extends AppCompatActivity {

    RecyclerView recyclerViewPedidos;
    ImageButton btnAtras;
    ArrayList<Pedido> listaPedidos;
    int idUser;
    DataBase conexion = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);

        conexion.conectar();

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        recyclerViewPedidos = findViewById(R.id.recyclerview_mis_pedidos);
        btnAtras = findViewById(R.id.btn_atras_mis_pedidos);

        recyclerViewPedidos.setLayoutManager(new GridLayoutManager(this,1));

        listaPedidos = new ArrayList<>();
        listaPedidos = conexion.verPedidos(idUser);

        AdaptadorPedidos adapter = new AdaptadorPedidos(listaPedidos);
        recyclerViewPedidos.setAdapter(adapter);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });

    }
}