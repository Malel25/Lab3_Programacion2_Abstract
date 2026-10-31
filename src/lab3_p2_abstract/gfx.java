
package lab3_p2_abstract;
import javax.swing.*;
import java.awt.*;

public class gfx extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(gfx.class.getName());
    private CardLayout cartas;
    private JPanel panelPrincipal;
    
    private JPanel panelInicio;
    private JTextField txtJugador1;
    private JTextField txtJugador2;
    private JButton btnIniciar;
    private JButton btnSalirApp;
    
    private JPanel panelJuego;
    private JLabel lblTurno;
    private JLabel lblPuntaje1;
    private JLabel lblPuntaje2;
    private JButton[][] botonesTablero;
    private JButton btnVolverMenu;
    
    
    private ControlMemoria control;
    private int primerFila = -1, primerCol = -1;
    private boolean esperandoSegundaCarta = false;
    private boolean bloqueadoPorPausa = false;
    
    public gfx() {
        control = new ControlMemoria();
        initComponents();
        configVentana();
    }
    
    private void configVentana(){
        setTitle("Juego de Memoria Pokemon");
        setPreferredSize(new Dimension(750,700));
        
        cartas = new CardLayout();
        panelPrincipal = new JPanel(cartas);
        
        pantallaInicial();
        pantallaJuego();
        
        panelPrincipal.add(panelInicio,"INICIO");
        panelPrincipal.add(panelJuego,"JUEGO");
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        
        cartas.show(panelPrincipal,"INICIO");
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void pantallaInicial(){
        panelInicio = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("JUEGO DE MEMORIA POKEMON", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelInicio.add(lblTitulo,gbc);
        
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panelInicio.add(new JLabel("Nombre Jugador 1:"),gbc);
        txtJugador1 = new JTextField(15);
        gbc.gridx = 1;
        panelInicio.add(txtJugador1, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        panelInicio.add(new JLabel("Nombre Jugador 2:"), gbc);
        txtJugador2 = new JTextField(15);
        gbc.gridx = 1;
        panelInicio.add(txtJugador2, gbc);

        btnIniciar = new JButton("Iniciar Partida");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panelInicio.add(btnIniciar, gbc);
        
        btnSalirApp = new JButton("Salir");
        btnSalirApp.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalirApp.setBackground(new Color(220, 80, 80));
        btnSalirApp.setForeground(Color.WHITE);
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        panelInicio.add(btnSalirApp, gbc);
        
        btnIniciar.addActionListener(e -> {
            String p1 = txtJugador1.getText().trim();
            String p2 = txtJugador2.getText().trim();
            
            if (p1.isEmpty() || p2.isEmpty()){
                JOptionPane.showMessageDialog(this,"Los nombres de los jugadores no pueden estar vacios...", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (p1.equalsIgnoreCase(p2)){
                JOptionPane.showMessageDialog(this, "Los nombres de los jugadores deben ser diferentes.", "Error de Validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            control.iniciarJuego(p1, p2);
            actualizarMarcadores();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    botonesTablero[i][j].setText("?");
                    botonesTablero[i][j].setEnabled(true);
                }
            }
            
            cartas.show(panelPrincipal, "JUEGO");
        });
        
        btnSalirApp.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Estás seguro de que deseas cerrar el programa?", 
                "Salir", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    private void pantallaJuego(){
        panelJuego = new JPanel(new BorderLayout(10,10));
        panelJuego.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        
        JPanel panelSuperiorC = new JPanel(new BorderLayout(10,10));
        
        JPanel panelSuperior = new JPanel(new GridLayout(1,3,10,10));
        lblPuntaje1 = new JLabel("Jugador 1: 0 pts",JLabel.CENTER);
        lblPuntaje1.setFont(new Font("Arial",Font.BOLD, 13));
        
        lblTurno = new JLabel("Turno: Jugador 1",JLabel.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 15));
        lblTurno.setForeground(new Color(0,102,204));
        
        lblPuntaje2 = new JLabel("Jugador 2: 0 pts",JLabel.CENTER);
        lblPuntaje2.setFont(new Font("Arial",Font.BOLD, 13));

        panelSuperior.add(lblPuntaje1);
        panelSuperior.add(lblTurno);
        panelSuperior.add(lblPuntaje2);
        
        btnVolverMenu = new JButton("Volver al Menú");
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 11));
        btnVolverMenu.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Deseas volver al menú principal? Se perderá el progreso de la partida actual.", 
                "Confirmar salida", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                cartas.show(panelPrincipal, "INICIO");
            }
        });
        
        panelSuperiorC.add(panelSuperior, BorderLayout.CENTER);
        panelSuperiorC.add(btnVolverMenu, BorderLayout.EAST);
        
        panelJuego.add(panelSuperiorC, BorderLayout.NORTH);
        
        JPanel panelTablero = new JPanel(new GridLayout(6, 6, 6, 6));
        botonesTablero = new JButton[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton btn = new JButton("?");
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                final int fila = i;
                final int col = j;
                
                
                btn.addActionListener(e -> {
                    if (bloqueadoPorPausa) {
                        return;
                    }
                    Carta cartaActual = control.getCarta(fila,col);
                    
                    if (cartaActual.isDescubierta() || cartaActual.isEmparejada()){
                        return;
                    }
                    if(!esperandoSegundaCarta){
                        primerFila = fila;
                        primerCol = col;
                        cartaActual.mostrar();
                        btn.setText(cartaActual.getnombrePokemon());
                        esperandoSegundaCarta = true;
                    } else {
                        if (fila == primerFila && col == primerCol){
                            return;
                        }
                        final int f1 = primerFila;
                        final int c1 = primerCol;
                        cartaActual.mostrar();
                        btn.setText(cartaActual.getnombrePokemon());
                        esperandoSegundaCarta=false;
                        
                        boolean acierto = control.verificarPareja(f1, c1, fila, col);
                        if(acierto){
                            actualizarMarcadores();
                            if(control.finalizarPartida()){
                                Jugador[] jrs = control.getJugadores();
                                String mensaje = "¡Partida Finalizada!\n";
                                if (jrs[0].getPuntaje() > jrs[1].getPuntaje()) {
                                    mensaje += "Ganador: " + jrs[0].getNombre() + " con " + jrs[0].getPuntaje() + " puntos.";
                                } else if (jrs[1].getPuntaje() > jrs[0].getPuntaje()) {
                                    mensaje += "Ganador: " + jrs[1].getNombre() + " con " + jrs[1].getPuntaje() + " puntos.";
                                } else {
                                    mensaje += "Empate! Ambos obtuvieron " + jrs[0].getPuntaje() + " puntos.";
                                }
                                mensaje += "\n\n¿Deseas iniciar una nueva partida?";
                                
                                int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Fin del Juego", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                if (opcion == JOptionPane.YES_OPTION) {
                                    cartas.show(panelPrincipal, "INICIO"); 
                                }
                            }
                        } else {
                            bloqueadoPorPausa = true;
                            Timer timerPausa = new Timer(1000, ev -> {
                                control.getCarta(f1, c1).ocultar();
                                control.getCarta(fila, col).ocultar();
                                
                                botonesTablero[f1][c1].setText("?");
                                botonesTablero[fila][col].setText("?");
                                
                                control.alternarTurno();
                                actualizarMarcadores();
                                bloqueadoPorPausa = false;
                            });
                            timerPausa.setRepeats(false);
                            timerPausa.start();
                        }
                    }
                });
                
                botonesTablero[i][j] = btn;
                panelTablero.add(btn);
            }
        }
        panelJuego.add(panelTablero, BorderLayout.CENTER);
    }
    
    private void actualizarMarcadores() {
        Jugador[] jrs = control.getJugadores();
        lblPuntaje1.setText(jrs[0].getNombre() + ": " + jrs[0].getPuntaje() + " pts");
        lblPuntaje2.setText(jrs[1].getNombre() + ": " + jrs[1].getPuntaje() + " pts");
        lblTurno.setText("Turno: " + control.getJugadorActual().getNombre());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new gfx().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
