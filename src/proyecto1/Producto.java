/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author gabsu
 */
public class Producto {
    public String codigo;
    public String nombre;
    public String categoria;
    public double precio;
    public int stock;

    public Producto() {}

    public Producto(String codigo, String nombre, String categoria, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | Q%.2f | %d", codigo, nombre, categoria, precio, stock);
    }
}
