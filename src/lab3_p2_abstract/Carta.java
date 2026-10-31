/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_p2_abstract;

/**
 *
 * @author ANGILI PALACIOS
 */
public abstract class Carta implements Cloneable{
    protected boolean descubierta;
    protected boolean emparejada;
    protected String nombrePokemon;
    
    
    public Carta(String nombrePokemon){
        this.nombrePokemon = nombrePokemon;
        this.descubierta= false;
        this.emparejada = false;
    }
    
    public void mostrar(){
        this.descubierta = false;
    }
    
    public void ocultar(){
        if(!emparejada){
            this.descubierta = false;
        }
    }
    public boolean isDescubierta(){
        return descubierta;
    
    }
    public boolean isEmparejada(){
        return emparejada;
    }
            
}
