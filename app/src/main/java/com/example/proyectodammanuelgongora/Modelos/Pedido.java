package com.example.proyectodammanuelgongora.Modelos;

import java.util.Date;

public class Pedido {

    private int id;
    private int idPropietario;
    private double totalPedido;
    private int idDireccion;
    private Date fechaPedido;

    public Pedido(int propietarioPedido, double totalPedido, int idDireccion, Date fechaPedido) {
        this.idPropietario = propietarioPedido;
        this.totalPedido = totalPedido;
        this.idDireccion = idDireccion;
        this.fechaPedido = fechaPedido;
    }

    // Ver pedidos
    public Pedido(int id, double totalPedido, Date fechaPedido) {
        this.id = id;
        this.totalPedido = totalPedido;
        this.fechaPedido = fechaPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

}
