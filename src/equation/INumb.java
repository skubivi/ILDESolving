package equation;

//Комплексное число
public class INumb {
    private double r;
    private double i;

    INumb(double r, double i) {
        this.r = r;
        this.i = i;
    }

    //Возвращает рациональную часть
    public double getR() {
        return r;
    }

    //Возвращает мнимую часть
    public double getI() {
        return Math.abs(i);
    }

    //Сравнение комплексных чисел
    public boolean equal(INumb other) {
        return (this.r == other.r && Math.abs(this.i) == Math.abs(other.i));
    }
}
