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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

import java.util.ArrayList;

public class EditarEliminarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etiNombre, etiDescripcion, etiStock, etiPrecio;
    Spinner etiCategoria;
    Button btnGuardar, btnEliminar;
    ImageButton imagenProd, btnAtras;
    Producto p;
    int idProd, idUser;
    DataBase conexion = new DataBase();
    Utiles utiles = new Utiles();
    String categoria;

    private ActivityResultLauncher<Intent> galeriaLauncher;
    private Uri uri = null;
    Boolean imagenIntroducida = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eliminar);

        conexion.conectar();

        Bundle bun = getIntent().getExtras();

        ArrayList<Integer> ids = bun.getIntegerArrayList("ids");
        idProd = ids.get(0);
        idUser = ids.get(1);

        Log.e("id", String.valueOf(idProd));

        p = conexion.verProducto(idProd);



        etiNombre = findViewById(R.id.eti_nombre_edit_prod);
        etiDescripcion = findViewById(R.id.eti_desc_edit_prod);
        etiCategoria = (Spinner) findViewById(R.id.eti_cat_edit_prod);
        etiStock = findViewById(R.id.eti_stock_edit_prod);
        etiPrecio = findViewById(R.id.eti_precio_edit_prod);
        imagenProd = findViewById(R.id.img_prod_edit);
        btnGuardar = findViewById(R.id.btn_edit_prod);
        btnEliminar = findViewById(R.id.btn_eliminar_prod);
        btnAtras = findViewById(R.id.btn_volver_edit_prdoucto);

        rellenarDatos();

        // Llenamos el spinner
        ArrayAdapter<CharSequence> adaptadorSpinner = ArrayAdapter.createFromResource(this, R.array.string_array_categorias, android.R.layout.simple_spinner_item);

        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applicamos el adaptador al Spinner
        etiCategoria.setAdapter(adaptadorSpinner);

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
                            Toast.makeText(EditarEliminarActivity.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
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

        etiCategoria.setOnItemSelectedListener(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etiNombre.getText().toString();
                String descipcion = etiDescripcion.getText().toString();
                int stock = Integer.parseInt(etiStock.getText().toString());
                double precio = Double.parseDouble(etiPrecio.getText().toString());
                byte[] imagen = utiles.imageButtonABlob(imagenProd);

                boolean ok = conexion.crearProducto(new Producto(nombre, categoria, imagen, descipcion, stock, precio));

                if (ok) {
                    Toast.makeText(EditarEliminarActivity.this, "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarEliminarActivity.this, PanelActivity.class);
                    startActivity(intent);
                }

            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PanelActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });


    }

    private void rellenarDatos() {

        etiNombre.setText(p.getNombreProd());
        etiDescripcion.setText(p.getDescripcion());
        etiStock.setText(String.valueOf(p.getStock()));
        etiPrecio.setText(String.valueOf(p.getPrecio()));
        ImageView imageView = utiles.blobAImageView(this, p.getImagen());
        imagenProd.setImageDrawable(imageView.getDrawable());
        categoria = p.getCategoria();
    }

    // Metodo que llama al launcher de la galeria
    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaLauncher.launch(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        if (categoria.equals("Camiseta")) {
            parent.setSelection(0);
        } else if (categoria.equals("Sudadera")) {
            parent.setSelection(1);
        }

    }
}