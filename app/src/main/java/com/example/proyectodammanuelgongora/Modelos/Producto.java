package com.example.proyectodammanuelgongora.Modelos;

import java.io.Serializable;
import java.sql.Blob;

public class Producto implements Serializable {

    private int idProducto;
    private String nombreProd;
    private String categoria;
    private byte[] imagen;
    private String descripcion;
    private int stock;
    private double precio;
    private int numPedido;

    public Producto() {
    }

    // Para carrito
    public Producto(int idProducto, String nombreProd, double precio) {
        this.idProducto = idProducto;
        this.nombreProd = nombreProd;
        this.precio = precio;
    }

    // Devolver procutos
    public Producto(int id_producto, String nombre_prod, String categoria, byte[] imagen, String descripcion, int stock, double precio) {
        this.idProducto = id_producto;
        this.nombreProd = nombre_prod;
        this.categoria = categoria;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
    }

    // Para añadir un nuevo produto
    public Producto( String nombre_prod, String categoria, byte[] imagen, String descripcion, int stock, double precio) {
        this.nombreProd = nombre_prod;
        this.categoria = categoria;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
    }

    public Producto( String nombre_prod, String categoria, String descripcion, int stock, double precio) {
        this.nombreProd = nombre_prod;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
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


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
                ", stock=" + stock +
                ", precio=" + precio +
                ", numPedido=" + numPedido +
                '}';
    }
}
