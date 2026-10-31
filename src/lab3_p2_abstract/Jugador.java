/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_p2_abstract;

/**
 *
 * @author ANGILI PALACIOS
 */
public class Jugador {
   private String nombre;
   private int aciertos;
   private int puntaje;
   
   public Jugador(String nombre){
       this.nombre = nombre;
       this.aciertos = 0;
       this.puntaje = 0;
       
   }
   public String getNombre(){
       return nombre;
   }
   
   public int getAciertos(){
       return aciertos;
   }
   public int getPuntaje(){
       return puntaje;
   }
   
   public void sumarAcierto(int puntos){
       this.aciertos++;
       this.puntaje += puntos;
   }
   public void reiniciar(){
       this.aciertos = 0;
       this.puntaje = 0;
   }
}
