package uvg.edu.gt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Para leer el archivo txt y construir el grafo
        Grafo grafo = leerGrafoDesdeArchivo("src//main//java//uvg//edu//gt//guategrafo.txt");

        // Para calcular la distancia más corta entre todos los pares de ciudades
        grafo.aplicarFloydWarshall();

        // Menú
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    // Calcular y mostrar la ruta más corta entre dos ciudades
                    calcularRutaMasCorta(grafo);
                    break;
                case 2:
                    // Calcular y mostrar la ciudad que queda en el centro del grafo
                    calcularCentroGrafo(grafo);
                    break;
                case 3:
                    // Modificar el grafo según la entrada del usuario
                    modificarGrafo(grafo);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Método para leer el grafo desde un archivo
    private static Grafo leerGrafoDesdeArchivo(String nombreArchivo) {
        Grafo grafo = new Grafo();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String ciudad1 = partes[0].trim();
                String ciudad2 = partes[1].trim();
                int distancia = Integer.parseInt(partes[2].trim());
                grafo.agregarConexion(ciudad1, ciudad2, distancia);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return grafo;
    }

    private static void mostrarMenu() {
        System.out.println("Opciones:");
        System.out.println("1. Calcular ruta más corta entre dos ciudades.");
        System.out.println("2. Calcular la ciudad que queda en el centro del grafo.");
        System.out.println("3. Modificar el grafo.");
        System.out.println("4. Finalizar programa.");
        System.out.print("Seleccione una opción: ");
    }

    // Método para leer un entero desde la entrada estándar
    private static int leerEntero() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // Método para calcular y mostrar la ruta más corta entre dos ciudades
    private static void calcularRutaMasCorta(Grafo grafo) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ciudad de origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ingrese la ciudad de destino: ");
        String destino = scanner.nextLine();
        System.out.println(grafo.rutaMasCorta(origen, destino));
    }

    // Método para calcular y mostrar la ciudad que queda en el centro del grafo
    private static void calcularCentroGrafo(Grafo grafo) {
        System.out.println(grafo.centroGrafo());
    }

    // Método para modificar el grafo según la entrada del usuario
    private static void modificarGrafo(Grafo grafo) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ciudad de origen: ");
        String ciudad1 = scanner.nextLine();
        System.out.print("Ingrese la ciudad de destino: ");
        String ciudad2 = scanner.nextLine();
        System.out.print("Ingrese la distancia entre las ciudades (en KM): ");
        int distancia = scanner.nextInt();
        // Modificar la conexión en el grafo
        grafo.modificarConexion(ciudad1, ciudad2, distancia);
        System.out.println("Modificación realizada.");
    }
}
