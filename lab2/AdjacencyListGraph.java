/*
 * Proyecto 1 del Laboratorio de Algoritmos y Estructuras de Datos III
 * Autores: Juan Cuevas (19-10056) y Luis Isea (19-10175).
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph<T> implements Graph<T> {
	/*
	 * Creamos dos HashMap que contiene una lista de adyacencia para cada vértice.
	 * El primer HashMap es para los sucesores del vértice y el segundo para los
	 * predecesores.
	 * Sea el grafo G = (V, E) con |V| = n y |E| = m, ambos HashMap tienen n
	 * entradas
	 * y la suma de las longitudes de las listas de adyacencia es 2m.
	 */
	HashMap<T, List<T>> adjacencyListOut = new HashMap<T, List<T>>();
	HashMap<T, List<T>> adjacencyListIn = new HashMap<T, List<T>>();

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
		// Se mapea el vértice a una lista vacía en ambos HashMap, de sucesores y de
		// predecesores.
		adjacencyListOut.put(vertex, new LinkedList<T>());
		adjacencyListIn.put(vertex, new LinkedList<T>());
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y agrega al grafo un arco saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el arco es agregado con éxito.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean connect(T from, T to) {
		// Si alguno de los vértices no existe en el grafo, no se agrega el arco y se
		// retorna false.
		if (!contains(from) || !contains(to)) {
			return false;
		}
		// Si el arco ya existe en el grafo, no se agrega el arco y se retorna false.
		if (adjacencyListOut.get(from).contains(to) || adjacencyListIn.get(to).contains(from)) {
			return false;
		}
		// Se añade el vértice 'to' a la lista de sucesores de 'from'.
		adjacencyListOut.get(from).add(to);
		// Se añade el vértice 'from' a la lista de predecesores de 'to'.
		adjacencyListIn.get(to).add(from);
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y elimina del grafo el arco saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el arco es eliminado con éxito.
	 * Retorna false en caso contrario.
	 * Si el arco no existe en el grafo, no se elimina el arco y se retorna false.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	public boolean disconnect(T from, T to) {
		// Si alguno de los vértices no existe en el grafo, no se elimina el arco y se
		// retorna false.
		if (!contains(from) || !contains(to)) {
			return false;
		}
		// Si el arco no existe en el grafo, no se elimina el arco y se retorna false.
		if (!adjacencyListOut.get(from).contains(to) || !adjacencyListIn.get(to).contains(from)) {
			return false;
		}
		// Se elimina el vértice 'to' de la lista de sucesores de 'from'.
		adjacencyListOut.get(from).remove(to);
		// Se elimina el vértice 'from' de la lista de predecesores de 'to'.
		adjacencyListIn.get(to).remove(from);
		return true;
	}

	/*
	 * Recibe un vértice y retorna true si el vértice pertenece a V.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1).
	 */
	public boolean contains(T vertex) {
		return adjacencyListOut.containsKey(vertex);
	}

	/*
	 * Recibe un vértice v y retorna la lista de predecedores de v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que (u, v) ∈ E.
	 * Si ocurre algún error, retorna la referencia null
	 * Complejidad: O(1)
	 */
	public List<T> getInwardEdges(T to) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(to)) {
			return null;
		}
		// Creamos una copia de la lista de sucesores de 'from' para evitar que se
		// modifique la lista original.
		List<T> result = adjacencyListIn.get(to);
		return result;
	}

	/*
	 * Recibe un vértice v y retorna la lista de sucesores de v. Es decir, retorna
	 * la lista de todos los u ∈ V tales que (v, u) ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(1).
	 */
	public List<T> getOutwardEdges(T from) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(from)) {
			return null;
		}
		// Creamos una copia de la lista de sucesores de 'from' para evitar que se
		// modifique la lista original.
		List<T> result = adjacencyListOut.get(from);
		return result;
	}

	/*
	 * Recibe un vértice v y retorna la lista de vértices adyacentes a v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que (v, u) ∈ E o (u, v) ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	public List<T> getVerticesConnectedTo(T vertex) {
		// Si el vértice no existe en el grafo, se retorna null.
		if (!contains(vertex)) {
			return null;
		}
		List<T> result = new LinkedList<T>();
		result.addAll(adjacencyListOut.get(vertex));
		result.addAll(adjacencyListIn.get(vertex));
		return result.stream().distinct().toList();
	}

	/*
	 * Retorna la lista de todos vértices del grafo.
	 * Es decir, todos los elementos de V.
	 * Complejidad: O(1).
	 */
	public List<T> getAllVertices() {
		return adjacencyListOut.keySet().stream().toList();
	}

	/*
	 * Recibe un vértice y lo elimina del grafo. Retorna true si el vértice es
	 * eliminado con éxito. Retorna false en caso contrario.
	 * Complejidad: O(n*m). Siendo n la cantidad de vértices y m la cantidad
	 * promedio de arcos por vértice.
	 */
	public boolean remove(T vertex) {
		// Si el vértice no existe en el grafo, no se elimina y se retorna false.
		if (!contains(vertex)) {
			return false;
		}
		for (T edge : adjacencyListOut.get(vertex)) {
			adjacencyListIn.get(edge).remove(vertex);
		}
		for (T edge : adjacencyListIn.get(vertex)) {
			adjacencyListOut.get(edge).remove(vertex);
		}
		adjacencyListOut.remove(vertex);
		adjacencyListIn.remove(vertex);
		return true;
	}

	/*
	 * Retorna la cantidad de vértices que contiene el grafo. Es decir, la
	 * cardinalidad del conjunto de vértices |V|.
	 * Complejidad: O(1).
	 */
	public int size() {
		return adjacencyListOut.size();
	}

	/*
	 * Retorna una representación en String del grafo.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	@Override
	public String toString() {
		String result = "";
		for (T vertex : adjacencyListOut.keySet()) {
			result += "[" + vertex + "] -> " + adjacencyListOut.get(vertex) + "\n";
		}
		return result;
	}
}
