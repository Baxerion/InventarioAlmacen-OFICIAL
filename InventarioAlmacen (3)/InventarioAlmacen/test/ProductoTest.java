import modelo.Producto;
import org.junit.Test;
import static org.junit.Assert.*;

// Pruebas unitarias para la clase Producto
public class ProductoTest {

    @Test
    public void testAgregarStock() {
        Producto p = new Producto("bebida", 7);
        p.agregarStock(5);
        assertEquals(12, p.getStock());
    }

    @Test
    public void testRestarStockCorrecto() {
        Producto p = new Producto("bebida", 10);
        boolean resultado = p.restarStock(3);
        assertTrue(resultado);
        assertEquals(7, p.getStock());
    }

    @Test
    public void testRestarStockInsuficiente() {
        Producto p = new Producto("bebida", 5);
        boolean resultado = p.restarStock(6);
        assertFalse(resultado);
        assertEquals(5, p.getStock());
    }

    @Test
    public void testEsCritico() {
        Producto p = new Producto("bebida", 5);
        assertTrue(p.esCritico());
    }
}
