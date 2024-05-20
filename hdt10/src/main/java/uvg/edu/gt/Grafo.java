package uvg.edu.gt;

import java.util.*;

public class Grafo {
    private Map<String, Map<String, Integer>> conexiones;

    public Grafo() {
        conexiones = new HashMap<>();
    }

    public void agregarConexion(String ciudad1, String ciudad2, int distancia) {
        conexiones.putIfAbsent(ciudad1, new HashMap<String, Integer>());
        conexiones.putIfAbsent(ciudad2, new HashMap<String, Integer>());
        conexiones.get(ciudad1).put(ciudad2, distancia);
    }

    public int obtenerDistancia(String ciudad1, String ciudad2) {
        return conexiones.containsKey(ciudad1) && conexiones.get(ciudad1).containsKey(ciudad2)
                ? conexiones.get(ciudad1).get(ciudad2)
                : Integer.MAX_VALUE;
    }

    public Set<String> obtenerCiudades() {
        return conexiones.keySet();
    }

    public void aplicarFloydWarshall() {
        for (String intermedia : obtenerCiudades()) {
            for (String origen : obtenerCiudades()) {
                for (String destino : obtenerCiudades()) {
                    int distanciaOrigenIntermedia = obtenerDistancia(origen, intermedia);
                    int distanciaIntermediaDestino = obtenerDistancia(intermedia, destino);
                    int distanciaOrigenDestino = obtenerDistancia(origen, destino);
                    int nuevaDistancia = distanciaOrigenIntermedia != Integer.MAX_VALUE &&
                            distanciaIntermediaDestino != Integer.MAX_VALUE ?
                            distanciaOrigenIntermedia + distanciaIntermediaDestino : Integer.MAX_VALUE;
                    if (distanciaOrigenDestino > nuevaDistancia) {
                        agregarConexion(origen, destino, nuevaDistancia);
                    }
                }
            }
        }
    }

    public String rutaMasCorta(String origen, String destino) {
        if (!conexiones.containsKey(origen) || !conexiones.containsKey(destino)) {
            return "No hay ruta disponible.";
        }
        List<String> ciudadesIntermedias = new ArrayList<>();
        int distancia = obtenerDistancia(origen, destino);
        if (distancia == Integer.MAX_VALUE) {
            return "No hay ruta disponible.";
        }
        String ciudadActual = origen;
        Set<String> visitadas = new HashSet<>(); // Para evitar ciclos infinitos
        while (!ciudadActual.equals(destino) && ciudadesIntermedias.size() < conexiones.size()) {
            ciudadesIntermedias.add(ciudadActual);
            visitadas.add(ciudadActual);
            boolean encontrado = false;
            for (String ciudad : conexiones.get(ciudadActual).keySet()) {
                if (!visitadas.contains(ciudad)) {
                    int nuevaDistancia = obtenerDistancia(ciudadActual, ciudad) + obtenerDistancia(ciudad, destino);
                    if (nuevaDistancia == distancia) {
                        ciudadActual = ciudad;
                        encontrado = true;
                        break;
                    }
                }
            }
            if (!encontrado) {
                // Si no se encontró ninguna ciudad para avanzar, salir del bucle
                break;
            }
        }
        ciudadesIntermedias.add(destino);
        return "Ruta más corta: " + ciudadesIntermedias + ", distancia: " + distancia + " KM.";
    }    

    public String centroGrafo() {
        String centro = null;
        int menorDistancia = Integer.MAX_VALUE;
        for (String ciudad : obtenerCiudades()) {
            int mayorDistancia = 0;
            for (String otraCiudad : obtenerCiudades()) {
                if (!ciudad.equals(otraCiudad)) {
                    mayorDistancia = Math.max(mayorDistancia, obtenerDistancia(ciudad, otraCiudad));
                }
            }
            if (menorDistancia > mayorDistancia) {
                menorDistancia = mayorDistancia;
                centro = ciudad;
            }
        }
        return "El centro del grafo es: " + centro;
    }

    public void modificarConexion(String ciudad1, String ciudad2, int distancia) {
        if (conexiones.containsKey(ciudad1) && conexiones.get(ciudad1).containsKey(ciudad2)) {
            conexiones.get(ciudad1).put(ciudad2, distancia);
        } else {
            System.out.println("La conexión entre " + ciudad1 + " y " + ciudad2 + " no existe.");
        }
    }

    // Método para mostrar la matriz de adyacencia
    public void mostrarMatrizAdyacencia() {
        System.out.println("Matriz de adyacencia:");
        System.out.print("\t");
        for (String ciudad : obtenerCiudades()) {
            System.out.print(ciudad + "\t");
        }
        System.out.println();
        for (String ciudadOrigen : obtenerCiudades()) {
            System.out.print(ciudadOrigen + "\t");
            for (String ciudadDestino : obtenerCiudades()) {
                int distancia = obtenerDistancia(ciudadOrigen, ciudadDestino);
                System.out.print((distancia == Integer.MAX_VALUE ? "-" : distancia) + "\t");
            }
            System.out.println();
        }
    }
}