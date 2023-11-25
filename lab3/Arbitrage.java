import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Arbitrage {
    public static void main(String[] args) {
        String archivo = "tasas.txt";
        Graph<String> grafo = leerArchivo(archivo);
        System.out.println("Grafo: ");
        System.out.println(grafo);
        double dineroInicial = 1.0;
        
        String arbitraje = arbitrajePosible(grafo, dineroInicial);
        System.out.println(arbitraje);
    }

    /**
     * Lee un archivo de texto y retorna un grafo con los datos del archivo
     * Complejidad: O(|E|*log(|V|)) donde |E| es la cantidad de arcos y |V| es la cantidad de vértices.
     * Esta complejidad se debe a que se recorre el archivo que tiene |E| líneas, y el agregar un vértice es O(1).
     * Sin embargo, el agregar un arco es O(|V|) porque se debe buscar el vértice en el HashMap.
     * @param archivo Nombre del archivo
     * @return Grafo dirigido con los datos del archivo
     */
    public static Graph<String> leerArchivo(String archivo) {
        // Crear el grafo
        Graph<String> grafo = new AdjacencyListGraph<String>();
        try {
            // Inicializar el lector del archivo
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea = br.readLine();
            // Mientras haya líneas en el archivo
            while (linea != null) {
                // Separar la línea por espacios, el formato es "u v peso" donde u y v son vértices y peso es un número real (Costo del arco)
                String[] partes = linea.split(" ");
                String u = partes[0];
                String v = partes[1];
                double peso = Double.parseDouble(partes[2]);
                // Agregar los vértices al grafo
                grafo.add(u);
                grafo.add(v);
                // Conectar los vértices con el arco agregando el peso
                grafo.connect(u, v, peso);
                // Leer la siguiente línea
                linea = br.readLine();
            }
            // Cerrar el lector
            br.close();
        }
        catch (Exception e) {
            // En caso de error, imprimir el mensaje
            System.out.println("Error al leer el archivo");
            System.out.println(e.getMessage());
        }
        return grafo;
    }

    /**
     * Calcula el costo de un circuito
     * Complejidad: O(|C|) donde |C| es la cantidad de arcos del circuito
     * @param circuito Lista de arcos que forman el circuito
     * @param dineroInicial Dinero inicial (De forma predeterminada es 1.0)
     * @return Costo del circuito
     */
    public static double costo(List<Lado<String>> circuito, double dineroInicial) {
        // Inicializar el dinero actual
        double dineroActual = Double.valueOf(dineroInicial);
        // Multiplicar el dinero actual por el peso de cada arco
        for (Lado<String> lado : circuito) {
            dineroActual *= lado.getPeso();
        }
        return dineroActual;
    }

    /**
     * Reconstruye los caminos más largos a partir de los padres y las distancias
     * Complejidad: O(|V|^2) donde |V| es la cantidad de vértices.
     * Esta complejidad se debe a que se recorren todos los vértices y para cada vértice se recorren los demas hasta llegar al vértice inicial.
     * Que se recorran todos los vértices es O(|V|) y que se recorran los demás es O(|V|) también.
     * @param grafo Grafo dirigido
     * @param padres Arreglo de predecesores de cada vértice
     * @param distancias Arreglo de distancias de cada camino desde el vértice inicial
     * @param s Vértice inicial
     * @return Conjunto de caminos más largos
     */
    public static Set<List<Lado<String>>> reconstruirCaminos(Graph<String> grafo, HashMap<String, String> padres, HashMap<String, Double> distancias, String s) {
        // Inicializar el conjunto de caminos
        Set<List<Lado<String>>> caminos = new HashSet<List<Lado<String>>>();
        // Para cada vértice v
        for (String v : grafo.getAllVertices()) {
            // Si v es el vértice inicial, continuar
            if (v.equals(s)) {
                continue;
            }
            // Inicializar el camino
            List<Lado<String>> camino = new LinkedList<Lado<String>>();
            String u = v;
            // Mientras el padre de u no sea null y no sea el vértice inicial
            while (u != null && !u.equals(s)) {
                // Buscamos el padre de u
                String padre = padres.get(u);
                // Si el padre no es null (Siempre debería existir)
                if (padre != null) {
                    // Agregamos el arco al camino con el peso correspondiente
                    double peso = distancias.get(u) - distancias.get(padre);
                    Lado<String> lado = new Lado<String>(padre, u, peso);
                    camino.add(0, lado);
                }
                u = padre;
            }
            // Agregamos el camino al conjunto de caminos
            caminos.add(camino);
        }
        return caminos;
    }

    /**
     * Algoritmo de Dijkstra modificado para que retorne el camino más largo
     * Complejidad: O(|V|^2) donde |V| es la cantidad de vértices.
     * Esta complejidad se debe a que recorre todos los vértices y para cada vértice recorre todos los demás.
     * @param grafo Grafo dirigido
     * @param s Vértice inicial
     * @return Conjunto de caminos más largos
     */
    public static Set<List<Lado<String>>> dijkstra(Graph<String> grafo, String s) {
        // Inicializar distancias y padres
        HashMap<String, Double> distancias = new HashMap<String, Double>();
        HashMap<String, String> padres = new HashMap<String, String>();
        List<String> vertices = grafo.getAllVertices();
        // Para cada vértice u
        for (String u : vertices) {
            // Inicializar distancias y padres
            distancias.put(u, Double.NEGATIVE_INFINITY);
            padres.put(u, null);
        }
        // Inicializar distancias y padres para el nodo inicial
        distancias.put(s, 0.0);
        // Inicializar cola
        List<String> cola = new LinkedList<String>();
        cola.add(s);
        // Inicializar conjunto de nodos visitados
        Set<String> visitados = new HashSet<String>();
        // Mientras la cola no esté vacía
        while (!cola.isEmpty()) {
            // Sacar el primer elemento de la cola
            String u = cola.remove(0);
            // Si el nodo ya fue visitado, continuar
            if (visitados.contains(u)) {
                continue;
            }
            // Agregar el nodo a los visitados
            visitados.add(u);
            // Para cada nodo v adyacente a u
            for (Lado<String> lado : grafo.getOutwardEdges(u)) {
                String v = lado.getV();
                double peso = lado.getPeso();
                // Si la distancia de s a v es menor que la distancia actual
                // y el nodo no ha sido visitado
                if (distancias.get(v) < distancias.get(u) + peso && !visitados.contains(v)) {
                    // Actualizar la distancia de s a v
                    distancias.put(v, distancias.get(u) + peso);
                    // Actualizar el padre de v
                    padres.put(v, u);
                    // Agregar v a la cola
                    cola.add(v);
                }
            }
        }
        return reconstruirCaminos(grafo, padres, distancias, s);
    }

    /**
     * Verifica si un camino se puede cerrar
     * Complejidad: O(|suc(v)|) donde |suc(v)| es la cantidad de sucesores del último vértice del camino.
     * Esta complejidad se debe a que se recorren todos los sucesores del último vértice del camino.
     * @param grafo Grafo dirigido
     * @param camino Camino
     * @return True si se puede cerrar, false en caso contrario
     */
    public static boolean sePuedeCerrar(Graph<String> grafo, List<Lado<String>> camino) {
        // Si el camino es vacío, no se puede cerrar
        if (camino.isEmpty()) {
            return false;
        }
        // Tomamos el primer y último vértice del camino
        String u = camino.get(0).getU();
        String v = camino.get(camino.size() - 1).getV();
        // Si el primer y último vértice son iguales, se puede cerrar (Ya que es un circuito)
        if (u.equals(v)) {
            return true;
        }
        // Si no, buscamos si existe un arco entre el último y el primer vértice
        for (Lado<String> lado : grafo.getOutwardEdges(v)) {
            // Si existe, agregamos el arco al camino y se puede cerrar
            if (lado.getV().equals(u)) {
                camino.add(lado);
                return true;
            }
        }
        // Si no existe, no se puede cerrar
        return false;
    }

    /**
     * Calcula el circuito de mayor costo
     * Complejidad: O(|V|^2) donde |V| es la cantidad de vértices.
     * Esta complejidad se debe a que se calculan los caminos más largos desde cada vértice y se recorren todos los vértices.
     * @param grafo Grafo dirigido
     * @param v Vértice inicial
     * @param dineroInicial Dinero inicial (De forma predeterminada es 1.0)
     * @return Circuito de mayor costo empezando en v
     */
    public static List<Lado<String>> circuitoMayorCosto(Graph<String> grafo, String v, double dineroInicial) {
        // Calculamos los caminos más largos desde v
        Set<List<Lado<String>>> caminos = dijkstra(grafo, v);
        // Inicializamos el circuito y el máximo
        List<Lado<String>> circuito = new LinkedList<Lado<String>>();
        double maximo = Double.NEGATIVE_INFINITY;
        // Para cada camino
        for (List<Lado<String>> camino : caminos) {
            // Calculamos el costo del camino
            double costo = costo(camino, dineroInicial);
            // Si el costo es mayor que el máximo y se puede cerrar el camino
            if (costo > maximo && sePuedeCerrar(grafo, camino)) {
                // Actualizamos el máximo y el circuito
                maximo = costo;
                circuito = camino;
            }
        }
        return circuito;
    }

    /**
     * Verifica si hay ganancia en un vértice
     * Complejidad: O(|V|^2) donde |V| es la cantidad de vértices.
     * Esta complejidad se debe a que se calculan los caminos más largos desde cada vértice y se recorren todos los vértices.
     * @param grafo Grafo dirigido
     * @param v Vértice inicial
     * @param dineroInicial Dinero inicial (De forma predeterminada es 1.0)
     * @return True si hay ganancia, false en caso contrario
     */
    public static boolean hayGanancia(Graph<String> grafo, String v, double dineroInicial) {
        List<Lado<String>> circuito = circuitoMayorCosto(grafo, v, dineroInicial);
        double dineroActual = costo(circuito, dineroInicial);
        return dineroActual > dineroInicial;
    }
    
    /**
     * Verifica si hay arbitraje posible en un grafo
     * Complejidad: O(|V|^3) donde |V| es la cantidad de vértices.
     * @param grafo Grafo dirigido
     * @param dineroInicial Dinero inicial (De forma predeterminada es 1.0)
     * @return "DINERO FACIL DESDE TU CASA" si hay arbitraje, "TODO GUAY DEL PARAGUAY" en caso contrario
     */
    public static String arbitrajePosible(Graph<String> grafo, double dineroInicial) {
        for (String v : grafo.getAllVertices()){
            if (hayGanancia(grafo, v, dineroInicial)){
                List<Lado<String>> circuito = circuitoMayorCosto(grafo, v, dineroInicial);
                double dineroActual = costo(circuito, dineroInicial);
                System.out.println("Se puede aplicar arbitraje con la siguiente secuencia: " + "\n" + "\u001B[96m" + circuito + "\u001B[0m");
                System.out.println("Dinero inicial: " + "\u001B[93m" + dineroInicial + "\u001B[0m");
                System.out.println("Dinero final: " + "\u001B[93m"  + dineroActual + "\u001B[0m");
                return "\u001B[92m" + "DINERO FACIL DESDE TU CASA" + "\u001B[0m";
            }
        }
        return "\u001B[91m" + "TODO GUAY DEL PARAGUAY" + "\u001B[0m";
    }
}