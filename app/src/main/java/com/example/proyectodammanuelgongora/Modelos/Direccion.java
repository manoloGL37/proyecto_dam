package com.example.proyectodammanuelgongora.Modelos;

public class Direccion {

    private int idDireccion;
    private int idUsuario;
    private String calle;
    private String numero;
    private String ciudad;
    private String pais;
    private String cp;
    private boolean tieneDireccion;

    public Direccion() {
    }

    // Mostrar
    public Direccion(int idDireccion, int idUsuario, String calle, String numero, String ciudad, String pais, String cp, boolean tieneDireccion) {
        this.idDireccion = idDireccion;
        this.idUsuario = idUsuario;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cp = cp;
        this.tieneDireccion = tieneDireccion;
    }

    // Insertar
    public Direccion(int idUsuario, String calle, String numero, String ciudad, String pais, String cp) {
        this.idUsuario = idUsuario;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cp = cp;
    }

    // Update
    public Direccion(int idDireccion ,int idUsuario, String calle, String numero, String ciudad, String pais, String cp) {
        this.idDireccion = idDireccion;
        this.idUsuario = idUsuario;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cp = cp;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public boolean isTieneDireccion() {
        return tieneDireccion;
    }

    public void setTieneDireccion(boolean tieneDireccion) {
        this.tieneDireccion = tieneDireccion;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", idUsuario=" + idUsuario +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", cp='" + cp + '\'' +
                ", tieneDireccion=" + tieneDireccion +
                '}';
    }
}
