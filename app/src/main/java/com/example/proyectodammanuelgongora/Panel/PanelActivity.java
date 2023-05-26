package com.example.proyectodammanuelgongora.Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Login.LoginActivity;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

public class PanelActivity extends AppCompatActivity {

    ImageButton btnVentas, btnPublicaciones, btnUsuarios, btnMasProducto, btnEliminarProducto, btnModProducto;
    Button btnCerrarSesion;
    CardView opUsuarios;
    DataBase conexion = new DataBase();
    int idUser;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        conexion.conectar();


        btnVentas = findViewById(R.id.btn_panel_ventas);
        btnPublicaciones = findViewById(R.id.btn_panel_publicaciones);
        btnUsuarios = findViewById(R.id.btn_panel_usuarios);
        btnMasProducto = findViewById(R.id.btn_panel_mas_producto);
        btnEliminarProducto = findViewById(R.id.btn_panel_eliminar_producto);
        btnModProducto = findViewById(R.id.btn_panel_mod_producto);
        btnCerrarSesion = findViewById(R.id.btn_panel_cerrar_sesion);
        opUsuarios = findViewById(R.id.card_view_usuarios);



        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        usuario = obtenerUsuarioPorId(idUser);

        Log.e("Usuario recogido", usuario.toString());

        boolean esAdmin = esAdmin(usuario);

        Log.e("Es admin?", String.valueOf(esAdmin));


        if (esAdmin(usuario)) {
            opUsuarios.setVisibility(View.VISIBLE);
        } else {
            opUsuarios.setVisibility(View.INVISIBLE);
        }



        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, VentasActivity.class);
                startActivity(intent);
            }
        });

        btnPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, PublicacionesActivity.class);
                startActivity(intent);
            }
        });

        btnMasProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, MasProductoActivity.class);
                startActivity(intent);
            }
        });

        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, EliminarProductoActivity.class);
                startActivity(intent);
            }
        });

        btnModProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, ModProductoActivity.class);
                startActivity(intent);
            }
        });

        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, VentasActivity.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanelActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }

    // Obtener usuario
    private Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario;
        usuario = conexion.obtenerUsuario(id);
        return usuario;
    }

    private boolean esAdmin(Usuario usuario) {
        boolean esAdmin = false;
        if (usuario.getRol() == 1) {
            esAdmin = true;
        }
        return esAdmin;
    }
}