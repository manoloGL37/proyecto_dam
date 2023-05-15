package com.example.proyectodammanuelgongora.Compra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Aplicacion.RopaFragment;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;
import com.mysql.fabric.xmlrpc.base.Value;

public class ProductActivity extends AppCompatActivity {

    TextView nombreProd, precioProd, descripcionProd;
    Producto p;
    DataBase bd = new DataBase();
    Button volver, carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nombreProd = findViewById(R.id.nombre_producto);
        precioProd = findViewById(R.id.precio_producto);
        descripcionProd = findViewById(R.id.descripcion_producto);
        volver = findViewById(R.id.btn_volver);
        carrito = findViewById(R.id.btn_carrito);

        Intent intent = getIntent();
        int id = intent.getIntExtra("idProducto", -1);
        Log.e("Id traido", String.valueOf(id));

        bd.conectar();
        p = bd.verProducto(id);

        rellenarDatos();
        Log.e("Producto buscado en bd", p.toString());

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(intent);
            }
        });


    }

    void rellenarDatos() {
        nombreProd.setText(p.getNombreProd());
        precioProd.setText(String.valueOf(p.getPrecio()) + "€");
        descripcionProd.setText(p.getDescripcion());
    }
}