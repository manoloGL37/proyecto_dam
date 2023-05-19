package com.example.proyectodammanuelgongora.Social;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Utils.Utiles;

public class PublicacionActivity extends AppCompatActivity {

    ImageButton imagenPublicacion, btnAtras;
    EditText descripcionPublicacion;
    Button btnPublicar;
    Utiles utils = new Utiles();
    DataBase conexion = new DataBase();
    Usuario usuarioLog;
    Boolean imagenIntroducida = false;

    private ActivityResultLauncher<Intent> galeriaLauncher;
    private Uri uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        // Conectar base de datos
        conexion.conectar();

        // Recuperamos id logeado
        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idLogeado", -1);
        usuarioLog = obtenerUsuarioPorId(idUser);

        imagenPublicacion = findViewById(R.id.img_publicacion);
        descripcionPublicacion = findViewById(R.id.eti_descripcion_publicacion);
        btnAtras = findViewById(R.id.btn_atras_publicacion);
        btnPublicar = findViewById(R.id.btn_publicar);

        // Launcher para abrir la galeria y recoger la imagen seleccionada
        galeriaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            imagenPublicacion.setImageURI(uri);
                            imagenIntroducida = true;
                        } else {
                            Toast.makeText(PublicacionActivity.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                            imagenIntroducida = false;
                        }
                    }
                });


        // Boton para abrir la galeria
        imagenPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    abrirGaleria();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                intent.putExtra("idUsuarioLog", usuarioLog.getIdUser());
                startActivity(intent);
            }
        });

        // Boton para publicar
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (!comprobarCampos()) {
                    byte[] imagen = utils.imageButtonABlob(imagenPublicacion);
                    conexion.crearPublicacion(new Publicacion(0, imagen, descripcionPublicacion.getText().toString(), null, 0, usuarioLog.getIdUser(), usuarioLog.getNombreUsuario()));
                    Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                    intent.putExtra("idUsuarioLog", usuarioLog.getIdUser());
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

    // Obtener usuario
    private Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario;
        usuario = conexion.obtenerUsuario(id);
        return usuario;
    }

    public boolean comprobarCampos() {
        boolean faltanDatos = false;

        if (!imagenIntroducida) {
            faltanDatos = true;
            Toast.makeText(this, "Debe introducir una imagen", Toast.LENGTH_SHORT).show();
        }
        if (descripcionPublicacion.getText().toString().isEmpty()) {
            faltanDatos = true;
            Toast.makeText(this, "Debe introducir una descripción", Toast.LENGTH_SHORT).show();
        }

        return faltanDatos;
    }
}