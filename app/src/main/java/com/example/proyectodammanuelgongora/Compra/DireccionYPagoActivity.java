package com.example.proyectodammanuelgongora.Compra;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.proyectodammanuelgongora.Aplicacion.InicioActivity;
import com.example.proyectodammanuelgongora.Database.DataBase;
import com.example.proyectodammanuelgongora.Modelos.Direccion;
import com.example.proyectodammanuelgongora.Modelos.Pedido;
import com.example.proyectodammanuelgongora.Modelos.PedidoDetalles;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.R;

import java.util.ArrayList;
import java.util.Date;

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

        if (direccion.isTieneDireccion()) {
            rellenarCampos();
        }

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (!comporbarCampos()) {

                    if (direccion.isTieneDireccion()) {
                        // UPDATE
                        conexion.actualizarDireccion(new Direccion(direccion.getIdDireccion(), idUser, etiCalle.getText().toString(), etiNumero.getText().toString(),
                                etiCiudad.getText().toString(), etiPais.getText().toString(), etiCP.getText().toString()));

                    } else {
                        // INSERT
                        conexion.insertarDireccion(new Direccion(idUser, etiCalle.getText().toString(), etiNumero.getText().toString(),
                                etiCiudad.getText().toString(), etiPais.getText().toString(), etiCP.getText().toString()));
                    }

                    // INSERT PEDIDO

                    double total = conexion.totalCarrito(idUser);
                    int idDireccion = conexion.obtenerIdDireccion(idUser);
                    Pedido pedido = new Pedido(idUser, total, idDireccion, null);
                    conexion.insertarPedido(pedido);

                    // DELETE CARRITO

                    ArrayList<Producto> productosPedido = new ArrayList<>();
                    productosPedido = conexion.verCarrito(idUser);

                    conexion.borrarCarrito(idUser);

                    // INSERT PEDIDO DETALLES

                    int idPedido = conexion.obtenerIdPedido(idUser);


                    for (Producto producto: productosPedido) {
                        PedidoDetalles pedidoDetalles = new PedidoDetalles(idPedido, producto.getIdProducto(), producto.getPrecio());
                        conexion.insertarDetallesPedido(pedidoDetalles);
                    }


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
                } else {
                    Toast.makeText(DireccionYPagoActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean comporbarCampos() {
        String calle = etiCalle.getText().toString().trim();
        String numero = etiNumero.getText().toString().trim();
        String ciudad = etiCiudad.getText().toString().trim();
        String pais = etiPais.getText().toString().trim();
        String cp = etiCP.getText().toString().trim();
        String tarjeta = etiTarjeta.getText().toString().trim();
        boolean faltanDatos = false;

        if (calle.isEmpty() || numero.isEmpty() || ciudad.isEmpty() || pais.isEmpty() || cp.isEmpty() || tarjeta.isEmpty()) {
            return true;
        }

        return faltanDatos;
    }

    private void rellenarCampos() {
        etiCalle.setText(direccion.getCalle());
        etiNumero.setText(direccion.getNumero());
        etiCiudad.setText(direccion.getCiudad());
        etiPais.setText(direccion.getPais());
        etiCP.setText(direccion.getCp());
    }
}