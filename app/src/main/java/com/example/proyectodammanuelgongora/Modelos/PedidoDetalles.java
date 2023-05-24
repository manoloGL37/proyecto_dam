package com.example.proyectodammanuelgongora.Modelos;

public class PedidoDetalles {

    private int id;
    private int idPedido;
    private int idProducto;
    private double precio;

    public PedidoDetalles(int idPedido, int idProducto, double precio) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
