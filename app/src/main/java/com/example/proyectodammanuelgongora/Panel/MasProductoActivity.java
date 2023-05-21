package com.example.proyectodammanuelgongora.Panel;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Social.PublicacionActivity;
import com.example.proyectodammanuelgongora.Utils.Utiles;

public class MasProductoActivity extends AppCompatActivity {

    EditText etiNombre, etiDescripcion, etiCategoria, etiStock, etiPrecio;
    Button btnGuardar;
    ImageButton imagenProd;
    DataBase conexion = new DataBase();
    Utiles utils = new Utiles();

    private ActivityResultLauncher<Intent> galeriaLauncher;
    private Uri uri = null;
    Boolean imagenIntroducida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_producto);

        conexion.conectar();

        etiNombre = findViewById(R.id.eti_nombre_mas_prod);
        etiDescripcion = findViewById(R.id.eti_desc_mas_prod);
        etiCategoria = findViewById(R.id.eti_cat_mas_prod);
        etiStock = findViewById(R.id.eti_stock_mas_prod);
        etiPrecio = findViewById(R.id.eti_precio_mas_prod);
        imagenProd = findViewById(R.id.img_prod_mas);
        btnGuardar = findViewById(R.id.btn_mas_prod);

        // Launcher para abrir la galeria y recoger la imagen seleccionada
        galeriaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            imagenProd.setImageURI(uri);
                            imagenIntroducida = true;
                        } else {
                            Toast.makeText(MasProductoActivity.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                            imagenIntroducida = false;
                        }
                    }
                });

        // Boton para abrir la galeria
        imagenProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etiNombre.getText().toString();
                String descipcion = etiDescripcion.getText().toString();
                String categoria = etiCategoria.getText().toString();
                int stock = Integer.parseInt(etiStock.getText().toString());
                double precio = Double.parseDouble(etiPrecio.getText().toString());
                byte[] imagen = utils.imageButtonABlob(imagenProd);

                boolean ok = conexion.crearProducto(new Producto(nombre, categoria, imagen, descipcion, stock, precio));

                if (ok) {
                    Toast.makeText(MasProductoActivity.this, "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MasProductoActivity.this, PanelActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    // Metodo que llama al launcher de la galeria
    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaLauncher.launch(intent);
    }


}