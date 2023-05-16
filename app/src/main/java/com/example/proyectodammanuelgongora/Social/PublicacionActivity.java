package com.example.proyectodammanuelgongora.Social;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

public class PublicacionActivity extends AppCompatActivity {

    ImageButton imagenPublicacion;
    EditText descripcionPublicacion;
    Button btnAtras, btnPublicar;
    Utiles utils = new Utiles();
    DataBase bd = new DataBase();
    int idUser;

    private ActivityResultLauncher<Intent> galeriaLauncher;
    private Uri uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        bd.conectar();

        imagenPublicacion = findViewById(R.id.img_publicacion);
        descripcionPublicacion = findViewById(R.id.eti_descripcion_publicacion);
        btnAtras = findViewById(R.id.btn_atras_publicacion);
        btnPublicar = findViewById(R.id.btn_publicar);

        galeriaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            imagenPublicacion.setImageURI(uri);
                        } else {
                            Toast.makeText(PublicacionActivity.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        imagenPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    abrirGaleria();
            }
        });

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                byte[] imagen = utils.convertirImageButtonABlob(imagenPublicacion);
                bd.crearPublicacion(new Publicacion(0, imagen, descripcionPublicacion.getText().toString(), null, 0, 1));
            }
        });

    }

    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaLauncher.launch(intent);
    }
}