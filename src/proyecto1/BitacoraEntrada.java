/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author gabsu
 */
public class BitacoraEntrada {
    public String fechaHora;
    public String tipoAccion;
    public boolean correcta;
    public String usuario;

    public BitacoraEntrada(String fechaHora, String tipoAccion, boolean correcta, String usuario) {
        this.fechaHora = fechaHora;
        this.tipoAccion = tipoAccion;
        this.correcta = correcta;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", fechaHora, tipoAccion, (correcta ? "OK" : "ERROR"), usuario);
    }

}
