import java.util.List;
import java.util.Collection;

interface Graph<T> {
    boolean add(T vertex);
    boolean connect(T from, T to);
    boolean disconnect(T from, T to);
    boolean contains(T vertex);
    boolean areConnected(T from, T to);
    List<T> getVerticesConnectedTo(T vertex);
    List<T> getAllVertices();
    boolean remove(T vertex);
    int size();
    Graph<T> subgraph(Collection<T> vertices);
}
