
package lab3_p2_abstract;

import java.util.Random;

public class ControlMemoria extends ControlJuego {
    private Jugador [] jugadores;
    private int turnoActual;
    
    private Carta[][] tablero;
    
    private final String[] pokemon = {
        "Amonita", "Hoot-Hoot", "Bulbasaur", "Pikachu", "Hypotrempe", "Sabelette",
        "Coconfort", "Charmander", "Squirtle", "Jigglypuff", "Meowth", "Psyduck",
        "Machop", "Geodude", "Gastly", "Onix", "Cubone", "Snorlax"
    };
    
    private int parejasEncontradas;
    
    public ControlMemoria(){
        jugadores = new Jugador[2];
        tablero = new Carta[6][6];
        parejasEncontradas = 0;
        turnoActual = 0;
    }
    
    @Override
    public void iniciarJuego(String nombre1, String nombre2) {
        jugadores[0] = new Jugador(nombre1);
        jugadores[1] = new Jugador(nombre2);
        turnoActual = 0;
        parejasEncontradas = 0;
        
        // Inicializar y mezclar el tablero
        construirMezclarTablero();
    }
    
    private void construirMezclarTablero(){
        Carta[] listaCartasTemp = new Carta[36];
        int index = 0;
        for (int i = 0; i < 18; i++) {
            boolean especial = (i<2);
            String nomPokemon= pokemon[i];
            Carta carta1 = especial ? new CartaEspecial(nomPokemon) : new CartaEstandar(nomPokemon);          
            Carta carta2 = (Carta) carta1.clone();
            
            listaCartasTemp[index++] = carta1;
            listaCartasTemp[index++] = carta2;
        }
        mezclarFisherYates(listaCartasTemp, listaCartasTemp.length - 1);
        index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = listaCartasTemp[index++];
                tablero[i][j].ocultar();
            }
        }
    }
    
    private void mezclarFisherYates(Carta [] arreglo, int indice){
        if(indice <= 0){
            return;
        }
        Random r = new Random();
        int aleatorio = r.nextInt(indice+1);
        
        Carta temp = arreglo[indice];
        arreglo[indice]=arreglo[aleatorio];
        arreglo[aleatorio]=temp;
        mezclarFisherYates(arreglo,indice-1);
    }
    
    public void alternarTurno() {
        turnoActual = (turnoActual == 0) ? 1 : 0;
    }
    
    public boolean verificarPareja(int fila1, int col1, int fila2, int col2){
        Carta c1 =tablero[fila1][col1];
        Carta c2 = tablero[fila2][col2];
        
        c1.mostrar();
        c2.mostrar();
        if (c1.getnombrePokemon().equalsIgnoreCase(c2.getnombrePokemon())) {
            c1.marcarEmparejada();
            c2.marcarEmparejada();
            
            
            Jugador actual = jugadores[turnoActual];
            actual.sumarAcierto(c1.getPuntos() + c2.getPuntos());
            
            parejasEncontradas++;
            return true; 
        } else {
            return false; 
        }
    }
    
    public boolean finalizarPartida() {
        int totalEmparejadas = contarEmparejadas(0, 0);
        return totalEmparejadas >= 36; 
    }
    
    private int contarEmparejadas(int fila, int col){
        if (fila >= 6) {
            return 0;
        }
        
        int cuentaActual = tablero[fila][col].isEmparejada() ? 1 : 0;
        
        int siguienteCol = col + 1;
        int siguienteFila = fila;
        if (siguienteCol >= 6) {
            siguienteCol = 0;
            siguienteFila = fila + 1;
        }
        return cuentaActual + contarEmparejadas(siguienteFila, siguienteCol);
    }
    
    public Jugador getJugadorActual() {
        return jugadores[turnoActual];
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public Carta getCarta(int fila, int col) {
        return tablero[fila][col];
    }
}
