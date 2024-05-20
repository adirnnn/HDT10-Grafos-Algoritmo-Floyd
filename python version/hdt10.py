import networkx as nx
import matplotlib.pyplot as plt

#El grafo aparece al terminar el programa

def leer_grafo_desde_archivo(nombre_archivo):
    G = nx.DiGraph()
    try:
        with open(nombre_archivo, 'r') as file:
            for linea in file:
                partes = linea.strip().split(',')
                ciudad1 = partes[0].strip()
                ciudad2 = partes[1].strip()
                distancia = int(partes[2].strip())
                G.add_edge(ciudad1, ciudad2, weight=distancia)
    except IOError as e:
        print(f"Error al leer el archivo: {e}")
    return G

def mostrar_menu():
    print("Opciones:")
    print("1. Calcular ruta más corta entre dos ciudades.")
    print("2. Calcular la ciudad que queda en el centro del grafo.")
    print("3. Modificar el grafo.")
    print("4. Finalizar programa.")
    print("Seleccione una opción: ", end="")

def calcular_ruta_mas_corta(G):
    origen = input("Ingrese la ciudad de origen: ")
    destino = input("Ingrese la ciudad de destino: ")
    try:
        distancia, ruta = nx.single_source_dijkstra(G, origen, destino, weight='weight')
        print(f"Ruta más corta: {ruta}, distancia: {distancia} KM.")
    except nx.NetworkXNoPath:
        print("No hay ruta disponible.")
    except nx.NodeNotFound:
        print("Una o ambas ciudades no existen en el grafo.")

def calcular_centro_grafo(G):
    floyd_warshall = dict(nx.floyd_warshall(G, weight='weight'))
    max_distancias = {nodo: max(distancias.values()) for nodo, distancias in floyd_warshall.items()}
    centro = min(max_distancias, key=max_distancias.get)
    print(f"El centro del grafo es: {centro}")

def modificar_grafo(G):
    print("Opciones de modificación:")
    print("a. Hay interrupción de tráfico entre un par de ciudades.")
    print("b. Se establece una conexión entre ciudad1 y ciudad2 con valor de x KM de distancia.")
    opcion = input("Seleccione una opción: ")
    ciudad1 = input("Ingrese la ciudad de origen: ")
    ciudad2 = input("Ingrese la ciudad de destino: ")
    
    if opcion == 'a':
        if G.has_edge(ciudad1, ciudad2):
            G.remove_edge(ciudad1, ciudad2)
            print(f"Interrupción de tráfico entre {ciudad1} y {ciudad2} eliminada.")
        else:
            print(f"No existe una conexión entre {ciudad1} y {ciudad2}.")
    elif opcion == 'b':
        distancia = int(input("Ingrese la distancia entre las ciudades (en KM): "))
        G.add_edge(ciudad1, ciudad2, weight=distancia)
        print(f"Conexión entre {ciudad1} y {ciudad2} con {distancia} KM agregada.")
    else:
        print("Opción no válida.")

def main():
    G = leer_grafo_desde_archivo('guategrafo.txt')
    floyd_warshall = dict(nx.floyd_warshall(G, weight='weight'))  # Calcula las distancias al inicio
    
    salir = False
    while not salir:
        mostrar_menu()
        opcion = int(input())
        if opcion == 1:
            calcular_ruta_mas_corta(G)
        elif opcion == 2:
            calcular_centro_grafo(G)
        elif opcion == 3:
            modificar_grafo(G)
            floyd_warshall = dict(nx.floyd_warshall(G, weight='weight'))  # Recalcula las distancias después de modificar el grafo
        elif opcion == 4:
            salir = True
        else:
            print("Opción no válida.")
    
    # Dibuja el grafo
    pos = nx.spring_layout(G)
    labels = nx.get_edge_attributes(G, 'weight')
    nx.draw(G, pos, with_labels=True, node_color='lightblue', node_size=500, font_size=10)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)
    plt.show()

if __name__ == "__main__":
    main()