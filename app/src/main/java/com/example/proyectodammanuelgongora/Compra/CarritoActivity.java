package com.example.proyectodammanuelgongora.Compra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView recyclerViewCarrito;
    TextView total;
    Button btnComprar;
    ImageButton btnVolver;
    DataBase conexion = new DataBase();
    ArrayList<Producto> carrito;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerViewCarrito = findViewById(R.id.recyclerViewCarrito);
        recyclerViewCarrito.setLayoutManager(new GridLayoutManager(this,1));
        total = findViewById(R.id.eti_total_carrito);
        btnComprar = findViewById(R.id.btn_comprar_carrito);
        btnVolver = findViewById(R.id.btn_volver_carrito);


        conexion.conectar();
        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        carrito = new ArrayList<>();
        carrito = conexion.verCarrito(idUser);

        AdaptadorCarrito adapterCarrito = new AdaptadorCarrito(carrito);
        recyclerViewCarrito.setAdapter(adapterCarrito);

        total.setText(String.valueOf(conexion.totalCarrito(carrito.get(0).getNumPedido())) + " €");

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:
            }
        });


    }
}