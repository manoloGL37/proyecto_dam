package com.example.proyectodammanuelgongora.Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.MiCuenta.AdaptadorPublicaciones;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity {

    RecyclerView recyclerViewUsuarios;
    ImageButton btnAtras;
    DataBase conexion = new DataBase();
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        conexion.conectar();

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        recyclerViewUsuarios = findViewById(R.id.recyclerview_usuarios);
        btnAtras = findViewById(R.id.btn_atras_usuarios);

        recyclerViewUsuarios.setLayoutManager(new GridLayoutManager(this,1));

        listaUsuarios = new ArrayList<>();
        listaUsuarios = conexion.verUsuarios();

        AdaptadorUsuario adapter = new AdaptadorUsuario(listaUsuarios);
        recyclerViewUsuarios.setAdapter(adapter);

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