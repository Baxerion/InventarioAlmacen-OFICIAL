package vista;

import modelo.Producto;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Ventana principal del sistema de inventario
public class VentanaInventario extends JFrame {
    private JTextField campoNombre, campoCantidad;
    private JTextArea areaInventario;
    private ArrayList<Producto> productos = new ArrayList<>();

    public VentanaInventario() {
        setTitle("Inventario Almacén");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); // Aumentamos el alto de la ventana
        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(2, 2));
        panelEntrada.add(new JLabel("Nombre del producto:"));
        campoNombre = new JTextField();
        panelEntrada.add(campoNombre);
        panelEntrada.add(new JLabel("Cantidad:"));
        campoCantidad = new JTextField();
        panelEntrada.add(campoCantidad);
        add(panelEntrada, BorderLayout.NORTH);

        // Panel de botones en cuadrícula 2x2
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVender = new JButton("Vender");
        JButton btnVerInventario = new JButton("Ver Inventario");
        JButton btnVerCritico = new JButton("Ver Stock Crítico");

        Dimension botonGrande = new Dimension(200, 50);
        btnRegistrar.setPreferredSize(botonGrande);
        btnVender.setPreferredSize(botonGrande);
        btnVerInventario.setPreferredSize(botonGrande);
        btnVerCritico.setPreferredSize(botonGrande);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnVender);
        panelBotones.add(btnVerInventario);
        panelBotones.add(btnVerCritico);
        add(panelBotones, BorderLayout.CENTER);

        // Área de resultados más alta y legible
        areaInventario = new JTextArea();
        areaInventario.setEditable(false);
        areaInventario.setRows(8);
        areaInventario.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(areaInventario), BorderLayout.SOUTH);

        // Acción: Registrar producto
        btnRegistrar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            int cantidad;
            try {
                cantidad = Integer.parseInt(campoCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                mostrar("Cantidad inválida.");
                return;
            }

            if (cantidad <= 0) {
                mostrar("La cantidad debe ser un número positivo.");
                return;
            }

            Producto existente = buscarProducto(nombre);
            if (existente != null) {
                existente.agregarStock(cantidad);
            } else {
                productos.add(new Producto(nombre, cantidad));
            }

            mostrar("Producto registrado.");
            mostrarInventario();
        });

        // Acción: Vender producto
        btnVender.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            int cantidad;
            try {
                cantidad = Integer.parseInt(campoCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                mostrar("Cantidad inválida.");
                return;
            }

            if (cantidad <= 0) {
                mostrar("La cantidad debe ser un número positivo.");
                return;
            }

            Producto p = buscarProducto(nombre);
            if (p == null) {
                mostrar("Producto no encontrado.");
            } else if (!p.restarStock(cantidad)) {
                mostrar("Stock insuficiente.");
            } else {
                mostrar("Venta registrada.");
                mostrarInventario();
            }
        });

        // Acción: Ver inventario completo
        btnVerInventario.addActionListener(e -> mostrarInventario());

        // Acción: Ver productos con stock crítico
        btnVerCritico.addActionListener(e -> {
            areaInventario.setText("");
            System.out.println("[STOCK CRÍTICO]");
            for (Producto p : productos) {
                if (p.esCritico()) {
                    String linea = p.getNombre() + ": " + p.getStock() + " unidades (CRÍTICO)";
                    areaInventario.append(linea + "\n");
                    System.out.println(linea);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Busca un producto por nombre (ignorando mayúsculas)
    private Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Muestra un mensaje emergente y lo imprime en consola
    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
        System.out.println("[INFO] " + mensaje);
    }

    // Muestra el inventario en el área de texto y en consola
    private void mostrarInventario() {
        areaInventario.setText("");
        System.out.println("[INVENTARIO]");
        for (Producto p : productos) {
            String linea = p.getNombre() + ": " + p.getStock() + " unidades";
            areaInventario.append(linea + "\n");
            System.out.println(linea);
        }
    }
}
