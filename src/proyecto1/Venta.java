/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author gabsu
 */
public class Venta {
    public String codigoProducto;
    public int cantidad;
    public String fechaHora;
    public double total;

    public Venta() {}

    public Venta(String codigoProducto, int cantidad, String fechaHora, double total) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("%s | %d | %s | Q%.2f", codigoProducto, cantidad, fechaHora, total);
    }
}
