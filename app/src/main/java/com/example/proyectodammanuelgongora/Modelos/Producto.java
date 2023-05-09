package com.example.proyectodammanuelgongora.Modelos;

import java.sql.Blob;

public class Producto {

    private int id_producto;
    private String nombre_prod;
    private String categoria;
    private Blob imagen;
    private String descripcion;
    private String talla;
    private int stock;
    private String grupo_producto;
    private double precio;

    public Producto() {
    }

    public Producto(int id_producto, String nombre_prod, String categoria, Blob imagen, String descripcion, String talla, int stock, String grupo_producto, double precio) {
        this.id_producto = id_producto;
        this.nombre_prod = nombre_prod;
        this.categoria = categoria;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.talla = talla;
        this.stock = stock;
        this.grupo_producto = grupo_producto;
        this.precio = precio;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getGrupo_producto() {
        return grupo_producto;
    }

    public void setGrupo_producto(String grupo_producto) {
        this.grupo_producto = grupo_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre_prod='" + nombre_prod + '\'' +
                ", categoria='" + categoria + '\'' +
                ", imagen=" + imagen +
                ", descripcion='" + descripcion + '\'' +
                ", talla='" + talla + '\'' +
                ", stock=" + stock +
                ", grupo_producto='" + grupo_producto + '\'' +
                ", precio=" + precio +
                '}';
    }
}
