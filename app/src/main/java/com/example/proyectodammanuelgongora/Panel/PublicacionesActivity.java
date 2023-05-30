package com.example.proyectodammanuelgongora.Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.MiCuenta.AdaptadorPublicaciones;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class PublicacionesActivity extends AppCompatActivity {

    RecyclerView recyclerViewPublicaciones;
    ImageButton btnAtras;
    DataBase conexion = new DataBase();
    ArrayList<Publicacion> listaPublicaciones = new ArrayList<Publicacion>();
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        conexion.conectar();

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        recyclerViewPublicaciones = findViewById(R.id.recyclerview_publicaciones);
        btnAtras = findViewById(R.id.btn_atras_publicaciones);

        recyclerViewPublicaciones.setLayoutManager(new GridLayoutManager(this,1));

        listaPublicaciones = new ArrayList<>();
        listaPublicaciones = conexion.verPublicaciones();

        AdaptadorPublicaciones adapter = new AdaptadorPublicaciones(this, listaPublicaciones, idUser);
        recyclerViewPublicaciones.setAdapter(adapter);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PanelActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });

    }
}