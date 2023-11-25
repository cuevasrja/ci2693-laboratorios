# Laboratorio 3: Arbitrage

## Descripción

El uso de computadoras en la industria financiera se ha visto marcado por controversias  ́ultimamente, ya que el _trading_ programado, disenado ha sido prohibido en muchas firmas de **Wall Street**. La ́etica de la programacion informatica es un campo incipiente conmuchos problemas guabinosos.

El _arbitraje_ es el intercambio de una moneda por otra con la esperanza de aprovechar las pequenas diferencias en las tasas de conversion entre varias monedas para obtener ganancias. Por ejemplo, si $1.00 en moneda estadounidense compra 0.7 libras esterlinas, £1 en moneda britanica compra 9.5 francos franceses y 1 franco frances compra $0.16 en dolares estadounidenses, entonces un comerciante de arbitraje puede comenzar con $1.00 y ganar $1.64, obteniendo asi una ganancia del 6.4 por ciento.

Se  desea  que  escriba  un  programa  que  determine  si  una  secuencia  de  intercambios  de  divisas  puede  generar ganancias como se describe anteriormente. 

Para que el _arbitraje_ sea exitoso, una secuencia de intercambios debe comenzar y terminar con la misma moneda, pero se puede considerar cualquier moneda inicial.

## Estructura de Archivos

- **AdjencyListGraph.java**: Implementa la clase **AdjencyListGraph**, que es la representación de un grafo dirigido, donde cada moneda es un vértice y cada operación de cambio de moneda es un arco con peso.
- **Graph.java**: Implementa la interfaz **Graph**, que define los métodos que debe implementar un grafo dirigido para este proyecto.
- **Lado.java**: Implementa la clase **Lado**, que es la representación de un arco en un grafo dirigido, donde cada arco tiene un vértice origen, un vértice destino y un peso.
- **Arbitrage.java**: Archivo principal del proyecto, que contiene el método main y el algoritmo que resuelve el problema planteado. Se encarga de leer el archivo _tasas.txt_ y construir el grafo dirigido correspondiente, luego ejecuta el algoritmo que resuelve el problema y finalmente imprime la solución en la terminal.
- **tasas.txt**: Archivo que contiene las operaciones entre pares de monedas con el costo de la misma. El formato del archivo es el siguiente:

  ```
  USD GBP 0.7
  GBP FRF 9.5
  FRF USD 0.16
  ...
  ```

  Nótese que es un par de monedas por línea, donde la primera moneda es la moneda origen, la segunda moneda es la moneda destino y el tercer elemento es el costo de la operación. No hay necesidad de repetir pares ordenados, ya que el grafo es dirigido y simple, por lo que solo puede existir un arco de un vértice origen a un vértice destino.

## Compilación y Ejecución

Para compilar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
javac Arbitrage.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
java Arbitrage
```

**Nota**: Debe existir un archivo _tasas.txt_ en el mismo directorio que el resto del proyecto, con el formato descrito en la sección anterior.

## Explicación de la Solución Propuesta (con Complejidades)

Para resolver el problema planteado, se propuso utilizar un algoritmo inspirado en Dijsktra, que consiste en encontrar el camino de costo mínimo entre un vértice origen y todos los demás vértices del grafo. En este caso, el vértice origen es el dólar estadounidense, ya que es la moneda que se tiene al principio y al final de la secuencia de operaciones. Sin embargo, queriamos el camino de mayor costo entre todas las monedas, por lo que se modificó el algoritmo de Dijsktra para que en vez de encontrar el camino de costo mínimo, encuentre el camino de costo máximo. 

El algoritmo que resuelve el problema planteado se encuentra en el método `arbitrajePosible` este metodo se encarga de iterar sobre todos los vértices del grafo y para cada vértice, ejecuta el algoritmo de Dijsktra para encontrar el camino de costo máximo entre el vértice origen y todos los demás vértices del grafo. Luego, se verifica si se puede cerrar el circuito, es decir, si el vértice origen es igual al vértice destino del camino de costo máximo. Si se puede cerrar el circuito, se verifica si hay ganancia, es decir, si el costo del camino de costo máximo es mayor a 1. Si hay ganancia, se imprime el camino de costo máximo y se retorna `true`, indicando que se puede hacer arbitraje. Si no hay ganancia, se imprime el camino de costo máximo y se retorna `false`, indicando que no se puede hacer arbitraje. Si no se puede cerrar el circuito, se imprime el camino de costo máximo y se retorna `false`, indicando que no se puede hacer arbitraje.

Entre las funciones auxiliares que se utilizaron para implementar el algoritmo, se encuentran:

- `leerArchivo`: Se encarga de leer el archivo _tasas.txt_ y construir el grafo dirigido correspondiente. Para esto, se lee el archivo línea por línea y se separa cada línea en tres elementos, donde el primer elemento es el vértice origen, el segundo elemento es el vértice destino y el tercer elemento es el peso del arco. Luego, se crea un vértice para cada moneda y se agrega al grafo. Finalmente, se crea un arco entre el vértice origen y el vértice destino con el peso del arco.

- `costo`: Se encarga de calcular el costo de un camino. Para esto, se itera sobre todos los arcos del camino y se suma el peso de cada arco.

- `reconstruirCaminos`: Se encarga de reconstruir los caminos de costo máximo entre el vértice origen y todos los demás vértices del grafo. Para esto, se itera sobre todos los vértices del grafo y para cada vértice, se reconstruye el camino de costo máximo entre el vértice origen y el vértice actual. Para reconstruir el camino, se itera sobre todos los vértices del grafo y para cada vértice, se verifica si el vértice actual es el vértice destino del camino de costo máximo entre el vértice origen y el vértice actual. Si es el vértice destino, se agrega el vértice actual al camino y se actualiza el vértice actual al vértice origen del camino de costo máximo entre el vértice origen y el vértice actual. Si no es el vértice destino, se actualiza el vértice actual al vértice origen del camino de costo máximo entre el vértice origen y el vértice actual. Finalmente, se agrega el vértice origen al camino y se invierte el camino para que quede en el orden correcto.

- `dijkstra`: Se encarga de encontrar el camino de costo máximo entre un vértice origen y todos los demás vértices del grafo. Para esto, se inicializa un arreglo de distancias con infinito para todos los vértices del grafo, excepto para el vértice origen, que se inicializa con 0. Luego, se inicializa un arreglo de padres con _null_ para todos los vértices del grafo. Finalmente, se inicializa una cola de prioridad con todos los vértices del grafo, donde la prioridad es la distancia del vértice al vértice origen. 

  Luego, se itera sobre todos los vértices del grafo y para cada vértice, se itera sobre todos los vértices adyacentes al vértice actual y para cada vértice adyacente, se verifica si la distancia del vértice origen al vértice actual multiplicado por el peso del arco entre el vértice actual y el vértice adyacente es mayor a la distancia del vértice origen al el vértice adyacente. Si es mayor, se actualiza la distancia del vértice origen al vértice adyacente y se actualiza el padre del vértice adyacente al vértice actual. Finalmente, se reconstruyen los caminos de costo máximo entre el vértice origen y todos los demás vértices del grafo.

- `sePuedeCerrar`: Se encarga de verificar si se puede cerrar el circuito, es decir, si el vértice origen es igual al vértice destino del camino de costo máximo entre el vértice origen y todos los demás vértices del grafo.

- `circuitoMayorCosto`: Se encarga de encontrar el camino de costo máximo entre todos los vértices del grafo. Para esto, se inicializa un costo máximo con 0 y un camino de costo máximo vacío. Luego, se itera sobre todos caminos de costo máximo entre el vértice origen y todos los demás vértices del grafo y para cada camino, se verifica si el costo del camino es mayor al costo máximo. Si es mayor, se actualiza el costo máximo con el costo del camino y se actualiza el camino de costo máximo con el camino. Finalmente, se retorna el camino de costo máximo.

- `hayGanancia`: Se encarga de verificar si hay ganancia, es decir, si el costo del camino de costo máximo entre todos los vértices del grafo es mayor a 1.

Dibujamos una tabla con la complejidad de cada método:

| Método                          | Complejidad          |
| ------------------------------- | -------------------- |
| leerArchivo                     | O(\|E\|*log(\|V\|))  |
| costo                           | O(\|C\|)             |
| reconstruirCaminos              | O(\|V\|<sup>2</sup>) |
| dijkstra                        | O(\|V\|<sup>2</sup>) |
| sePuedeCerrar                   | O(\|suc(v)\|)        |
| circuitoMayorCosto              | O(\|V\|<sup>2</sup>) |
| hayGanancia                     | O(\|V\|<sup>2</sup>) |
| arbitrajePosible                | O(\|V\|<sup>3</sup>) |

Donde `|V|` es la cantidad de vertices en el grafo, `|E|` es la cantidad de arcos, `|C|` es la longitud de un camino y `|suc(v)|` es la cantidad de sucesores del vértice `v`.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `Arbitrage`.

## Hecho por

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).