package com.example.proyectodammanuelgongora.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    DataBase conexion;
    EditText username, password;
    Button btnLogin, btnRegistro;
    ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Conexion base de datos
        conexion = new DataBase();
        conexion.conectar();


        username = findViewById(R.id.eti_login_user);
        password = findViewById(R.id.eti_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegistro = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InicioActivity.class);

                // Logear usuario
                Usuario user = conexion.login(username.getText().toString(), password.getText().toString());
                if (user.getIdUser() != 0) {
                    i.putExtra("idUsuarioLog", user.getIdUser());
                    startActivity(i);
                } else
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de crear nuevo usuario
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(i);
            }
        });


    }
}