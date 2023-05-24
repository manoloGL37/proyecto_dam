package com.example.proyectodammanuelgongora.Modelos;

import java.util.Date;

public class Pedido {

    private int id;
    private int idPedido;
    private double totalPedido;
    private int idDireccion;
    private Date fechaPedido;

    public Pedido(int propietarioPedido, double totalPedido, int idDireccion, Date fechaPedido) {
        this.idPedido = propietarioPedido;
        this.totalPedido = totalPedido;
        this.idDireccion = idDireccion;
        this.fechaPedido = fechaPedido;
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
