import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class NextToYou{
    public static void main(String[] args){
        String archivo = "Caracas.txt";
        // Leemos el archivo y obtenemos el grafo
        Graph<String> graph = leerArchivo(archivo);
        // Si el grafo es null, retornamos
        if (graph == null) return;
        // Imprimimos el grafo
        System.out.println("Grafo:");
        System.out.println(graph);

        // Obtenemos la cantidad de repartidores necesarios para cubrir todas las componentes fuertemente conexas
        int repartidores = repartidoresNecesarios(graph);
        // Imprimimos la cantidad de repartidores necesarios
        System.out.println("Repartidores necesarios: " + repartidores);
    }

    /**
     * Lee un archivo y retorna un grafo con los datos del archivo.
     * Complejidad: O(|E|*|V|) donde |E| es la cantidad de arcos y |V| es la cantidad de vértices
     * Esto es porque se itera |E| veces sobre el archivo y se itera |V| veces sobre el grafo al agregar vértices 
     * @param archivo nombre del archivo con su extensión
     * @return grafo dirigido
    */
    public static Graph<String> leerArchivo(String archivo){
        try {
            // Inicializamos el scanner
            File file = new File(archivo);
            Scanner sc = new Scanner(file);
            // Si el archivo está vacío, retornamos null
            if (!sc.hasNextLine()) {
                System.out.println("El archivo está vacío");
                sc.close();
                return null;
            }
            // Creamos el grafo
            Graph<String> graph = new AdjacencyListGraph<String>();
            // Mientras haya una línea siguiente, leemos la línea y agregamos los vértices y arcos al grafo
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(", ");
                // Si la línea no tiene 2 partes, retornamos null
                if (parts.length != 2) {
                    System.out.println("Error en el formato del archivo");
                    sc.close();
                    return null;
                }
                // Agregramos los vartices al grafo (si ya están agregados, no se agregan nuevamente)
                graph.add(parts[0]);
                graph.add(parts[1]);
                // Agregamos el arco parts[0] -> parts[1] al grafo
                graph.connect(parts[0], parts[1]);
            }
            // Cerramos el scanner y retornamos el grafo
            sc.close();
            return graph;
        } catch (Exception e) {
            // Si hay un error al leer el archivo, retornamos null
            System.out.println("Error al leer el archivo");
            System.out.println(e);
            return null;
        }
    }

    /**
     * Retorna una lista de conjuntos de vértices que representan las componentes fuertemente conexas del grafo
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
    */
    public static List<Set<String>> componentesFuertementeConexas(Graph<String> graph){
        // Inicialiamos nuestra pila de caminos cerrados y nuestro conjunto de vértices visitados
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        // Realizamos DFS en el grafo original
        for (String vertex : graph.getAllVertices()) {
            // Si el vértice no ha sido visitado, hacemos DFS
            if (!visited.contains(vertex)) {
                dfs(graph, vertex, visited, stack);
            }
        }

        // Creamos el grafo transpuesto
        Graph<String> transposedGraph = simetrico(graph);

        // Reiniciamos nuestro conjunto de vértices visitados
        visited.clear();
        // Creamos una lista de conjuntos de vértices que representan las componentes fuertemente conexas
        List<Set<String>> CFC = new ArrayList<>();

        // Realizamos DFS en el grafo transpuesto
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            // Si el vértice no ha sido visitado, hacemos DFS
            if (!visited.contains(vertex)) {
                // Creamos un conjunto de vértices que representan una componente fuertemente conexa
                Set<String> componente = new HashSet<>();
                dfs(transposedGraph, vertex, visited, componente);
                // Agregamos la componente fuertemente conexa a la lista de componentes
                CFC.add(componente);
            }
        }

        return CFC;
    }

    /**
     * DFS en un grafo dirigido
     * Complejidad: O(|V|+|E|) donde |E| es la cantidad de arcos y |V| es la cantidad de vértices
     * @param graph grafo dirigido
     * @param vertex vértice inicial
     * @param visited conjunto de vértices visitados
     * @param result conjunto de vértices visitados en orden de finalización
    */
    private static void dfs(Graph<String> graph, String vertex, Set<String> visited, Collection<String> result) {
        // Marcamos el vértice como visitado y lo agregamos al conjunto de vértices visitados en orden de finalización
        // (esto es, cuando ya no tiene vértices adyacentes sin visitar)
        visited.add(vertex);
        

        // Recorremos los vértices adyacentes al vértice actual
        for (String v : graph.getOutwardEdges(vertex)) {
            // Si el vértice no ha sido visitado, hacemos DFS
            if (!visited.contains(v)) {
                dfs(graph, v, visited, result);
            }
        }

        // Agregamos el vértice al conjunto de vértices visitados en orden de finalización
        result.add(vertex);
    }

    /**
     * Retorna el grafo simetrico de un grafo dirigido
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return grafo simetrico
    */
    private static Graph<String> simetrico(Graph<String> graph) {
        // Creamos un nuevo grafo
        Graph<String> transposedGraph = new AdjacencyListGraph<>();
        // Obtenemos los vértices del grafo original en orden de finalización
        List<String> vertices = graph.getAllVertices();

        // Agregamos los vértices al grafo simetrico
        for (String vertex : vertices) {
            transposedGraph.add(vertex);
        }

        // Conectamos los vértices del grafo simetrico
        for (String vertex : vertices) {
            // Recorremos los vértices que tengan arcos de entrada en el vértice actual.
            // Estos vértices serán los vértices de salida en el grafo simetrico.
            for (String adjacentVertex : graph.getInwardEdges(vertex)) {
                transposedGraph.connect(vertex, adjacentVertex);
            }
        }

        return transposedGraph;
    }

    /**
     * Suma la cantidad de repartidores necesarios para cubrir todas las componentes fuertemente conexas
     * Complejidad: O(|CC|) donde |CC| es la cantidad de componentes fuertemente conexas
     * @param componentes lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
     * @return cantidad de repartidores necesarios
    */
    public static int sumarRepartidores(List<Set<String>> componentes){
        // Inicializamos la cantidad de repartidores
        int repartidores = 0;
        // Recorremos las componentes fuertemente conexas
        for (Set<String> componente : componentes) {
            // Si la componente tiene 2 o menos vértices, se necesitan 10 repartidores
            // Si la componente tiene entre 3 y 5 vértices, se necesitan 20 repartidores
            // Si la componente tiene más de 5 vértices, se necesitan 30 repartidores
            if(componente.size() <= 2){
                repartidores += 10;
            } else if (componente.size() <= 5){
                repartidores += 20;
            } else {
                repartidores += 30;
            }
        }
        return repartidores;
    }

    /**
     * Imprime las componentes fuertemente conexas
     * Complejidad: O(|CC|) donde |CC| es la cantidad de componentes fuertemente conexas
     * @param componentes lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
    */
    public static void imprimirCC(List<Set<String>> componentes){
        int i = 1;
        int color = 91;
        // Recorremos las componentes fuertemente conexas
        for (Set<String> componente : componentes) {
            if (color > 96) color = 91;
            System.out.println(colorear(color, i) + ": " + componente);
            i++;
            color++;
        }
        System.out.println("");
    }

    /**
     * Retorna la cantidad de repartidores necesarios para cubrir todas las componentes fuertemente conexas
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return cantidad de repartidores necesarios
     */
    public static int repartidoresNecesarios(Graph<String> graph){
        // Obtenemos las componentes fuertemente conexas del grafo
        List<Set<String>> componentes = componentesFuertementeConexas(graph);
        // Imprimimos las componentes fuertemente conexas
        System.out.println("Componentes fuertemente conexas:");
        imprimirCC(componentes);
        // Sumamos la cantidad de repartidores necesarios para cubrir todas las componentes fuertemente conexas
        return sumarRepartidores(componentes);
    }

    /**
     * Retorna un string con el texto coloreado
     * Complejidad: O(1)
     * @param color Numero del color
     * @param i Numero del componente
     * @return String con el texto coloreado
     */
    public static String colorear(int color, int i){
        return "\u001B[" + color + "m" + "Componente " + i + "\u001B[0m";
    }
}