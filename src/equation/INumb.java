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
        return Math.abs(i);
    }

    public boolean equal(INumb other) {
        return (this.r == other.r && Math.abs(this.i) == Math.abs(other.i));
    }
}
