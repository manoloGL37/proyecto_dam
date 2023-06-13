package com.example.proyectodammanuelgongora.Panel;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    Boolean imagenCambiada = false;

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

        categoria = p.getCategoria();
        if (categoria.equals("Camiseta")) {
            etiCategoria.setSelection(0);
        } else if (categoria.equals("Sudadera")) {
            etiCategoria.setSelection(1);
        }

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
                            imagenCambiada = true;
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

                if (!faltanDatos()) {
                    String nombre = etiNombre.getText().toString();
                    String descipcion = etiDescripcion.getText().toString();
                    int stock = Integer.parseInt(etiStock.getText().toString());

                    byte[] imagen = utiles.imageButtonABlob(imagenProd);

                    try {
                        double precio = Utiles.parseToDouble(etiPrecio.getText().toString());

                        boolean ok = false;

                        if (imagenCambiada) {
                            ok = conexion.actualizarProducto(idProd, new Producto(nombre, categoria, imagen, descipcion, stock, precio));

                        } else if (!imagenCambiada) {
                            ok = conexion.actualizarProductoSinImagen(idProd, new Producto(nombre, categoria, descipcion, stock, precio));
                        }


                        if (ok) {
                            Toast.makeText(EditarEliminarActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditarEliminarActivity.this, ModProductoActivity.class);
                            intent.putExtra("idUsuarioLog", idUser);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(EditarEliminarActivity.this, "Tiene algún error, revise los campos", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditarEliminarActivity.this);
                builder.setTitle("Confirmar eliminación");
                builder.setMessage("¿Estás seguro de que deseas eliminar el producto?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean ok = conexion.eliminarProducto(idProd);

                        if (ok) {
                            Toast.makeText(EditarEliminarActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditarEliminarActivity.this, ModProductoActivity.class);
                            intent.putExtra("idUsuarioLog", idUser);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModProductoActivity.class);
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
        ImageButton image = utiles.blobAImageButton(this, p.getImagen());
        imagenProd.setImageDrawable(image.getDrawable());
        imagenProd.setBackground(null);

    }

    private boolean faltanDatos() {
        String nombre = etiNombre.getText().toString().trim();
        String descripcion = etiDescripcion.getText().toString().trim();
        String stock = etiStock.getText().toString().trim();
        String precio = etiPrecio.getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || stock.isEmpty() || precio.isEmpty()) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (etiCategoria.getSelectedItem() == null) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(imagenProd != null) {
            imagenIntroducida = true;
        }

        if(!imagenIntroducida) {
            Toast.makeText(this, "Debe introducir una imagen", Toast.LENGTH_SHORT).show();
            return true;
        }


        return false;
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

    }
}