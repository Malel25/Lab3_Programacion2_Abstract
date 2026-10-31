/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3_p2_abstract;

/**
 *
 * @author ANGILI PALACIOS
 */
public class CartaEspecial extends Carta {
    
    public CartaEspecial(String nombrePokemon){
        super(nombrePokemon);
        
    }
    
    @Override
    public int getPuntos(){
        return 2;
    }
}
