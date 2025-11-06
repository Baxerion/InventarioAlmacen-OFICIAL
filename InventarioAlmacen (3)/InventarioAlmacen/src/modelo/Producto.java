package modelo;

// Clase que representa un producto con nombre y stock
public class Producto {
    private String nombre;
    private int stock;

    // Constructor: inicializa nombre y stock
    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.stock = stock;
    }

    // Devuelve el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // Devuelve el stock actual
    public int getStock() {
        return stock;
    }

    // Suma cantidad al stock
    public void agregarStock(int cantidad) {
        stock += cantidad;
    }

    // Resta cantidad del stock si hay suficiente
    public boolean restarStock(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
            return true;
        }
        return false;
    }

    // Verifica si el stock es crítico (≤ 5 unidades)
    public boolean esCritico() {
        return stock <= 5;
    }
}
