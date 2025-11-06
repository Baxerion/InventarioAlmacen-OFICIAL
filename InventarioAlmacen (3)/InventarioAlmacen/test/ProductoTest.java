import modelo.Producto;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

// Pruebas unitarias para la clase Producto
public class ProductoTest {

    // Verifica que agregar stock suma correctamente al valor inicial
    @Test
    public void testAgregarStock() {
        Producto p = new Producto("bebida", 7);
        p.agregarStock(5); // Se agregan 5 unidades
        assertEquals(12, p.getStock()); // Se espera un total de 12 unidades
    }

    // Verifica que restar stock funciona correctamente cuando hay suficiente cantidad
    @Test
    public void testRestarStockCorrecto() {
        Producto p = new Producto("bebida", 10);
        boolean resultado = p.restarStock(3); // Se intenta vender 3 unidades
        assertTrue(resultado); // La operación debe ser exitosa
        assertEquals(7, p.getStock()); // El stock debe disminuir a 7
    }

    // Verifica que no se puede restar más stock del disponible
    @Test
    public void testRestarStockInsuficiente() {
        Producto p = new Producto("bebida", 5);
        boolean resultado = p.restarStock(6); // Se intenta vender más de lo disponible
        assertFalse(resultado); // La operación debe fallar
        assertEquals(5, p.getStock()); // El stock debe mantenerse sin cambios
    }

    // Verifica que se detecta correctamente el estado crítico (stock ≤ 5)
    @Test
    public void testEsCritico() {
        Producto p = new Producto("bebida", 5);
        assertTrue(p.esCritico()); // El producto debe estar en estado crítico
    }

    // Verifica que se puede eliminar un producto de una lista de inventario
    @Test
    public void testEliminarProductoDeLista() {
        Producto p1 = new Producto("bebida", 10);
        Producto p2 = new Producto("pan", 5);

        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(p1); // Se agrega "bebida"
        productos.add(p2); // Se agrega "pan"

        assertEquals(2, productos.size()); // Se verifica que hay 2 productos

        productos.remove(p2); // Se elimina "pan" de la lista

        assertEquals(1, productos.size()); // Se espera que quede solo 1 producto
        assertEquals("bebida", productos.get(0).getNombre()); // Se verifica que el producto restante es "bebida"
    }
}
