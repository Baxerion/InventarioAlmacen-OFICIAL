package vista;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Producto;

// Ventana principal del sistema de inventario
public class VentanaInventario extends JFrame {
    // Campos de entrada para nombre y cantidad
    private JTextField campoNombre, campoCantidad;

    // Área de texto para mostrar el inventario
    private JTextArea areaInventario;

    // Lista dinámica que almacena los productos registrados
    private ArrayList<Producto> productos = new ArrayList<>();

    public VentanaInventario() {
        // Configuración básica de la ventana
        setTitle("Inventario Almacén");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        // Panel superior con campos de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(2, 2));
        panelEntrada.add(new JLabel("Nombre del producto:"));
        campoNombre = new JTextField();
        panelEntrada.add(campoNombre);
        panelEntrada.add(new JLabel("Cantidad:"));
        campoCantidad = new JTextField();
        panelEntrada.add(campoCantidad);
        add(panelEntrada, BorderLayout.NORTH);

        // Panel central con botones organizados en 3 filas
        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre botones

        // Botones de acción
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnVender = new JButton("Vender");
        JButton btnVerInventario = new JButton("Ver Inventario");
        JButton btnVerCritico = new JButton("Ver Stock Crítico");
        JButton btnEliminar = new JButton("Eliminar");

        // Tamaño uniforme para todos los botones
        Dimension botonGrande = new Dimension(200, 50);
        btnRegistrar.setPreferredSize(botonGrande);
        btnVender.setPreferredSize(botonGrande);
        btnVerInventario.setPreferredSize(botonGrande);
        btnVerCritico.setPreferredSize(botonGrande);
        btnEliminar.setPreferredSize(botonGrande);

        // Fila 1: Registrar | Vender
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(btnRegistrar, gbc);
        gbc.gridx = 1;
        panelBotones.add(btnVender, gbc);

        // Fila 2: Ver Inventario | Ver Stock Crítico
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelBotones.add(btnVerInventario, gbc);
        gbc.gridx = 1;
        panelBotones.add(btnVerCritico, gbc);

        // Fila 3: Eliminar centrado (ocupa dos columnas)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelBotones.add(btnEliminar, gbc);

        add(panelBotones, BorderLayout.CENTER);

        // Panel inferior con área de resultados
        areaInventario = new JTextArea();
        areaInventario.setEditable(false); // Solo lectura
        areaInventario.setRows(8);
        areaInventario.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente alineada
        add(new JScrollPane(areaInventario), BorderLayout.SOUTH);

        // Acción: Registrar producto
        btnRegistrar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            int cantidad;

            // Validación de entrada numérica
            try {
                cantidad = Integer.parseInt(campoCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                mostrar("Cantidad inválida.");
                return;
            }

            // Validación de cantidad positiva
            if (cantidad <= 0) {
                mostrar("La cantidad debe ser un número positivo.");
                return;
            }

            // Si el producto ya existe, se suma stock; si no, se crea uno nuevo
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

            // Validación de entrada numérica
            try {
                cantidad = Integer.parseInt(campoCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                mostrar("Cantidad inválida.");
                return;
            }

            // Validación de cantidad positiva
            if (cantidad <= 0) {
                mostrar("La cantidad debe ser un número positivo.");
                return;
            }

            // Búsqueda del producto y verificación de stock suficiente
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

        // Acción: Mostrar inventario completo
        btnVerInventario.addActionListener(e -> mostrarInventario());

        // Acción: Mostrar productos con stock crítico (≤ 5 unidades)
        btnVerCritico.addActionListener(e -> {
            areaInventario.setText(""); // Limpia el área gráfica
            System.out.println("[STOCK CRÍTICO]");
            for (Producto p : productos) {
                if (p.esCritico()) {
                    String linea = p.getNombre() + ": " + p.getStock() + " unidades (CRÍTICO)";
                    areaInventario.append(linea + "\n");
                    System.out.println(linea);
                }
            }
        });

        // Acción: Eliminar producto por nombre
        btnEliminar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();

            // Validación de entrada vacía
            if (nombre.isEmpty()) {
                mostrar("Ingrese el nombre del producto a eliminar.");
                return;
            }

            // Búsqueda y eliminación del producto
            Producto p = buscarProducto(nombre);
            if (p == null) {
                mostrar("Producto no encontrado.");
            } else {
                productos.remove(p);
                mostrar("Producto eliminado.");
                mostrarInventario();
            }
        });

        // Centra la ventana en pantalla
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Busca un producto por nombre ignorando mayúsculas
    private Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Muestra un mensaje en ventana emergente y en consola
    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje); // Para el usuario
        System.out.println("[INFO] " + mensaje);      // Para el programador
    }

    // Muestra el inventario completo en la interfaz y en consola
    private void mostrarInventario() {
        areaInventario.setText(""); // Limpia el área gráfica
        System.out.println("[INVENTARIO]");
        for (Producto p : productos) {
            String linea = p.getNombre() + ": " + p.getStock() + " unidades";
            areaInventario.append(linea + "\n");
            System.out.println(linea);
        }
    }
}
