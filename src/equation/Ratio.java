package equation;

public class Ratio {
    private int degree;
    private int value;

    public Ratio(int d, int v) {
        degree = d;
        value = v;
    }

    public Ratio(Ratio other) {
        degree = other.getDegree();
        value = other.getValue();
    }

    public void copy(Ratio other){
        degree = other.getDegree();
        value = other.getValue();
    }

    public int getDegree() {
        return degree;
    }

    public int getValue() {
        return value;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
