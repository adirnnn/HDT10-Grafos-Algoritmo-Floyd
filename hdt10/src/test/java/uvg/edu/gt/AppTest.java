package uvg.edu.gt;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest 
{
 @Test
    public void testAgregarConexion() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        assertEquals(50, grafo.obtenerDistancia("Ciudad1", "Ciudad2"));
    }

    @Test
    public void testObtenerDistancia() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        assertEquals(50, grafo.obtenerDistancia("Ciudad1", "Ciudad2"));
    }

    @Test
    public void testAplicarFloydWarshall() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        grafo.agregarConexion("Ciudad1", "Ciudad3", 100);
        grafo.agregarConexion("Ciudad2", "Ciudad3", 60);
        grafo.aplicarFloydWarshall();
        assertEquals(50, grafo.obtenerDistancia("Ciudad1", "Ciudad2"));
        assertEquals(100, grafo.obtenerDistancia("Ciudad1", "Ciudad3"));
        assertEquals(60, grafo.obtenerDistancia("Ciudad2", "Ciudad3"));
    }

    @Test
    public void testRutaMasCorta() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        grafo.agregarConexion("Ciudad1", "Ciudad3", 100);
        grafo.agregarConexion("Ciudad2", "Ciudad3", 60);
        grafo.aplicarFloydWarshall();
        assertEquals("Ruta más corta: [Ciudad1, Ciudad2], distancia: 50 KM.", grafo.rutaMasCorta("Ciudad1", "Ciudad2"));
        assertEquals("Ruta más corta: [Ciudad1, Ciudad3], distancia: 100 KM.", grafo.rutaMasCorta("Ciudad1", "Ciudad3"));
        assertEquals("Ruta más corta: [Ciudad2, Ciudad3], distancia: 60 KM.", grafo.rutaMasCorta("Ciudad2", "Ciudad3"));
        assertEquals("No hay ruta disponible.", grafo.rutaMasCorta("Ciudad2", "Ciudad1"));
    }

    @Test
    public void testCentroGrafo() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        grafo.agregarConexion("Ciudad1", "Ciudad3", 100);
        grafo.agregarConexion("Ciudad2", "Ciudad3", 60);
        grafo.aplicarFloydWarshall();
        assertEquals("El centro del grafo es: Ciudad1", grafo.centroGrafo());
    }

    @Test
    public void testModificarConexion() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        grafo.modificarConexion("Ciudad1", "Ciudad2", 100);
        assertEquals(100, grafo.obtenerDistancia("Ciudad1", "Ciudad2"));
    }

    @Test
    public void testMostrarMatrizAdyacencia() {
        Grafo grafo = new Grafo();
        grafo.agregarConexion("Ciudad1", "Ciudad2", 50);
        grafo.agregarConexion("Ciudad1", "Ciudad3", 100);
        grafo.agregarConexion("Ciudad2", "Ciudad3", 60);
        grafo.aplicarFloydWarshall();
        grafo.mostrarMatrizAdyacencia(); // Esto imprime la matriz de adyacencia en la consola
    }
}
