# Laboratorio 2: App de delivery

## Descripción

Recientemente se anuncio el proximo lanzamiento de **_NextToYou_** una nueva aplicacion que promete revolucionar el mercado de las apps de delivery. **_NextToYou_** tiene un concepto simple pero efectivo: prometen enfocarse en la rapidez. Prometen un tiempo de entrega menor a 30 minutos o te devuelven el dinero.

Para  lograr  tales  tiempos  de  entrega,  piensan  separar  las  ciudades  en  las  que  operan  en  varias _localidades_ y mostrar al usuario solo aquellos comercios que est ́en dentro de su localidad.

**_NextToYou_** piensa lanzar su piloto inicialmente en la ciudad de Caracas. Para ello, han hecho un estudio de los comercios disponibles y las vias la ciudad. A partir de este estudio, se concluyo que lo mejor ser ́ıa excluir aquellas vias que suelen tener mucho trafico.

La app solo necesita dos cosas para lanzar su prueba piloto. Lo primero es determinar las _localidades_ de la ciudad de Caracas. Esto se hara con base a los comercios socios de **_NextToYou_**. Una _localidad_ viene dada por un conjunto de comercios entre los cuales se puede transitar sin pasar por vias de mucho trafico. A cada usuario se le asignara la _localidad_ que le quede mas cerca. Notese que con este esquema es posible tener localidades que incluyan un solo comercio.

Lo segundo es determinar el numero de repartidores que hara falta contratar. Se estima que se necesitaran 10 repartidores para localidades pequenas _(2 o menos comercios)_, 20 para localidades medianas _(5 o menos comercios)_ y 30 para localidades grandes _(de 6 comercios en adelante)_.

## Estructura de Archivos

- **AdjencyListGraph.java**: Implementa la clase **AdjencyListGraph**, que es la representación de un grafo dirigido, donde cada comercio es un vértice y cada arista representa una ruta unidireccional entre dos comercios.
- **Graph.java**: Implementa la interfaz **Graph**, que define los métodos que debe implementar un grafo dirigido para este proyecto.
- **NextToYou.java**: Archivo principal del proyecto, que contiene el método main y el algoritmo que resuelve el problema planteado. Se encarga de leer el archivo _Caracas.txt_ y construir el grafo dirigido correspondiente, luego ejecuta el algoritmo que resuelve el problema y finalmente imprime la solución en la terminal.
- **Caracas.txt**: Archivo que contiene las rutas entre cada par de comercios. El formato del archivo es el siguiente:

  ```
  Sucy’s Cookies, Kagari Sushi
  Kagari Sushi, Arepas Amanda
  Arepas Amanda, Sucy’s Cookies
  MacDonas, Farmanada
  Farmanada, MacDonas
  ...
  ```

  Nótese que es un par de comercios por línea, y no se deben poner pares repetidos en el mismo orden. Ya que el algoritmo toma en cuenta la posibilidad de pares ordenados repetidos de por sí y no es necesario ponerlos en el archivo.

## Compilación y Ejecución

Para compilar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
javac NextToYou.java
```

Para ejecutar el programa, se debe ejecutar el siguiente comando en la terminal:

```java
java NextToYou
```

**Nota**: Debe existir un archivo _Caracas.txt_ en el mismo directorio que el resto del proyecto, con el formato descrito en la sección anterior.

## Explicación de la Solución Propuesta (con Complejidades)

Para resolver el problema planteado, se propuso utilizar un algoritmo inspirado en la aplicacion de **DFS** para la visita de vertices. El algoritmo que resuelve el problema se encuentra en el método `repartidoresNecesarios` de la clase `NextToYou`.

_DFS_ (Conocido tambien como "Busqueda de Profundidad") es un algoritmo de búsqueda para lo cual recorre los nodos de un grafo. Su funcionamiento consiste en ir expandiendo cada uno de los nodos que va localizando, de forma recurrente (desde el nodo padre hacia el nodo hijo). Cuando ya no quedan más nodos que visitar en dicho camino, regresa al nodo predecesor, de modo que repite el mismo proceso con cada uno de los vecinos del nodo. Cabe resaltar que si se encuentra el nodo antes de recorrer todos los nodos, concluye la búsqueda. En su aplicacion para la visita de vertices, se va construyendo un camino; si este es expandible, se se exploran los sucesores del ultimo vertice del camino y se agregan, creando nuevos caminos; en caso contrario, se devuelve al predecesor del ultimo vertice del camino y se exploran los demas sucesores de ese vertice. Este proceso se repite hasta que se visiten todos los vertices, generando una o mas arborescencias.

Así, para la resolución de este problema, empezamos por crear una lista de rutas (arcos) entre dos comercios cualesquiera que representan el conjunto de arcos de el grafo que proximamente crearemos, el miesmo es un grafo dirigido que representa las rutas en una direccion entre comercios. Luego llamamos al método `repartidoresNecesarios` con el grafo como parámetro, que a su vez llama al método `componentesFuertementeConexas` y `sumarRepartidores` que son sus métodos auxiliares.

El método `componentesFuertementeConexas` busca las componentes conexas, es decir, los subgrafos conexos de un grafo dirigido. Para esto se usa una aplicación de _DFS_ que recorre el grafo y va guardando los vértices visitados en una pila. Luego se crea el grafo transpuesto, que es el grafo con las aristas invertidas, y se vuelve a aplicar _DFS_ pero esta vez con la pila de vértices visitados en el primer _DFS_. Esto nos da las componentes conexas del grafo.

Con esto logramos obtener las componentes conexas del grafo, que son los subgrafos conexos del grafo dirigido. Luego, el método `sumarRepartidores` se encarga de sumar los repartidores necesarios para cada componente conexa, y luego imprime la solución en la terminal.

Decimos que nos inspiramos en la aplicación de _DFS_ para la visita de vértices porque en este caso no nos interesa visitar todos los vértices del grafo, sino que nos interesa visitar los vértices de cada componente conexa. Por lo tanto, en vez de recorrer todos los vértices del grafo, recorremos los vértices de cada componente conexa. Esto lo logramos con el método `dfs` que recibe como parámetro un vértice y un grafo, y se encarga de recorrer todos los vértices de la componente conexa a la que pertenece el vértice dado. Para esto, se usa una aplicación de _DFS_ que recorre el grafo y va guardando los vértices visitados en una pila. Luego se crea el grafo transpuesto, que es el grafo con las aristas invertidas, y se vuelve a aplicar _DFS_ pero esta vez con la pila de vértices visitados en el primer _DFS_. Esto nos da los vértices de la componente conexa a la que pertenece el vértice dado. Luego, el método `dfs` se encarga de recorrer los vértices de la componente conexa y sumar los repartidores necesarios para cada componente conexa. Lo cual nos da la solución al problema con una complejidad de O(\|E\|*\|V\|).

Dibujamos una tabla con la complejidad de cada método:

| Método                          | Complejidad          |
| ------------------------------- | -------------------- |
| leerArchivo                     | O(\|E\|*\|V\|)       |
| componentesFuertementeConexas   | O(\|E\|*\|V\|)       |
| dfs                             | O(\|E\|+\|V\|)       |
| simetrico                       | O(\|E\|*\|V\|)       |
| sumarRepartidores               | O(\|CC\|)            |
| imprimirCC                      | O(\|CC\|)            |
| repartidoresNecesarios          | O(\|E\|*\|V\|)       |
| colorear                        | O(1)                 |

Donde `|V|` es la cantidad de vertices en el grafo, `|E|` es la cantidad de arcos y `|CC|` la cantidad de componentes conexas.

**Nota:** Más detalles sobre la complejidad de cada método se encuentran en los comentarios del código de la clase `NextToYou`.

## Hecho por

- Juan Cuevas [@cuevasrja](https://github.com/cuevasrja) (19-10056).