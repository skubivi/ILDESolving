package slau;

public class Fraction {
    private int numerator;
    private int denominator;

    Fraction(int a) {
        numerator = a;
        denominator = 1;
    }

    Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        normalization();
    }

    Fraction(Fraction other) {
        this.numerator = other.numerator;
        this.denominator = other.denominator;
    }

    private static int max(int a, int b) {
        if (a > b) return a;
        else return b;
    }

    private static int min(int a, int b) {
        if (a < b) return a;
        else return b;
    }

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

    public void mul(int a) {
        numerator *= a;
        normalization();
    }

    public void mul(Fraction other) {
        numerator *= other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    public void div(int a) {
        denominator *= a;
        normalization();
    }

    public void div(Fraction other) {
        numerator *= other.denominator;
        denominator *= other.numerator;
        normalization();
    }

    public void sub(Fraction other) {
        numerator = numerator * other.denominator - denominator * other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    public void sum(Fraction other) {
        numerator = numerator * other.denominator + denominator * other.numerator;
        denominator *= other.denominator;
        normalization();
    }

    public void print() {
        System.out.print(numerator + "/" + denominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public static void main(String[] args) {
        Fraction a = new Fraction(0);
        Fraction b = new Fraction(1, 3);
        b.div(a);
        b.print();
    }
}
