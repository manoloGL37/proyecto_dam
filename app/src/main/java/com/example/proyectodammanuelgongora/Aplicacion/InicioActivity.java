package com.example.proyectodammanuelgongora.Aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.proyectodammanuelgongora.Aplicacion.DatosUsuarioFragment;
import com.example.proyectodammanuelgongora.Compra.CarritoActivity;
import com.example.proyectodammanuelgongora.Compra.ProductoActivity;
import com.example.proyectodammanuelgongora.R;
import com.example.proyectodammanuelgongora.Aplicacion.RopaFragment;
import com.example.proyectodammanuelgongora.Aplicacion.SocialFragment;
import com.google.android.material.snackbar.Snackbar;

public class InicioActivity extends AppCompatActivity {

    FragmentTransaction transaction;
    Fragment fragmentUsuario, fragmentSocial, fragmentHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_inicio);
        setSupportActionBar(myToolbar);

        // Inicializar Fragments
        fragmentUsuario = new DatosUsuarioFragment();
        fragmentSocial = new SocialFragment();
        fragmentHome = new RopaFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.espacio_fragment, fragmentHome).commit();


    }


    // Metodo para cambios de fragment segun pulsacion
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.btn_home_menu:
                transaction.replace(R.id.espacio_fragment, fragmentHome);
                transaction.addToBackStack(null);
                break;
            case R.id.btn_social_menu:
                transaction.replace(R.id.espacio_fragment, fragmentSocial);
                transaction.addToBackStack(null);
                break;
            case R.id.btn_usuario_menu:
                transaction.replace(R.id.espacio_fragment, fragmentUsuario);
                transaction.addToBackStack(null);
                break;
        }
        transaction.commit();
    }



    // Metodo para el menú superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Opciones de menú
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.carrito_toolbar:
                Intent intent = new Intent(this, CarritoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}