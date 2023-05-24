package com.example.proyectodammanuelgongora.Compra;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Direccion;
import com.example.proyectodammanuelgongora.R;

public class DireccionYPagoActivity extends AppCompatActivity {

    EditText etiCalle, etiNumero, etiCiudad, etiPais, etiCP, etiTarjeta;
    Button btnPagar;
    LottieAnimationView compraHecha;
    Direccion direccion;
    int idUser;


    DataBase conexion = new DataBase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_y_pago);

        conexion.conectar();

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUsuarioLog", -1);

        Log.e("Usuario recibido", String.valueOf(idUser));

        etiCalle = findViewById(R.id.eti_calle_pago);
        etiNumero = findViewById(R.id.eti_num_pago);
        etiCiudad = findViewById(R.id.eti_ciudad_pago);
        etiPais = findViewById(R.id.eti_pais_pago);
        etiCP = findViewById(R.id.eti_cp_pago);
        etiTarjeta = findViewById(R.id.eti_tarjeta_pago);
        btnPagar = findViewById(R.id.btn_pagar);
        compraHecha = findViewById(R.id.compra_hecha);
        compraHecha.setVisibility(View.INVISIBLE);

        direccion = conexion.verDireccion(idUser);

        Log.e("direccion", direccion.toString());

        if (direccion.isTieneDireccion()) {
            rellenarCampos();
        }

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Direccion direccion = new Direccion(idUser, etiCalle.getText().toString(), etiNumero.getText().toString(), etiCiudad.getText().toString(),
                        etiPais.getText().toString(), etiCP.getText().toString());

                if (direccion.isTieneDireccion()) {
                    // UPDATE
                    conexion.insertarDireccion(direccion);
                } else {
                    // INSERT
                    conexion.insertarDireccion(direccion);
                }

                // INSERT PEDIDO
                
                // DELETE CARRITO

                // INSERT PEDIDO

                compraHecha.setVisibility(View.VISIBLE);

                compraHecha.setAnimation(R.raw.compra_hecha);
                compraHecha.playAnimation();

                compraHecha.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                        intent.putExtra("idUsuarioLog", idUser);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

            }
        });


    }

    private void rellenarCampos() {
        etiCalle.setText(direccion.getCalle());
        etiNumero.setText(direccion.getNumero());
        etiCiudad.setText(direccion.getCiudad());
        etiPais.setText(direccion.getPais());
        etiCP.setText(direccion.getPais());
    }
}