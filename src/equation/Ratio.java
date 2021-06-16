package equation;

public class Ratio {
    private int degree;
    private int value;

    Ratio(int d, int v) {
        degree = d;
        value = v;
    }
    Ratio(Ratio other){
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
