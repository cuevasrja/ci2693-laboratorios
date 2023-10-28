import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class DegreesOfSeparation{
    /**
     * Programa principal
     * Complejidad: O(?)
     * @param args: Argumentos de la linea de comandos (Se esperan dos nombres de personas)
     * @return void
     */
    public static void main(String[] args){
        // Creamos un grafo no dirigido
        Graph<String> graph = new AdjacencyListGraph<String>();
        /**
         * Los nombres que se van a agregar son:
         * Carlos, Juan, Ana, Pedro, Marta, Luis, Carmen, Diego, Elena, Felipe
        */
        graph.add("Carlos");
        graph.add("Juan");
        graph.add("Ana");
        graph.add("Pedro");
        graph.add("Marta");
        graph.add("Luis");
        graph.add("Carmen");
        graph.add("Diego");
        graph.add("Elena");
        graph.add("Felipe");

        // Creamos relaciones al azar (No puede ser un grafo completo ni conexo)
        // Recordar que el grafo es no dirigido por lo que si hay una relacion de A a B, tambien hay una de B a A
        int n = graph.size();
        randomGraph(graph, 2*n);

        // Mostramos el grafo
        System.out.println(graph);

        // Obtenemos los nombres de los vertices de los argumentos
        String nombre1 = args[0];
        String nombre2 = args[1];
        // Buscar el camino mas corto entre los dos vertices
        List<String> cadena = BFS(graph, nombre1, nombre2);
        // Mostramos la cadena de amistad
        System.out.println(cadena);
        // Mostramos el grado de amistad
        int gradoAmistad = SeparationDegree(cadena);
        System.out.println("El grado de amistad entre " + nombre1 + " y " + nombre2 + " es: " + gradoAmistad);

    }

    /**
     * Agrega lados al azar a un grafo
     * Complejidad: O(n)
     * @param graph: Grafo al que se le van a agregar lados
     * @param n: Numero de lados que se van a agregar
     * @return void
    */
    public static void randomGraph(Graph<String> graph, int n){
        int ladosCreados = 0;
        // Obtenemos todos los vertices del grafo y creamos un arreglo con ellos
        String[] vertices = (String[]) graph.getAllVertices().toArray(new String[0]);
        // Creamos lados al azar hasta que se hayan creado n lados
        while(ladosCreados < n){
            int i = (int) (Math.random() * vertices.length);
            int j = (int) (Math.random() * vertices.length);
            // Si el lado no existe, lo creamos
            if(graph.connect(vertices[i], vertices[j])){
                ladosCreados++;
            }
        }
    }

    /**
     * Implementacion del algoritmo de BFS para encontrar el camino mas corto entre dos vertices
     * Complejidad: O(n + m). n = numero de vertices, m = numero de lados
     * @param graph: Grafo no dirigido representado como lista de adyacencia
     * @param start: Vertice inicial
     * @param end: Vertice final
     * @return List<String>: Lista de vertices que forman el camino mas corto entre start y end
     */

    public static List<String> BFS(Graph<String> graph, String start, String end){
        // Creamos un ArrayList para guardar el camino mas corto
        List<String> cadena = new ArrayList<String>();
        // Insertamos el vertice inicial en la lista
        cadena.add(start);
        // Creamos una cola para guardar los caminos que se van a explorar
        Queue<List<String>> abiertos = new LinkedList<List<String>>();
        // Insertamos el camino inicial en la cola
        abiertos.add(cadena);
        // Creamos una lista para guardar los vertices que ya se han explorado
        List<String> cerrados = new ArrayList<String>();
        // Mientras la cola no este vacia
        while(!abiertos.isEmpty()){
            // Sacamos el primer camino de la cola
            cadena = abiertos.poll();
            // Obtenemos el ultimo vertice del camino
            String ultimo = cadena.get(cadena.size() - 1);
            // Si el ultimo vertice es el final, regresamos el camino
            if(ultimo.equals(end)){
                return cadena;
            }
            // Si el ultimo vertice no esta en la lista de vertices explorados
            if(!cerrados.contains(ultimo)){
                // Agregamos el vertice a la lista de vertices explorados
                cerrados.add(ultimo);
                // Obtenemos los vertices adyacentes al ultimo vertice
                List<String> adyacentes = graph.getVerticesConnectedTo(ultimo);
                // Para cada vertice adyacente
                for(String adyacente : adyacentes){
                    // Si el vertice no esta en la lista de vertices explorados
                    if(!cerrados.contains(adyacente)){
                        // Creamos un nuevo camino que es una copia del camino actual
                        List<String> nuevoCamino = new ArrayList<String>(cadena);
                        // Agregamos el vertice adyacente al nuevo camino
                        nuevoCamino.add(adyacente);
                        // Agregamos el nuevo camino a la cola
                        abiertos.add(nuevoCamino);
                    }
                }
            }
        }
        // Si no se encontro un camino, regresamos null
        return null;
    }

    /**
     * Calcula el grado de separacion entre dos personas
     * Complejidad: O(1)
     * @param cadena: Lista de vertices que forman el camino mas corto entre dos vertices
     * @return int: Grado de separacion entre dos personas
     */
    public static int SeparationDegree(List<String> cadena){
        if (cadena == null){
            return -1;
        }
        return cadena.size() - 1;
    }
}