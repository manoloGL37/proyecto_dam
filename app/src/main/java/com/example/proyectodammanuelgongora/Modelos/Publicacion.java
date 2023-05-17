package com.example.proyectodammanuelgongora.Modelos;

import java.util.Arrays;
import java.util.Date;

public class Publicacion {

    private int idPublicacion;
    private byte[] imagen;
    private String descripcion;
    private Date fechaSubida;
    private int likes;
    private int idPropietario;
    private String nombre_usuario;

    public Publicacion() {
    }



    public Publicacion(int idPublicacion, byte[] imagen, String descripcion, Date fechaSubida, int likes, int idPropietario, String nombre_usuario) {
        this.idPublicacion = idPublicacion;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.fechaSubida = fechaSubida;
        this.likes = likes;
        this.idPropietario = idPropietario;
        this.nombre_usuario = nombre_usuario;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "idPublicacion=" + idPublicacion +
                ", imagen=" + Arrays.toString(imagen) +
                ", descripcion='" + descripcion + '\'' +
                ", fechaSubida=" + fechaSubida +
                ", likes=" + likes +
                ", idPropietario=" + idPropietario +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                '}';
    }
}
