package com.example.proyectodammanuelgongora.Compra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

import java.util.ArrayList;

public class ProductoActivity extends AppCompatActivity {

    TextView nombreProd, precioProd, descripcionProd;
    ImageView imagenProd;
    Producto p;
    DataBase conexion = new DataBase();
    ImageButton btnVolver;
    Button btnCarrito;
    Utiles utiles = new Utiles();
    int idProd;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nombreProd = findViewById(R.id.nombre_producto);
        precioProd = findViewById(R.id.precio_producto);
        descripcionProd = findViewById(R.id.descripcion_producto);
        btnVolver = findViewById(R.id.btn_volver);
        btnCarrito = findViewById(R.id.btn_carrito);
        imagenProd = findViewById(R.id.imagen_producto);

        Bundle bun = getIntent().getExtras();

        ArrayList<Integer> ids = bun.getIntegerArrayList("ids");
        idProd = ids.get(0);
        idUser = ids.get(1);

        Log.e("Array recibido", ids.toString());

        conexion.conectar();
        p = conexion.verProducto(idProd);

        rellenarDatos();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:
                // Añadir producto al carrito del id logeado
                boolean ok = conexion.enviarAlCarrito(idUser, p);
                if (ok) {
                    Toast.makeText(ProductoActivity.this, "Producto añadido al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductoActivity.this, "El producto ya se encuentra en el carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    void rellenarDatos() {
        nombreProd.setText(p.getNombreProd());
        precioProd.setText(String.valueOf(p.getPrecio()) + "€");
        descripcionProd.setText(p.getDescripcion());
        ImageView imageView = utiles.blobAImageView(this, p.getImagen());
        imagenProd.setImageDrawable(imageView.getDrawable());
    }
}