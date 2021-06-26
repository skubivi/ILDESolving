package equation;

public class INumb {
    private double r;
    private double i;

    INumb(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public double getR() {
        return r;
    }

    public double getI() {
        return i;
    }

    public boolean equal(INumb other) {
        return (this.r == other.r && this.i == other.i);
    }
}
