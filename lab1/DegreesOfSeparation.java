public class DegreesOfSeparation{
    public static void main(String[] args){
        // Creamos un grafo no dirigido
        Graph graph = new AdjacencyListGraph();
        /**
         * Los nombres que se van a agregar son:
         * Carlos, Juan, Ana, Pedro, Marta, Luis, Carmen, Diego, Elena, Felipe
        */
        graph.addVertex("Carlos");
        graph.addVertex("Juan");
        graph.addVertex("Ana");
        graph.addVertex("Pedro");
        graph.addVertex("Marta");
        graph.addVertex("Luis");
        graph.addVertex("Carmen");
        graph.addVertex("Diego");
        graph.addVertex("Elena");
        graph.addVertex("Felipe");

        // Creamos relaciones al azar (No puede ser un grafo completo ni conexo)
        // Recordar que el grafo es no dirigido por lo que si hay una relacion de A a B, tambien hay una de B a A
        graph.addEdge("Carlos", "Juan");
        graph.addEdge("Carlos", "Ana");
        graph.addEdge("Carlos", "Elena");
        graph.addEdge("Carlos", "Felipe");
        graph.addEdge("Juan", "Ana");
        graph.addEdge("Juan", "Marta");
        graph.addEdge("Juan", "Luis");
        graph.addEdge("Juan", "Carmen");
        graph.addEdge("Ana", "Pedro");
        graph.addEdge("Ana", "Luis");
        graph.addEdge("Ana", "Carmen");
        graph.addEdge("Pedro", "Marta");
        graph.addEdge("Pedro", "Carmen");
        graph.addEdge("Marta", "Luis");
        graph.addEdge("Marta", "Carmen");
        graph.addEdge("Luis", "Carmen");
        graph.addEdge("Diego", "Elena");
        graph.addEdge("Diego", "Felipe");
        graph.addEdge("Elena", "Felipe");

        String nombre1 = args[0];
        String nombre2 = args[1];
        // TODO: Implementar el algoritmo de BFS para encontrar el camino mas corto entre dos vertices
        List<String> cadena = BFS(graph, nombre1, nombre2);
        System.out.println(cadena);
        int gradoAmistad = cadena.size() - 1;
        System.out.println("El grado de amistad entre " + nombre1 + " y " + nombre2 + " es: " + gradoAmistad);

    }

    public static List<String> BFS(Graph graph, String start, String end){
        // TODO: Implementar el algoritmo de BFS para encontrar el camino mas corto entre dos vertices
    }
}