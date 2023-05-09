package com.example.proyectodammanuelgongora.Database;

import android.os.StrictMode;
import android.util.Log;

import com.example.proyectodammanuelgongora.Modelos.Producto;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

public class DataBase {

    String bd = "stylepeak";
    String url = "jdbc:mysql://192.168.0.194:3306/";
    String user = "root";
    String password = "";
    Connection conn;
    String driver = "com.mysql.jdbc.Driver";

    public DataBase() {
    }

    public Connection conectar() {
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName(driver);
            conn = DriverManager.getConnection(url + bd, user, password);
            Log.e("Conexion", "Conexion establecida");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            Log.e("Conexion", "Error al conectar");
        }
        return conn;
    }

    public void desconectar() {
        try {
            conn.close();

            conn = null;
        } catch (SQLException ex) {

            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ArrayList<Producto> verProductos() {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                PreparedStatement queryProductos = conn.prepareStatement("SELECT * FROM producto");
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id_producto");
                    String nombre_prod = resultProductos.getString("id_producto");
                    String categoria = resultProductos.getString("categoria");
                    Blob imagen = resultProductos.getBlob("categoria");
                    String descripcion = resultProductos.getString("descripcion");
                    String talla = resultProductos.getString("descripcion");
                    int stock = resultProductos.getInt("stock");
                    String grupo_producto = resultProductos.getString("grupo_producto");
                    double precio = resultProductos.getDouble("precio");
                    productos.add(new Producto(id_producto, nombre_prod, categoria, imagen, descripcion, talla, stock, grupo_producto, precio));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return productos;
    }


}
