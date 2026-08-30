package com.example.appmantenimientonew;

public class Proformaltem {
    private String codigo;
    private String producto;
    private double precio;
    private int cantidad;

    public Proformaltem(String codigo, String producto, double precio, int cantidad) {
        this.codigo = codigo;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return "▪ [" + codigo + "] " + producto + "\n" +
                "  Cant: " + cantidad + " | P.Unit: S/. " + String.format("%.2f", precio) +
                " | Subtotal: S/. " + String.format("%.2f", getTotal());
    }
}