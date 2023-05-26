package com.example.proyectodammanuelgongora.Modelos;

public class Usuario {

    private int idUser;
    private String nombre;
    private String nombreUsuario;
    private String contrsenya;
    private String email;
    private int rol;
    private String telefono;
    private String direccion;

    public Usuario() {
    }

    public Usuario(String nombre, String nombreUsuario, String contrsenya, String email) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrsenya = contrsenya;
        this.email = email;
    }

    public Usuario(int idUser, String nombre, String nombreUsuario, String contrsenya, String email, int rol) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrsenya = contrsenya;
        this.email = email;
        this.rol = rol;
    }

    // Actualizar
    public Usuario(int idUser, String nombre, String nombreUsuario, String email, int rol) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.rol = rol;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrsenya() {
        return contrsenya;
    }

    public void setContrsenya(String contrsenya) {
        this.contrsenya = contrsenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUser=" + idUser +
                ", nombre='" + nombre + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrsenya='" + contrsenya + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
