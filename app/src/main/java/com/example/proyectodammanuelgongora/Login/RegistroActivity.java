package com.example.proyectodammanuelgongora.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.R;

public class RegistroActivity extends AppCompatActivity {

    EditText etiNombre, etiUsuario, email, etiContrasenya, etiConfContrasenya;
    Button btnRegistro, btnVolver;

    DataBase db = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etiNombre = findViewById(R.id.eti_nombre_registro);
        etiUsuario = findViewById(R.id.eti_usuario_registro);
        email = findViewById(R.id.eti_email_registro);
        etiContrasenya = findViewById(R.id.eti_contrasenya_registro);
        etiConfContrasenya = findViewById(R.id.eti_confirmar_contrasenya_registro);
        btnRegistro = findViewById(R.id.btn_registro);
        btnVolver = findViewById(R.id.btn_vovler_registro);

        db.conectar();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comrpobardatos()) {

                    // Llamar bd e insertar datos

                    Toast.makeText(RegistroActivity.this, "Registro realizado con éxito", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private boolean comrpobardatos() {

        boolean error = false;


        return error;
    }
}