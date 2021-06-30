package equation;

//Вспомогательный класс для решения уравнений, в котором задаются степень и коэффициент данной степени
public class Ratio {
    private int degree;
    private int value = 0;
    private double dValue;

    //Стандартный конструктор
    public Ratio(int d, int v) {
        degree = d;
        value = v;
    }

    public Ratio(int d, double v) {
        degree = d;
        dValue = v;
    }

    public void makeInt(int m) {
        if (dValue != 0)
            value = (int) (dValue * Math.pow(10, m));
        else
            value = value * (int)Math.pow(10, m);
    }

    //Конструктор копирования
    public Ratio(Ratio other) {
        degree = other.getDegree();
        value = other.getValue();
        dValue = other.getDValue();
    }

    //Функция копирования
    public void copy(Ratio other) {
        degree = other.getDegree();
        value = other.getValue();
        dValue = other.getDValue();
    }

    //Возвращает степень
    public int getDegree() {
        return degree;
    }

    //Возвращает значение
    public int getValue() {
        return value;
    }

    public double getDValue(){
        return  dValue;
    }

    //Устанавливает степень
    public void setDegree(int degree) {
        this.degree = degree;
    }

    private static void test(){
        Ratio r = new Ratio(0,3.5);
        r.makeInt(1);
        System.out.println(r.getValue());
    }

    public static void main(String[] args) {
        test();
    }
}
