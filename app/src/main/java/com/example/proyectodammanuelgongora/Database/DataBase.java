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
    String url = "jdbc:mysql://192.168.0.194:3306/";
    String user = "root";
    String password = "";
    Connection conn;
    String driver = "com.mysql.jdbc.Driver";

    public DataBase() {
    }

    // Conectar base de datos
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

    // Desconectar base de datos
    public void desconectar() {
        try {
            conn.close();

            conn = null;
        } catch (SQLException ex) {

            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Crear usuario nuevo en base de datos
    public boolean crearUsuario(Usuario usuario) {
        boolean ok = false;
        try {

            String contrasenyaEncriptada = encriptarContrasenya(usuario.getContrsenya());

            String query = "INSERT INTO usuario (nombre, nombre_usuario, contrasenya, email, rol) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement insertUser = conn.prepareStatement(query);
            insertUser.setString(1, usuario.getNombre());
            insertUser.setString(2, usuario.getNombreUsuario());
            insertUser.setString(3, contrasenyaEncriptada);
            insertUser.setString(4, usuario.getEmail());
            insertUser.setInt(5, 3);
            insertUser.executeUpdate();

            ok = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ok;
    }

    // Logear usuario
    public Usuario login(String email, String contrasenya) {
        Usuario usuarioLog = new Usuario();
        try {
            String contrasenyaEncriptada = encriptarContrasenya(contrasenya);

            String query = "SELECT * FROM usuario WHERE email = ? AND contrasenya = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, contrasenyaEncriptada);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuarioLog.setIdUser(resultSet.getInt("id"));
                usuarioLog.setRol(resultSet.getInt("rol"));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarioLog;
    }

    public Usuario obtenerUsuario(int id) {
        Usuario usuario = new Usuario();

        if (conn != null) {
            try {
            String query = "SELECT * FROM usuario WHERE id = ?";

            PreparedStatement queryUsuario = conn.prepareStatement(query);
            queryUsuario.setInt(1, id);
            ResultSet resultUsuario = queryUsuario.executeQuery();
            if (resultUsuario.next()) {
                usuario.setIdUser(resultUsuario.getInt("id"));
                usuario.setNombre(resultUsuario.getString("nombre"));
                usuario.setNombreUsuario(resultUsuario.getString("nombre_usuario"));
                usuario.setRol(resultUsuario.getInt("rol"));
            }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return usuario;
    }

    // Ver todos los productos
    public ArrayList<Producto> verProductos() {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                String query = "SELECT * FROM producto";

                PreparedStatement queryProductos = conn.prepareStatement(query);
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id_producto");
                    String nombre_prod = resultProductos.getString("nombre_prod");
                    String categoria = resultProductos.getString("categoria");
                    byte[] imagen = resultProductos.getBytes("imagen");
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

    // Ver un producto
    public Producto verProducto(int id) {
        Producto producto = new Producto();
        if (conn != null) {
            try {
                String query = "SELECT * FROM producto WHERE id_producto = ?";

                PreparedStatement queryProducto = conn.prepareStatement(query);
                queryProducto.setInt(1, id);
                ResultSet resultProducto = queryProducto.executeQuery();
                if (resultProducto.next()) {
                    int id_producto = resultProducto.getInt("id_producto");
                    String nombre_prod = resultProducto.getString("nombre_prod");
                    String categoria = resultProducto.getString("categoria");
                    byte[] imagen = resultProducto.getBytes("imagen");
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

    // Crear publucacion
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearPublicacion(Publicacion publicacion) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaSubida = LocalDateTime.now().format(formatter);

            String query = "INSERT INTO publicacion(imagen, descripcion, fecha_subida, likes, id_propietario) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement queryPublicacion = conn.prepareStatement(query);
            queryPublicacion.setBytes(1, publicacion.getImagen());
            queryPublicacion.setString(2, publicacion.getDescripcion());
            queryPublicacion.setString(3, fechaSubida);
            queryPublicacion.setInt(4, 0);
            queryPublicacion.setInt(5, publicacion.getIdPropietario());

            queryPublicacion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ver todas las publicaciones
    public ArrayList<Publicacion> verPublicaciones() {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
        if (conn != null) {
            try {
                String query = "SELECT p.*, us.nombre_usuario FROM publicacion p INNER JOIN usuario us ON us.id = p.id_propietario ORDER BY fecha_subida DESC";

                PreparedStatement queryPublicaciones = conn.prepareStatement(query);
                ResultSet resultPublicaciones = queryPublicaciones.executeQuery();
                while (resultPublicaciones.next()) {
                    int idPublicacion = resultPublicaciones.getInt("id_publicacion");
                    byte[] imagen = resultPublicaciones.getBytes("imagen");
                    String descripcion = resultPublicaciones.getString("descripcion");
                    Date fechaSubida = resultPublicaciones.getDate("fecha_subida");
                    int likes = resultPublicaciones.getInt("likes");
                    int idPropietario = resultPublicaciones.getInt("id_propietario");
                    String nombre_propietario = resultPublicaciones.getString("nombre_usuario");
                    publicaciones.add(new Publicacion(idPublicacion, imagen, descripcion, fechaSubida, likes, idPropietario, nombre_propietario));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return publicaciones;
    }

    // Incrementar likes
    public void incrementarLikes(int likes, int id) {
        if (conn != null) {
            try {
                String query = "UPDATE Publicacion SET likes = ? WHERE id_publicacion = ?";

                PreparedStatement queryLikes = conn.prepareStatement(query);
                queryLikes.setInt(1, likes);
                queryLikes.setInt(2, id);

                queryLikes.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean comprobarUsuario (String usuario) {
        boolean existe = false;
        if (conn != null) {
            try {
                String query = "SELECT nombre_usuario FROM usuario WHERE nombre_usuario = ?";

                PreparedStatement queryComprobarUsuario = conn.prepareStatement(query);
                queryComprobarUsuario.setString(1, usuario);
                ResultSet resultComprobarUsuario = queryComprobarUsuario.executeQuery();
                if (resultComprobarUsuario.next()) {
                    existe = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return existe;
    }

    public boolean comprobarEmail (String email) {
        boolean existe = false;
        if (conn != null) {
            try {
                String query = "SELECT email FROM usuario WHERE email = ?";

                PreparedStatement queryComprobarEmail = conn.prepareStatement(query);
                queryComprobarEmail.setString(1, email);
                ResultSet resultComprobarEmail = queryComprobarEmail.executeQuery();
                if (resultComprobarEmail.next()) {
                    existe = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return existe;
    }

    public ArrayList<Producto> verCarrito(int id) {
        ArrayList<Producto> carrito = new ArrayList<Producto>();
        if (conn != null) {
            try {
                String query = "SELECT precio_prod, nombre_prod, n_pedido FROM carrito c INNER JOIN producto p ON c.id_producto = p.id_producto WHERE propietario_carrito = ?";

                PreparedStatement queryCarrito = conn.prepareStatement(query);
                queryCarrito.setInt(1, id);
                ResultSet resultCarrito = queryCarrito.executeQuery();
                while (resultCarrito.next()) {
                    Double precioProd = resultCarrito.getDouble("precio_prod");
                    String nombreProducto = resultCarrito.getString("nombre_prod");
                    int numPedido = resultCarrito.getInt("n_pedido");
                    carrito.add(new Producto(nombreProducto, precioProd, numPedido));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return carrito;
    }

    public double totalCarrito(int nPedido) {
        double total = 0;
        if (conn != null) {
            try {
                String query = "SELECT SUM(precio_prod) as total FROM carrito WHERE n_pedido = ?";

                PreparedStatement queryCarrito = conn.prepareStatement(query);
                queryCarrito.setInt(1, nPedido);
                ResultSet resultCarrito = queryCarrito.executeQuery();
                while (resultCarrito.next()) {
                    total = resultCarrito.getDouble("total");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return total;
    }

    // Encriptar contraseña en SHA-256
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
