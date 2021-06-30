package slau;

//Класс обыкновенной дроби
public class Fraction {
    private int numerator;
    private int denominator;

    //Конструктор целого числа с знаменателем равным 1
    public Fraction(int a) {
        numerator = a;
        denominator = 1;
    }

    //Стандартный конструктор
    Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        normalization();
    }

    //Конструктор копирования
    Fraction(Fraction other) {
        this.numerator = other.numerator;
        this.denominator = other.denominator;
    }

    //Функция поиска максимума из двух
    private static int max(int a, int b) {
        if (a > b) return a;
        else return b;
    }

    //Функция поиска минимума из двух
    private static int min(int a, int b) {
        if (a < b) return a;
        else return b;
    }

    //Поиск НОД
    private static int gCD(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int t = max(a, b);
        b = min(a, b);
        a = t;
        while ((a % b) != 0) {
            t = max(a, b);
            b = min(a, b);
            a = t % b;
        }
        return b;
    }

    //Функция приведения дроби к нормализованному виду
    private void normalization() {
        if (numerator == 0) {
            denominator = 1;
        }
        if (denominator == 0) {
            denominator = 1;
        }
        if (denominator < 0) {
            denominator *= -1;
            numerator *= -1;
        }
        if (numerator != 0) {
            int a = gCD(numerator, denominator);
            numerator /= a;
            denominator /= a;
        }
    }

    //Умножение на целое число
    public void mul(int a) {
        numerator *= a;
        normalization();
    }

    //Умножение на дробь
    public void mul(Fraction other) {
        numerator *= other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    //Деление на целое число
    public void div(int a) {
        denominator *= a;
        normalization();
    }

    //Деление на дробь
    public void div(Fraction other) {
        numerator *= other.denominator;
        denominator *= other.numerator;
        normalization();
    }

    //Вычитание дроби
    public void sub(Fraction other) {
        numerator = numerator * other.denominator - denominator * other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    //Сложение с дробью
    public void sum(Fraction other) {
        numerator = numerator * other.denominator + denominator * other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    //Вывод для отладки
    public void print() {
        System.out.print(numerator + "/" + denominator);
    }

    //Возвращает числитель
    public int getNumerator() {
        return numerator;
    }

    //Возвращает знаменатель
    public int getDenominator() {
        return denominator;
    }

    //Преобразование в String
    public String toString() {
        return numerator + "/" + denominator;
    }

    private static void test() {
        Fraction a = new Fraction(0);
        Fraction b = new Fraction(1, 3);
        b.div(a);
        b.print();
    }

    public static void main(String[] args) {
        test();
    }
}
