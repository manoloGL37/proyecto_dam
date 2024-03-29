package com.example.proyectodammanuelgongora.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText etiNombre, etiUsuario, etiEmail, etiContrasenya, etiConfContrasenya;
    Button btnRegistro, btnVolver;
    String nombre, usuario, email, contrasenya, confContrasenya;

    DataBase db = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etiNombre = findViewById(R.id.eti_nombre_registro);
        etiUsuario = findViewById(R.id.eti_usuario_registro);
        etiEmail = findViewById(R.id.eti_email_registro);
        etiContrasenya = findViewById(R.id.eti_contrasenya_registro);
        etiConfContrasenya = findViewById(R.id.eti_confirmar_contrasenya_registro);
        btnRegistro = findViewById(R.id.btn_registro);
        btnVolver = findViewById(R.id.btn_vovler_registro);

        db.conectar();



        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = etiNombre.getText().toString();
                usuario = etiUsuario.getText().toString();
                email = etiEmail.getText().toString();
                contrasenya = etiContrasenya.getText().toString();
                confContrasenya = etiConfContrasenya.getText().toString();

                if (!comrpobardatos()) {

                    boolean ok = db.crearUsuario(new Usuario(nombre, usuario, contrasenya, email));

                    if (ok) {
                        Toast.makeText(RegistroActivity.this, "Registro realizado con éxito", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
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

        if (nombre.isEmpty() || usuario.isEmpty() || email.isEmpty() || contrasenya.isEmpty() || confContrasenya.isEmpty()) {
            error = true;
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        if (db.comprobarEmail(email)) {
            error = true;
            Toast.makeText(this, "El email ya existe en la bd", Toast.LENGTH_SHORT).show();
        }

        if (db.comprobarUsuario(usuario)) {
            error = true;
            Toast.makeText(this, "El usuario ya existe en la bd", Toast.LENGTH_SHORT).show();
        }

        if (!confContrasenya.equals(contrasenya)) {
            error = true;
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

        /* TODO: Arreglar
        if (validadorEmail(email)) {
            error = true;
            Toast.makeText(this, "El email no es válido", Toast.LENGTH_SHORT).show();
        }

         */

        return error;
    }

    private boolean validadorEmail(String email) {
        String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }
}