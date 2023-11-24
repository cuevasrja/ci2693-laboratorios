class Lado<T> implements Comparable<Lado<T>>{
    private T u;
    private T v;
    private double peso;

    public Lado(T u, T v, double peso) {
        this.u = u;
        this.v = v;
        this.peso = peso;
    }

    public double getPeso() {
        return this.peso;
    }

    public T getU() {
        return this.u;
    }

    public T getV() {
        return this.v;
    }

    public int compareTo(Lado<T> other) {
        double discriminator = this.peso - other.getPeso();
        if (discriminator == 0) {
            return 0;
        }
        else if (discriminator > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }

    public String toString() {
        return this.u + " -(" + this.peso + ")-> " + this.v;
    }
}