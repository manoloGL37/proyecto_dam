package com.example.proyectodammanuelgongora.Database;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.Modelos.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

public class DataBase {

    String bd = "stylepeak";
    String url = "jdbc:mysql://192.168.0.192:3306/";
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

    public boolean crearUsuario(Usuario usuario) {
        boolean ok = false;
        try {

            String contrasenyaEncriptada = encriptarContrasenya(usuario.getContrsenya());

            String sql = "INSERT INTO Usuario (nombre, nombre_usuario, contrasenya, email) VALUES (?, ?, ?, ?)";
            PreparedStatement insertUser = conn.prepareStatement(sql);
            insertUser.setString(1, usuario.getNombre());
            insertUser.setString(2, usuario.getNombreUsuario());
            insertUser.setString(3, contrasenyaEncriptada);
            insertUser.setString(4, usuario.getEmail());
            insertUser.executeQuery();

            ok = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ok;
    }

    public Usuario login(String email, String contrasenya) {
        Usuario usuarioLog = new Usuario();
        try {
            String contrasenyaEncriptada = encriptarContrasenya(contrasenya);
            Log.e("Encriptar contraseña", contrasenyaEncriptada);
            String sql = "SELECT * FROM usuario WHERE email = ? AND contrasenya = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, contrasenyaEncriptada);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuarioLog = new Usuario();
                usuarioLog.setIdUser(resultSet.getInt("id"));
                Log.e("Ha hecho la consulta", String.valueOf(usuarioLog.getIdUser()));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarioLog;
    }

    public ArrayList<Producto> verProductos() {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                PreparedStatement queryProductos = conn.prepareStatement("SELECT * FROM producto");
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id_producto");
                    String nombre_prod = resultProductos.getString("nombre_prod");
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

    public Producto verProducto(int id) {
        Producto producto = new Producto();
        if (conn != null) {
            try {
                PreparedStatement queryProducto = conn.prepareStatement("SELECT * FROM producto WHERE id_producto = ?");
                queryProducto.setInt(1, id);
                ResultSet resultProducto = queryProducto.executeQuery();
                if (resultProducto.next()) {
                    int id_producto = resultProducto.getInt("id_producto");
                    String nombre_prod = resultProducto.getString("nombre_prod");
                    String categoria = resultProducto.getString("categoria");
                    Blob imagen = resultProducto.getBlob("categoria");
                    String descripcion = resultProducto.getString("descripcion");
                    String talla = resultProducto.getString("descripcion");
                    int stock = resultProducto.getInt("stock");
                    String grupo_producto = resultProducto.getString("grupo_producto");
                    double precio = resultProducto.getDouble("precio");
                    producto = new Producto(id_producto, nombre_prod, categoria, imagen, descripcion, talla, stock, grupo_producto, precio);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return producto;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearPublicacion(Publicacion publicacion) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaSubida = LocalDateTime.now().format(formatter);

            String sql = "INSERT INTO publicacion(imagen, descripcion, fecha_subida, likes, id_propietario) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, publicacion.getImagen());
            statement.setString(2, publicacion.getDescripcion());
            statement.setString(3, fechaSubida);
            statement.setInt(4, 0);
            statement.setInt(5, publicacion.getIdPropietario());

            statement.executeUpdate();
            Log.e("Subida", "Se ha subido bien");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Publicacion> verPublicaciones() {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
        if (conn != null) {
            try {
                PreparedStatement queryPublicaciones = conn.prepareStatement("SELECT * FROM publicacion ORDER BY fecha_subida DESC");
                ResultSet resultPublicaciones = queryPublicaciones.executeQuery();
                while (resultPublicaciones.next()) {
                    int idPublicacion = resultPublicaciones.getInt("id_publicacion");
                    byte[] imagen = resultPublicaciones.getBytes("imagen");
                    String descripcion = resultPublicaciones.getString("descripcion");
                    Date fechaSubida = resultPublicaciones.getDate("fecha_subida");
                    int likes = resultPublicaciones.getInt("likes");
                    int idPropietario = resultPublicaciones.getInt("id_propietario");
                    publicaciones.add(new Publicacion(idPublicacion, imagen, descripcion, fechaSubida, likes, idPropietario));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return publicaciones;
    }

    private String encriptarContrasenya(String contrasenya) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(contrasenya.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }



}
