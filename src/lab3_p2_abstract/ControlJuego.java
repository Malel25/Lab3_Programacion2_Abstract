package lab3_p2_abstract;

public abstract class ControlJuego {
    public abstract void iniciarJuego(String nombre1, String nombre2);
    public abstract void alternarTurno();
    public abstract boolean verificarPareja(int fila1, int col1, int fila2, int col2);
    public abstract boolean finalizarPartida();
}
