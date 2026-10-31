/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_p2_abstract;

/**
 *
 * @author ANGILI PALACIOS
 */
public abstract  class ControlJuego {
    public abstract void iniciarJuego();
    public abstract int controlarTurnos();
    public abstract boolean verificarPareja(Carta c1, Carta c2);
    public abstract boolean finalizarPartida();
}
