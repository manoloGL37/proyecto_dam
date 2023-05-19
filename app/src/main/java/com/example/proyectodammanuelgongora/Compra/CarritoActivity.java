package com.example.proyectodammanuelgongora.Compra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectodammanuelgongora.R;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView recyclerViewCarrito;
    TextView total;
    Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerViewCarrito = findViewById(R.id.recyclerViewCarrito);
        total = findViewById(R.id.eti_total_carrito);
        btnComprar = findViewById(R.id.btn_comprar_carrito);

    }
}