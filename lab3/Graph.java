import java.util.List;

interface Graph<T> {
    boolean add(T vertex);
    boolean connect(T from, T to, double weight);
    boolean contains(T vertex);
    List<Lado<T>> getInwardEdges(T to);
    List<Lado<T>> getOutwardEdges(T from);
    List<Lado<T>> getVerticesConnectedTo(T vertex);
    List<T> getAllVertices();
    int size();
}
