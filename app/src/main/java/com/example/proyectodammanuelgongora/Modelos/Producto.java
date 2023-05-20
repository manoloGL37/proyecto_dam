package com.example.proyectodammanuelgongora.Modelos;

import java.io.Serializable;
import java.sql.Blob;

public class Producto implements Serializable {

    private int idProducto;
    private String nombreProd;
    private String categoria;
    private byte[] imagen;
    private String descripcion;
    private String talla;
    private int stock;
    private String grupo_producto;
    private double precio;
    private int numPedido;

    public Producto() {
    }

    // Para carrito

    public Producto(String nombreProd, double precio, int numPedido) {
        this.nombreProd = nombreProd;
        this.precio = precio;
        this.numPedido = numPedido;
    }

    public Producto(int id_producto, String nombre_prod, String categoria, byte[] imagen, String descripcion, String talla, int stock, String grupo_producto, double precio) {
        this.idProducto = id_producto;
        this.nombreProd = nombre_prod;
        this.categoria = categoria;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.talla = talla;
        this.stock = stock;
        this.grupo_producto = grupo_producto;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombreProd='" + nombreProd + '\'' +
                ", categoria='" + categoria + '\'' +
                ", imagen=" + imagen +
                ", descripcion='" + descripcion + '\'' +
                ", talla='" + talla + '\'' +
                ", stock=" + stock +
                ", grupo_producto='" + grupo_producto + '\'' +
                ", precio=" + precio +
                ", numPedido=" + numPedido +
                '}';
    }
}
