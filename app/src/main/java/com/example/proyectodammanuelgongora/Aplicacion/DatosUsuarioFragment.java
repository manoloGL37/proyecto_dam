package com.example.proyectodammanuelgongora.Aplicacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Login.LoginActivity;
import com.example.proyectodammanuelgongora.Login.RegistroActivity;
import com.example.proyectodammanuelgongora.MiCuenta.MisDatosActivity;
import com.example.proyectodammanuelgongora.MiCuenta.MisPedidosActivity;
import com.example.proyectodammanuelgongora.MiCuenta.MisPublicacionesActivity;
import com.example.proyectodammanuelgongora.Modelos.Usuario;
import com.example.proyectodammanuelgongora.R;

public class DatosUsuarioFragment extends Fragment {

    TextView holaNombre, noEres;
    Button btnDatos, btnPedidos, btnPublicaciones, btnCerrarSesion;
    DataBase conexion = new DataBase();
    Usuario usuarioLog;

    public DatosUsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_datos_usuario, container, false);

        conexion.conectar();
        // Recuperamos usuario logeado
        int id = getArguments().getInt("idActivityInicio");
        usuarioLog = obtenerUsuarioPorId(id);

        holaNombre = vista.findViewById(R.id.eti_nombre_mis_datos);
        noEres = vista.findViewById(R.id.eti_cerrar_sesion);
        btnDatos = vista.findViewById(R.id.btn_user_datos);
        btnPedidos = vista.findViewById(R.id.btn_user_pedidos);
        btnPublicaciones = vista.findViewById(R.id.btn_user_publicaciones);
        btnCerrarSesion = vista.findViewById(R.id.btn_user_cerrar_sesion);

        holaNombre.setText("¡ Hola " + usuarioLog.getNombre() + " !");
        noEres.setText("¿No eres " + usuarioLog.getNombre() + "?");
        
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MisDatosActivity.class);
                i.putExtra("idUsuarioLog", usuarioLog.getIdUser());
                startActivity(i);
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MisPedidosActivity.class);
                i.putExtra("idUsuarioLog", usuarioLog.getIdUser());
                startActivity(i);
            }
        });

        btnPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MisPublicacionesActivity.class);
                i.putExtra("idUsuarioLog", usuarioLog.getIdUser());
                startActivity(i);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return vista;
    }

    // Obtener usuario
    private Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario;
        usuario = conexion.obtenerUsuario(id);
        return usuario;
    }
}