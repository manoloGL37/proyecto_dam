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

import com.airbnb.lottie.LottieAnimationView;
import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView recyclerViewCarrito;
    TextView total , textTotal;
    Button btnComprar;
    ImageButton btnVolver;
    LottieAnimationView carritoVacio;

    View divider;

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
        carritoVacio = findViewById(R.id.carrito_vacio);
        btnComprar = findViewById(R.id.btn_comprar_carrito);
        btnVolver = findViewById(R.id.btn_volver_carrito);
        divider = findViewById(R.id.divider_inferior);
        textTotal = findViewById(R.id.texto_total);


        conexion.conectar();
        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        carrito = new ArrayList<>();
        carrito = conexion.verCarrito(idUser);

        if (carrito.size() > 0) {
            AdaptadorCarrito adapterCarrito = new AdaptadorCarrito(carrito, idUser);
            recyclerViewCarrito.setAdapter(adapterCarrito);

            total.setText(String.valueOf(conexion.totalCarrito(idUser) + " €"));
            carritoVacio.setVisibility(View.INVISIBLE);
            recyclerViewCarrito.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            textTotal.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
        } else {
            carritoVacio.setVisibility(View.VISIBLE);
            carritoVacio.setAnimation(R.raw.carrito_vacio);
            carritoVacio.playAnimation();
            recyclerViewCarrito.setVisibility(View.INVISIBLE);
            divider.setVisibility(View.INVISIBLE);
            textTotal.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
        }

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
                if (carrito.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), DireccionYPagoActivity.class);
                    intent.putExtra("idUsuarioLog", idUser);
                    startActivity(intent);
                }
            }
        });


    }
}