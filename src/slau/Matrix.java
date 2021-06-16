package slau;

import java.util.ArrayList;

public class Matrix {
    private int n;
    private ArrayList<ArrayList<Fraction>> ratio;

    Matrix(ArrayList<ArrayList<Fraction>> ratio) {
        this.ratio = ratio;
        n = ratio.size();
    }

    public void gauss() {
        gauss(ratio);
    }

    private static void gauss(ArrayList<ArrayList<Fraction>> ratio) {
        if (ratio.size() > 1) {
            Fraction c1 = new Fraction(ratio.get(0).get(0));
            if (c1.getNumerator() != 0) {
                for (int i = 1; i < ratio.size(); i++) {
                    Fraction c2 = new Fraction(ratio.get(i).get(0));
                    if (c2.getNumerator() != 0) {
                        for (int j = 0; j < ratio.size(); j++) {
                            Fraction t = new Fraction(ratio.get(0).get(j));
                            t.div(c1);
                            t.mul(c2);
                            ratio.get(i).get(j).sub(t);
                        }
                    }
                }
            }
            ArrayList<ArrayList<Fraction>> nR = new ArrayList<>();
            for (int i = 1; i < ratio.size(); i++) {
                ArrayList<Fraction> temp = new ArrayList<>();
                for (int j = 1; j < ratio.size(); j++) {
                    temp.add(new Fraction(ratio.get(i).get(j)));
                }
                nR.add(temp);
            }
            gauss(nR);
            for (int i = 1; i < ratio.size(); i++) {
                for (int j = 1; j < ratio.size(); j++) {
                    ratio.get(i).set(j, nR.get(i - 1).get(j - 1));
                }
            }
        }
    }

    void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ratio.get(i).get(j).print();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ArrayList<Fraction> d1 = new ArrayList<>();
        ArrayList<Fraction> d2 = new ArrayList<>();
        ArrayList<Fraction> d3 = new ArrayList<>();
        d1.add(new Fraction(1));
        d1.add(new Fraction(1));
        d1.add(new Fraction(2));
        d2.add(new Fraction(0));
        d2.add(new Fraction(0));
        d2.add(new Fraction(-1));
        d3.add(new Fraction(-2));
        d3.add(new Fraction(1));
        d3.add(new Fraction(-2));
        ArrayList<ArrayList<Fraction>> ratio = new ArrayList<>();
        ratio.add(d1);
        ratio.add(d2);
        ratio.add(d3);
        Matrix m = new Matrix(ratio);
        m.gauss();
        m.print();
    }
}
