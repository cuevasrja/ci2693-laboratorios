# Laboratorio #1
### Autor: *Juan Andrés Cuevas Regalado*
### Carnet: *19-10056*

## Descripción
Los grados de separacion son una medida de la proximidad entre dos personas en una red social. Se define como el numero de personas que hay que atravesar para pasar de una a otra. Por ejemplo, si dos personas son amigos, entonces su grado de separacion es 1. Si dos personas tienen un amigo en comun pero no son amigas entre si, entonces su grado de separacion es 2. Y asi sucesivamente. Los grados de separacion se pueden utilizar para medir la conectividad de una red social. Una red social con un grado de separacion bajo es una red social en la que las personas estan relativamente cerca unas de otras.

La teoria de los seis grados de separacion es una hipotesis que afirma que cualquier persona en el mundo puede estar conectada a cualquier otra persona a traves de una cadena de conocidos que no tiene mas de seis intermediarios. Esta teoria se basa en la idea de que el numero de conocidos crece exponencialmente con el numero de enlaces en la cadena. Por ejemplo, si una persona conoce a 100 personas, entonces cada una de esas 100 personas conoce a otras 100 personas, lo que da un total de 10.000 conocidos. Sin embargo, la teor ́ıa de los seis grados de separacion no es un hecho comprobado. Hay algunos casos en los que el grado de separacion entre dos personas es mayor de seis

## Planteamiento del problema
Se desea que implemente la clase DegreesOfSeparation en un archivo DegreesOfSeparation.java. Esta clase debe contar con un m ́etodo main que lea dos nombres de personas como argumentos de linea de comandos e imprima por la salida estandar el grado de separacion entre ambas personas seg ́un los pares de amistad presentes en input.txt.

Ejemplo de uso:
```bash
java DegreesOfSeparation Carlos Ana
```
Salida del ejemplo:
```bash
2
```
Para esta implementacion, consideraremos que el grado de separacion entre una persona y ella misma es 0. En caso de que no exista un grado de separacion entre las personas, se debe imprimir -1

## Solución
**Estructura de datos utilizadas:** 
- Grafo no dirigido *G = (V, E)* representado por una lista de adyacencia.
- Cola de tipo FIFO *(First In First Out)* para los caminos a visitar en el algoritmo BFS.
- Lista para almacenar los caminos visitados por el algoritmo BFS.

**Algoritmos utilizados:** 
- **BFS (Breadth First Search)**: Algoritmo de busqueda en anchura. Se utiliza para encontrar el camino mas corto entre dos nodos de un grafo. En este caso, se utiliza para encontrar el grado de separacion entre dos personas.
Escogí este algoritmo porque es el mas eficiente para encontrar el camino mas corto entre dos nodos de un grafo no dirigido, ya que recorre el grafo por niveles, lo que permite encontrar con mayor seguridad el camino mas corto entre dos nodos.
El algoritmo *BFS* recibe como parametros el grafo, el nodo inicial y el nodo final. Primeramente, se inicializa una cola de tipo *FIFO*, y una lista para almacenar los caminos visitados. Luego, se encola el nodo inicial, y se agrega a la lista de caminos visitados. Mientras la cola no este vacia, se desencola el primer elemento de la cola, y se verifica si es el nodo final. En caso de que sea el nodo final, se retorna el camino visitado. En caso contrario, se encolan todos los nodos adyacentes al nodo desencolado, y se agregan a la lista de caminos visitados. Finalmente, si no se encontro el nodo final, se retorna *null*.
*Complejidad: O(|V|+|E|)*
- **SeparationDegree**: Algoritmo que calcula el grado de separacion entre dos personas usando el camino mas corto entre ellas. En caso de que no exista un camino entre las personas, se retorna -1. *Complejidad: O(1)*

**Funcionamiento del programa:**

Se inicializa un grafo no dirigido con 10 vertices ya nombrados, y con 20 aristas aleatorias. Luego, se imprime el grafo por pantalla, y se toman los nombres de las personas ingresados por el usuario. Se calcula la cadena de amistad entre las personas ingresadas usando el algoritmo BFS, el cual retorna el camino mas corto entre las personas. Finalmente, se calcula el grado de separacion entre las personas usando el algoritmo SeparationDegree, y se imprime por pantalla.

## Pruebas
Para probar el programa, se ejecutaron los siguientes comandos:

- Para compilar el programa:
```bash
javac DegreesOfSeparation.java
```

- Para ejecutar el programa:
```bash
java DegreesOfSeparation Nombre1 Nombre2
```

**Aclaración 1:** *Nombre1* y *Nombre2* son los nombres de las personas entre las cuales se desea calcular el grado de separacion.

**Aclaración 2:** Al ejecutar el programa, se crea un grafo con 10 vertices ya nombrados, y con 20 aristas aleatorias. Luego, se imprime el grafo por pantalla, y se calcula el grado de separacion entre las personas ingresadas por el usuario.