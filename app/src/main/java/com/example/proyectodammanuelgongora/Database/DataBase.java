package com.example.proyectodammanuelgongora.Database;

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
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection conn;

    public DataBase() {
    }

    /*
    public Connection conectar() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + bd, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void desconetar() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */


    public Connection conectar() {
        if (conn == null) {
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url+bd, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Producto> verProductos() {
        ArrayList<Producto> arrayProductos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                PreparedStatement queryProductos = conn.prepareStatement("SELECT * FROM producto");
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id_producto");
                    String nombre_prod = resultProductos.getString("nombre_prod");
                    String categoria = resultProductos.getString("categoria");
                    Blob imagen = resultProductos.getBlob("imagen");
                    String descripcion = resultProductos.getString("descripcion");
                    String talla = resultProductos.getString("talla");
                    int stock = resultProductos.getInt("nombre_prod");
                    String grupo_producto = resultProductos.getString("nombre_prod");
                    double precio = resultProductos.getDouble("nombre_prod");

                    arrayProductos.add(new Producto(id_producto, nombre_prod, categoria, imagen, descripcion, talla, stock, grupo_producto, precio));

                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return arrayProductos;
    }



}
