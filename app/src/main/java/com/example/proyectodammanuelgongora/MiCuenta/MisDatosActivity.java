package com.example.proyectodammanuelgongora.MiCuenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Login.LoginActivity;
import com.example.proyectodammanuelgongora.Login.RegistroActivity;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

public class MisDatosActivity extends AppCompatActivity {

    EditText etiNombre, etiUsuario, etiEmail;
    Button btnGuardar;
    ImageButton btnVolver;
    String nombre, usuario, email;
    int idUser;

    DataBase conexion = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        etiNombre = findViewById(R.id.eti_nombre_mis_datos);
        etiUsuario = findViewById(R.id.eti_usuario_mis_datos);
        etiUsuario.setEnabled(false);
        etiEmail = findViewById(R.id.eti_email_mis_datos);
        etiEmail.setEnabled(false);
        btnGuardar = findViewById(R.id.btn_guardar_mis_datos);
        btnVolver = findViewById(R.id.btn_atras_mis_datos);

        conexion.conectar();

        rellenarDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = etiNombre.getText().toString();
                usuario = etiUsuario.getText().toString();
                email = etiEmail.getText().toString();

                if (!comrpobardatos()) {

                    boolean ok = conexion.actualizarUsuario(new Usuario(idUser, nombre, usuario, email, 3));

                    if (ok) {
                        Toast.makeText(MisDatosActivity.this, "Registro actualizado con éxito", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), InicioActivity.class);
                        i.putExtra("idUsuarioLog", idUser);
                        startActivity(i);
                    }
                }

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                intent.putExtra("idUsuarioLog", idUser);
                startActivity(intent);
            }
        });
    }

    private void rellenarDatos() {
        Usuario usuario = conexion.obtenerUsuario(idUser);

        etiNombre.setText(usuario.getNombre());
        etiUsuario.setText(usuario.getNombreUsuario());
        etiEmail.setText(usuario.getEmail());
    }

    private boolean comrpobardatos() {

        boolean error = false;

        if (nombre.isEmpty() || usuario.isEmpty() || email.isEmpty()) {
            error = true;
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        /* TODO: Arreglar
        if (validadorEmail(email)) {
            error = true;
            Toast.makeText(this, "El email no es válido", Toast.LENGTH_SHORT).show();
        }

         */

        return error;
    }
}