package com.example.proyectodammanuelgongora.Database;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.proyectodammanuelgongora.Modelos.Direccion;
import com.example.proyectodammanuelgongora.Modelos.Pedido;
import com.example.proyectodammanuelgongora.Modelos.PedidoDetalles;
import com.example.proyectodammanuelgongora.Modelos.Producto;
import com.example.proyectodammanuelgongora.Modelos.Publicacion;
import com.example.proyectodammanuelgongora.Modelos.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

public class DataBase {

    String bd = "stylepeak";
    String url = "jdbc:mysql://192.168.0.193:3306/"; // Cambiar segun direccion de base de datos
    String user = "root";
    String password = "proyectoDAM";
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
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean actualizarUsuario(Usuario usuario) {
        boolean ok = false;
        try {

            String query = "UPDATE Usuario SET nombre = ?, nombre_usuario = ?, rol = ? WHERE id = ?";

            PreparedStatement updateUser = conn.prepareStatement(query);
            updateUser.setString(1, usuario.getNombre());
            updateUser.setString(2, usuario.getNombreUsuario());
            updateUser.setInt(3, usuario.getRol());
            updateUser.setInt(4, usuario.getIdUser());
            updateUser.executeUpdate();

            ok = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
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
                usuario.setEmail(resultUsuario.getString("email"));
                usuario.setNombreUsuario(resultUsuario.getString("nombre_usuario"));
                usuario.setRol(resultUsuario.getInt("rol"));
            }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return usuario;
    }

    public ArrayList<Usuario> verUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        if (conn != null) {
            try {
                String query = "SELECT u.*, r.nombre as rol_name FROM usuario u INNER JOIN roles r ON u.rol = r.id";

                PreparedStatement queryUsuarios = conn.prepareStatement(query);
                ResultSet resultUsuarios = queryUsuarios.executeQuery();
                while (resultUsuarios.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUser(resultUsuarios.getInt("id"));
                    usuario.setNombre(resultUsuarios.getString("nombre"));
                    usuario.setEmail(resultUsuarios.getString("email"));
                    usuario.setNombreUsuario(resultUsuarios.getString("nombre_usuario"));
                    usuario.setRolName(resultUsuarios.getString("rol_name"));
                    listaUsuarios.add(usuario);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        return listaUsuarios;
    }

    public void eliminarUsuario(int idUser) {
        try {
            String query = "DELETE FROM Usuario WHERE id = ?";

            PreparedStatement queryEliminarUsuario = conn.prepareStatement(query);
            queryEliminarUsuario.setInt(1, idUser);
            queryEliminarUsuario.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Crear un nuevo producto
    public boolean crearProducto(Producto prod) {
        try {

            String query = "INSERT INTO producto(nombre_prod, categoria, imagen, descripcion, stock, precio) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement queryProd = conn.prepareStatement(query);
            queryProd.setString(1, prod.getNombreProd());
            queryProd.setString(2, prod.getCategoria());
            queryProd.setBytes(3, prod.getImagen());
            queryProd.setString(4, prod.getDescripcion());
            queryProd.setInt(5, prod.getStock());
            queryProd.setDouble(6, prod.getPrecio());

            queryProd.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Producto> verProductos() {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                String query = "SELECT * FROM producto";

                PreparedStatement queryProductos = conn.prepareStatement(query);
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id");
                    String nombre_prod = resultProductos.getString("nombre_prod");
                    String cat = resultProductos.getString("categoria");
                    byte[] imagen = resultProductos.getBytes("imagen");
                    String descripcion = resultProductos.getString("descripcion");
                    int stock = resultProductos.getInt("stock");
                    double precio = resultProductos.getDouble("precio");
                    productos.add(new Producto(id_producto, nombre_prod, cat, imagen, descripcion, stock, precio));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return productos;
    }

    public boolean actualizarProducto(int idProd, Producto prod) {
        try {

            String query = "UPDATE Producto SET nombre_prod = ?, categoria = ?, imagen = ?, descripcion = ?, stock = ?, precio = ? WHERE id = ?";

            PreparedStatement queryUpdateProducto = conn.prepareStatement(query);
            queryUpdateProducto.setString(1, prod.getNombreProd());
            queryUpdateProducto.setString(2, prod.getCategoria());
            queryUpdateProducto.setBytes(3, prod.getImagen());
            queryUpdateProducto.setString(4, prod.getDescripcion());
            queryUpdateProducto.setInt(5, prod.getStock());
            queryUpdateProducto.setDouble(6, prod.getPrecio());
            queryUpdateProducto.setInt(7, idProd);
            queryUpdateProducto.executeUpdate();

            return true;

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    public boolean eliminarProducto(int idProd) {
        try {
            String query = "DELETE FROM Producto WHERE id = ?";

            PreparedStatement queryEliminarProducto = conn.prepareStatement(query);
            queryEliminarProducto.setInt(1, idProd);
            queryEliminarProducto.executeUpdate();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    // Ver todos los productos por categoria
    public ArrayList<Producto> verProductos(String categoria) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        if (conn != null) {
            try {
                String query = "SELECT * FROM producto WHERE categoria = ?";

                PreparedStatement queryProductos = conn.prepareStatement(query);
                queryProductos.setString(1, categoria);
                ResultSet resultProductos = queryProductos.executeQuery();
                while (resultProductos.next()) {
                    int id_producto = resultProductos.getInt("id");
                    String nombre_prod = resultProductos.getString("nombre_prod");
                    String cat = resultProductos.getString("categoria");
                    byte[] imagen = resultProductos.getBytes("imagen");
                    String descripcion = resultProductos.getString("descripcion");
                    int stock = resultProductos.getInt("stock");
                    double precio = resultProductos.getDouble("precio");
                    productos.add(new Producto(id_producto, nombre_prod, cat, imagen, descripcion, stock, precio));
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
                String query = "SELECT * FROM producto WHERE id = ?";

                PreparedStatement queryProducto = conn.prepareStatement(query);
                queryProducto.setInt(1, id);
                ResultSet resultProducto = queryProducto.executeQuery();
                if (resultProducto.next()) {
                    int id_producto = resultProducto.getInt("id");
                    String nombre_prod = resultProducto.getString("nombre_prod");
                    String categoria = resultProducto.getString("categoria");
                    byte[] imagen = resultProducto.getBytes("imagen");
                    String descripcion = resultProducto.getString("descripcion");
                    int stock = resultProducto.getInt("stock");
                    double precio = resultProducto.getDouble("precio");
                    producto = new Producto(id_producto, nombre_prod, categoria, imagen, descripcion, stock, precio);
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
            String fechaSubida = ZonedDateTime.now().format(formatter);

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

    // Ver publicaciones de un usuario
    public ArrayList<Publicacion> verPublicaciones(int idUser) {
        ArrayList<Publicacion> publicaciones = new ArrayList<Publicacion>();
        if (conn != null) {
            try {
                String query = "SELECT p.*, us.nombre_usuario FROM publicacion p INNER JOIN usuario us ON us.id = p.id_propietario WHERE p.id_propietario = ? ORDER BY fecha_subida DESC";

                PreparedStatement queryPublicaciones = conn.prepareStatement(query);
                queryPublicaciones.setInt(1, idUser);
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

    public void eliminarUnaPublicacion(int idUsuario, int idPublicacion) {
        try {

            String query = "DELETE FROM publicacion WHERE id_propietario = ? AND id_publicacion = ?";

            PreparedStatement queryBorrarPublicacion = conn.prepareStatement(query);
            queryBorrarPublicacion.setInt(1, idUsuario);
            queryBorrarPublicacion.setInt(2, idPublicacion);
            queryBorrarPublicacion.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Comprobar si el usuario ya ha dado mg anteriormente a la publicacion
    public boolean comprobarLike(int idUser, int idPublicacion) {
        boolean darLike = true;
        if (conn != null) {
            try {
                String query = "SELECT * FROM mg WHERE id_usuario = ? AND id_publicacion = ?";

                PreparedStatement queryPublicaciones = conn.prepareStatement(query);
                queryPublicaciones.setInt(1, idUser);
                queryPublicaciones.setInt(2, idPublicacion);
                ResultSet resultPublicaciones = queryPublicaciones.executeQuery();
                if (resultPublicaciones.next()) {
                    darLike = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return darLike;
    }

    // Incrementar likes
    public void incrementarLikes(int likes, int idPublicacione, int idUser) {
        if (conn != null) {
            try {
                String query = "UPDATE Publicacion SET likes = ? WHERE id_publicacion = ?";

                PreparedStatement queryLikes = conn.prepareStatement(query);
                queryLikes.setInt(1, likes);
                queryLikes.setInt(2, idPublicacione);

                queryLikes.executeUpdate();

                String queryMg = "INSERT mg VALUES (?, ?)";
                PreparedStatement queryTabMg = conn.prepareStatement(queryMg);
                queryTabMg.setInt(1, idUser);
                queryTabMg.setInt(2, idPublicacione);

                queryTabMg.executeUpdate();
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

    public boolean enviarAlCarrito(int idUser, Producto prod) {
        boolean ok = false;
        try {

            boolean estaCarrito = comprobarCarrito(idUser, prod.getIdProducto());
            if (!estaCarrito) {

                String query = "INSERT INTO carrito(propietario_carrito, id_producto) VALUES (?, ?)";

                PreparedStatement queryEnviarCarrito = conn.prepareStatement(query);
                queryEnviarCarrito.setInt(1, idUser);
                queryEnviarCarrito.setInt(2, prod.getIdProducto());

                queryEnviarCarrito.executeUpdate();
                ok = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }

    private boolean comprobarCarrito(int idUser, int idProd) {
        boolean estaCarrito = false;

        String query = "SELECT id_producto FROM carrito WHERE propietario_carrito = ? AND id_producto = ?";

        try {
        PreparedStatement queryEstaCarrito = conn.prepareStatement(query);
        queryEstaCarrito.setInt(1, idUser);
        queryEstaCarrito.setInt(2, idProd);
        ResultSet resultEstaCarrito = queryEstaCarrito.executeQuery();

        if (resultEstaCarrito.next()) {
            estaCarrito = true;
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estaCarrito;
    }

    public ArrayList<Producto> verCarrito(int id) {
        ArrayList<Producto> carrito = new ArrayList<Producto>();
        if (conn != null) {
            try {
                String query = "SELECT id_producto, precio, nombre_prod FROM carrito c INNER JOIN producto p ON c.id_producto = p.id WHERE propietario_carrito = ?";

                PreparedStatement queryCarrito = conn.prepareStatement(query);
                queryCarrito.setInt(1, id);
                ResultSet resultCarrito = queryCarrito.executeQuery();
                while (resultCarrito.next()) {
                    int idProd = resultCarrito.getInt("id_producto");
                    Double precioProd = resultCarrito.getDouble("precio");
                    String nombreProducto = resultCarrito.getString("nombre_prod");
                    carrito.add(new Producto(idProd, nombreProducto, precioProd));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return carrito;
    }

    public void eliminarUnProdCarrito(int idUser, int idProd) {
        try {

            String query = "DELETE FROM carrito WHERE propietario_carrito = ? AND id_producto = ?";

            PreparedStatement queryBorrarProdCarrito = conn.prepareStatement(query);
            queryBorrarProdCarrito.setInt(1, idUser);
            queryBorrarProdCarrito.setInt(2, idProd);
            queryBorrarProdCarrito.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Direccion verDireccion(int idUser) {
        Direccion direccion = new Direccion();
        if (conn != null) {
            try {
                String query = "SELECT * FROM direccion WHERE id_usuario = ?";

                PreparedStatement queryDireccion = conn.prepareStatement(query);
                queryDireccion.setInt(1, idUser);
                ResultSet resultDireccion = queryDireccion.executeQuery();
                if (resultDireccion.next()) {
                    direccion.setIdDireccion(resultDireccion.getInt("id"));
                    direccion.setIdUsuario(resultDireccion.getInt("id_usuario"));
                    direccion.setCalle(resultDireccion.getString("calle"));
                    direccion.setNumero(resultDireccion.getString("numero"));
                    direccion.setCiudad(resultDireccion.getString("ciudad"));
                    direccion.setPais(resultDireccion.getString("pais"));
                    direccion.setCp(resultDireccion.getString("cp"));
                    direccion.setTieneDireccion(true);
                } else {
                    direccion.setTieneDireccion(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }

        return direccion;
    }

    public void insertarDireccion(Direccion direccion) {
        try {

            String query = "INSERT INTO Direccion (id_usuario, calle, numero, ciudad, pais, cp) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement queryInsertDireccion = conn.prepareStatement(query);
            queryInsertDireccion.setInt(1, direccion.getIdUsuario());
            queryInsertDireccion.setString(2, direccion.getCalle());
            queryInsertDireccion.setString(3, direccion.getNumero());
            queryInsertDireccion.setString(4, direccion.getCiudad());
            queryInsertDireccion.setString(5, direccion.getPais());
            queryInsertDireccion.setString(6, direccion.getCp());
            queryInsertDireccion.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void actualizarDireccion(Direccion direccion) {
        try {

            String query = "UPDATE Direccion SET calle = ?, numero = ?, ciudad = ?, pais = ?, cp = ? WHERE id = ?";

            PreparedStatement queryUpdateDireccion = conn.prepareStatement(query);
            queryUpdateDireccion.setString(1, direccion.getCalle());
            queryUpdateDireccion.setString(2, direccion.getNumero());
            queryUpdateDireccion.setString(3, direccion.getCiudad());
            queryUpdateDireccion.setString(4, direccion.getPais());
            queryUpdateDireccion.setString(5, direccion.getCp());
            queryUpdateDireccion.setInt(6, direccion.getIdDireccion());
            queryUpdateDireccion.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public int obtenerIdDireccion(int idUser) {
        int idDireccion = 0;

        try {

            String query = "SELECT id FROM direccion WHERE id_usuario = ?";

            PreparedStatement queryIdDireccion = conn.prepareStatement(query);
            queryIdDireccion.setInt(1, idUser);
            ResultSet resultIdDireccion = queryIdDireccion.executeQuery();
            if (resultIdDireccion.next()) {
                idDireccion = resultIdDireccion.getInt("id");
            }

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }


        return idDireccion;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertarPedido(Pedido pedido) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaPedido = LocalDateTime.now().format(formatter);

            String query = "INSERT INTO Pedido (propietario_pedido, total_pedido, id_direccion, fecha_pedido) VALUES (?, ?, ?, ?)";

            PreparedStatement queryInsertPedido = conn.prepareStatement(query);
            queryInsertPedido.setInt(1, pedido.getIdPropietario());
            queryInsertPedido.setDouble(2, pedido.getTotalPedido());
            queryInsertPedido.setInt(3, pedido.getIdDireccion());
            queryInsertPedido.setString(4, fechaPedido);
            queryInsertPedido.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Pedido> verPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        if (conn != null) {
            try {
                String query = "SELECT id, total_pedido, fecha_pedido FROM pedido ORDER BY fecha_pedido DESC";

                PreparedStatement queryPedido = conn.prepareStatement(query);
                ResultSet resultPedido = queryPedido.executeQuery();
                while (resultPedido.next()) {
                    int idPedido = resultPedido.getInt("id");
                    double totalPedido = resultPedido.getDouble("total_pedido");
                    Date fechaPedido = resultPedido.getDate("fecha_pedido");
                    pedidos.add(new Pedido(idPedido, totalPedido, fechaPedido));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return pedidos;
    }

    public ArrayList<Pedido> verPedidos(int idUser) {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        if (conn != null) {
            try {
                String query = "SELECT id, total_pedido, fecha_pedido FROM pedido WHERE propietario_pedido = ? ORDER BY fecha_pedido DESC";

                PreparedStatement queryPedido = conn.prepareStatement(query);
                queryPedido.setInt(1, idUser);
                ResultSet resultPedido = queryPedido.executeQuery();
                while (resultPedido.next()) {
                    int idPedido = resultPedido.getInt("id");
                    double totalPedido = resultPedido.getDouble("total_pedido");
                    Date fechaPedido = resultPedido.getDate("fecha_pedido");
                    pedidos.add(new Pedido(idPedido, totalPedido, fechaPedido));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return pedidos;
    }

    public int obtenerIdPedido(int idUser) {
        int idPedido = 0;
        if (conn != null) {
            try {
                String query = "SELECT id FROM Pedido WHERE propietario_pedido = ? ORDER BY id DESC LIMIT 1";

                PreparedStatement queryIdPedido = conn.prepareStatement(query);
                queryIdPedido.setInt(1, idUser);
                ResultSet resultSet = queryIdPedido.executeQuery();
                if (resultSet.next()) {
                    idPedido = resultSet.getInt("id");
                }

            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //JOptionPane.showMessageDialog(null, "No se encuntra conectado a la base de datos.");
        }
        return idPedido;
    }

    public void borrarCarrito(int idUser) {
        try {

            String query = "DELETE FROM Carrito WHERE propietario_carrito = ?";

            PreparedStatement queryDeleteCarrito = conn.prepareStatement(query);
            queryDeleteCarrito = conn.prepareStatement(query);
            queryDeleteCarrito.setInt(1, idUser);
            queryDeleteCarrito.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertarDetallesPedido(PedidoDetalles pedidoDetalles) {
        try {

            String update = "UPDATE Producto SET stock = stock - 1 WHERE id = ?";

            PreparedStatement queryUpdateStock = conn.prepareStatement(update);
            queryUpdateStock.setInt(1, pedidoDetalles.getIdProducto());
            queryUpdateStock.executeUpdate();

            String query = "INSERT INTO Pedido_producto (id_pedido, id_producto, precio) VALUES (?, ?, ?)";

            PreparedStatement queryInsertPedidoDetalles = conn.prepareStatement(query);
            queryInsertPedidoDetalles = conn.prepareStatement(query);
            queryInsertPedidoDetalles.setInt(1, pedidoDetalles.getIdPedido());
            queryInsertPedidoDetalles.setInt(2, pedidoDetalles.getIdProducto());
            queryInsertPedidoDetalles.setDouble(3, pedidoDetalles.getPrecio());
            queryInsertPedidoDetalles.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public double totalCarrito(int idUser) {
        double total = 0;
        if (conn != null) {
            try {
                String query = "SELECT SUM(precio) as total FROM carrito c INNER JOIN producto p ON p.id = c.id_producto WHERE propietario_carrito = ?";

                PreparedStatement queryCarrito = conn.prepareStatement(query);
                queryCarrito.setInt(1, idUser);
                ResultSet resultCarrito = queryCarrito.executeQuery();
                while (resultCarrito.next()) {
                    total = resultCarrito.getDouble("total");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

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
