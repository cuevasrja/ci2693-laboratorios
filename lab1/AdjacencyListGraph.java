/*
 * Proyecto 1 del Laboratorio de Algoritmos y Estructuras de Datos III
 * Autores: Juan Cuevas (19-10056) y Luis Isea (19-10175).
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph<T> implements Graph<T> {
	/*
	 * Representación de un grafo no dirigido mediante listas de adyacencia.
	 * Creamos el HashMap que contiene una lista de adyacencia para cada vértice.
	 * El HashMap contiene los vertices adyacentes a cada vértice.
	 * Sea el grafo G = (V, E) con |V| = n y |E| = m, el HashMap tiene n
	 * entrada y la suma de las longitudes de las listas de adyacencia es 2m.
	 */
	HashMap<T, List<T>> adjacencyList = new HashMap<T, List<T>>();

	/*
	 * Recibe un vértice y lo agrega al grafo. Retorna true si el vértice es
	 * agregado con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean add(T vertex) {
		// Si el vértice ya existe en el grafo, no se agrega y se retorna false.
		if (contains(vertex)) {
			return false;
		}
		// Si el vértice es null, no se agrega y se retorna false.
		if (vertex == null) {
			return false;
		}
		// Se mapea el vértice a una lista vacía en el HashMap.
		adjacencyList.put(vertex, new LinkedList<T>());
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y agrega al grafo un lado saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el lado es agregado con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean connect(T from, T to) {
		// Si alguno de los vértices no existe en el grafo, no se agrega el lado y se
		// retorna false.
		if (!contains(from) || !contains(to)) {
			return false;
		}
		// Si el lado ya existe en el grafo, no se agrega el lado y se retorna false.
		if (areConnected(from, to)) {
			return false;
		}
		// Se añade el vértice 'to' a la lista de adyacencia de 'from'.
		adjacencyList.get(from).add(to);
		// Se añade el vértice 'from' a la lista de adyacencia de 'to'.
		adjacencyList.get(to).add(from);
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y elimina del grafo el lado saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el lado es eliminado con éxito.
	 * Retorna false en caso contrario.
	 * Si el lado no existe en el grafo, no se elimina el lado y se retorna false.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	public boolean disconnect(T from, T to) {
		// Si alguno de los vértices no existe en el grafo, no se elimina el lado y se
		// retorna false.
		if (!contains(from) || !contains(to)) {
			return false;
		}
		// Si el lado no existe en el grafo, no se elimina el lado y se retorna false.
		if (!adjacencyList.get(from).contains(to)) {
			return false;
		}
		// Se elimina el vértice 'to' de la lista de adyacencia de 'from'.
		adjacencyList.get(from).remove(to);
		// Se elimina el vértice 'from' de la lista de adyacencia de 'to'.
		adjacencyList.get(to).remove(from);
		return true;
	}

	/*
	 * Recibe un vértice y retorna true si el vértice pertenece a V.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean contains(T vertex) {
		return adjacencyList.containsKey(vertex);
	}

	/**
	 * Recibe dos vértices 'from' y 'to' y retorna true si existe una arista entre
	 * ellos. Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean areConnected(T from, T to) {
		// Si alguno de los vértices no existe en el grafo, no están conectados y se
		// retorna false.
		if (!contains(from) || !contains(to)) {
			return false;
		}
		// Si el vértice 'to' está en la lista de adyacencia de 'from', están conectados
		// y se retorna true.
		if (adjacencyList.get(from).contains(to) && adjacencyList.get(to).contains(from)) {
			return true;
		}
		return false;
	}

	/*
	 * Recibe un vértice v y retorna la lista de vértices adyacentes a v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que {v, u} ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(1). 
	 */
	public List<T> getVerticesConnectedTo(T vertex) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(vertex)) {
			return null;
		}
		return adjacencyList.get(vertex);
	}

	/*
	 * Retorna la lista de todos vértices del grafo.
	 * Es decir, todos los elementos de V.
	 * Complejidad: O(1).
	 */
	public List<T> getAllVertices() {
		return adjacencyList.keySet().stream().toList();
	}

	/*
	 * Recibe un vértice y lo elimina del grafo. Retorna true si el vértice es
	 * eliminado con éxito. Retorna false en caso contrario.
	 * Complejidad: O(n*m). Siendo n la cantidad de vértices y m la cantidad
	 * promedio de lados por vértice.
	 */
	public boolean remove(T vertex) {
		// Si el vértice no existe en el grafo, no se elimina y se retorna false.
		if (!contains(vertex)) {
			return false;
		}
		for (T edge : adjacencyList.get(vertex)) {
			// Se elimina el vértice de la lista de adyacencia de todos sus vértices
			// adyacentes.
			adjacencyList.get(edge).remove(vertex);
		}
		// Se elimina el vértice del HashMap.
		adjacencyList.remove(vertex);
		return true;
	}

	/*
	 * Retorna la cantidad de vértices que contiene el grafo. Es decir, la
	 * cardinalidad del conjunto de vértices |V|.
	 * Complejidad: O(1).
	 */
	public int size() {
		return adjacencyList.size();
	}

	/*
	 * Recibe una colección V′ de vértices y retorna otra instancia de grafo
	 * donde el conjunto de vértices contiene solo aquellos vértices presentes en V’
	 * y solo aquellos lados asociados a esos vértices. Es decir,
	 * retorna G′ = (V′, E′)
	 * donde E′ = {(u, v) ∈ E ∣ u ∈ V′ ∧ v ∈ V′}.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(n*m). Siendo n la cantidad de vértices y m la cantidad
	 * promedio de lados por vértice.
	 */
	public Graph<T> subgraph(Collection<T> vertices) {
		// Si la colección de vértices es null, se retorna null.
		if (vertices == null) {
			return null;
		}
		Graph<T> result = new AdjacencyListGraph<T>();
		for (T vertex : vertices) {
			// Si alguno de los vértices no existe en el grafo, se retorna null.
			if (!contains(vertex)) {
				return null;
			}
			result.add(vertex);
		}
		for (T vertex : vertices) {
			for (T edge : adjacencyList.get(vertex)) {
				// Si el vértice adyacente no existe en la colección de vértices, no se
				// agrega el lado.
				if (vertices.contains(edge)) {
					result.connect(vertex, edge);
				}
			}
		}
		return result;
	}

	/*
	 * Retorna una representación en String del grafo.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	@Override
	public String toString() {
		String result = "";
		for (T vertex : adjacencyList.keySet()) {
			result += "[" + vertex + "] -> " + adjacencyList.get(vertex) + "\n";
		}
		return result;
	}
}
