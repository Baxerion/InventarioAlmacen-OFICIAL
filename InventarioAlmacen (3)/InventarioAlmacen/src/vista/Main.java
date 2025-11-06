package vista;

import javax.swing.SwingUtilities;

// Clase principal que lanza la interfaz gráfica
public class Main {
    public static void main(String[] args) {
        // Ejecuta la ventana en el hilo de interfaz gráfica
        SwingUtilities.invokeLater(() -> new VentanaInventario());
    }
}
