package slau;

import java.util.ArrayList;

public class MatrixSLAU {
    private final int n;
    private final ArrayList<ArrayList<Fraction>> ratio;
    private final ArrayList<Fraction> k;
    private ArrayList<Fraction> eq = new ArrayList<>();

    MatrixSLAU(ArrayList<ArrayList<Fraction>> ratio, ArrayList<Fraction> k) {
        this.k = k;
        this.ratio = ratio;
        n = ratio.size();
    }

    public void gauss() {
        gauss(ratio, k);
    }

    private static void gauss(ArrayList<ArrayList<Fraction>> ratio, ArrayList<Fraction> k) {
        if (ratio.size() > 1) {
            Fraction c1 = new Fraction(ratio.get(0).get(0));
            int c = 0;
            while (c1.getNumerator() == 0) {
                c++;
                c1 = new Fraction(ratio.get(c).get(0));
            }
            for (int i = 0; i < ratio.size(); i++) {
                if (i != c) {
                    Fraction c2 = new Fraction(ratio.get(i).get(0));
                    if (c2.getNumerator() != 0) {
                        for (int j = 0; j < ratio.size(); j++) {
                            Fraction t = new Fraction(ratio.get(c).get(j));
                            t.div(c1);
                            t.mul(c2);
                            ratio.get(i).get(j).sub(t);
                        }
                        Fraction t = new Fraction(k.get(c));
                        t.div(c1);
                        t.mul(c2);
                        k.get(i).sub(t);
                    }
                }
            }
            ArrayList<ArrayList<Fraction>> nR = new ArrayList<>();
            ArrayList<Fraction> nK = new ArrayList<>();
            for (int i = 0; i < ratio.size(); i++) {
                if (i != c) {
                    ArrayList<Fraction> temp = new ArrayList<>();
                    for (int j = 1; j < ratio.size(); j++) {
                        temp.add(new Fraction(ratio.get(i).get(j)));
                    }
                    nR.add(temp);
                    nK.add(k.get(i));
                }
            }
            gauss(nR, nK);
            int i1 = 0;
            for (int i = 0; i < ratio.size(); i++) {
                if (i != c) {
                    for (int j = 1; j < ratio.size(); j++) {
                        ratio.get(i).set(j, nR.get(i1).get(j - 1));
                    }
                    k.set(i, nK.get(i1));
                    i1++;
                }
            }
        }
    }

    private static void solve(ArrayList<ArrayList<Fraction>> ratio, ArrayList<Fraction> k, ArrayList<Fraction> eq) {
        if (ratio.size() > 0) {
            int check = -1;
            for (int i = 0; i < ratio.size(); i++) {
                boolean checkB = true;
                for (int j = 0; j < ratio.size() - 1; j++) {
                    if (ratio.get(i).get(j).getNumerator() != 0) {
                        checkB = false;
                        break;
                    }
                }
                if (checkB) check = i;
            }
            Fraction t = new Fraction(k.get(check));
            t.div(ratio.get(check).get(ratio.size() - 1));
            eq.add(t);
            ArrayList<Fraction> nK = new ArrayList<>();
            ArrayList<ArrayList<Fraction>> nR = new ArrayList<>();
            for (int i = 0; i < ratio.size(); i++) {
                if (i != check) {
                    ArrayList<Fraction> temp = new ArrayList<>();
                    for (int j = 0; j < ratio.size() - 1; j++) {
                        temp.add(ratio.get(i).get(j));
                    }
                    nR.add(temp);
                    Fraction tK = new Fraction(t);
                    tK.mul(ratio.get(i).get(ratio.size() - 1));
                    tK.mul(-1);
                    tK.sum(k.get(i));
                    nK.add(tK);
                }
            }
            solve(nR, nK, eq);
        }
    }

    public void solve() {
        gauss();
        solve(ratio, k, eq);
        ArrayList<Fraction> temp = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            temp.add(eq.get(i));
        }
        eq = temp;
    }

    private void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ratio.get(i).get(j).print();
                System.out.print(" ");
            }
            k.get(i).print();
            System.out.println();
        }
    }

    private void printEq() {
        for (int i = 0; i < n; i++) {
            System.out.print("x" + (i + 1) + " = ");
            eq.get(i).print();
            System.out.print(" ");
        }
    }

    private static void test(){
        ArrayList<Fraction> d1 = new ArrayList<>();
        ArrayList<Fraction> d2 = new ArrayList<>();
        ArrayList<Fraction> d3 = new ArrayList<>();
        ArrayList<Fraction> d4 = new ArrayList<>();
        ArrayList<Fraction> k = new ArrayList<>();
        d1.add(new Fraction(1));
        d1.add(new Fraction(1));
        d1.add(new Fraction(2));
        d1.add(new Fraction(-2));
        d2.add(new Fraction(0));
        d2.add(new Fraction(0));
        d2.add(new Fraction(-1));
        d2.add(new Fraction(1));
        d3.add(new Fraction(-2));
        d3.add(new Fraction(1));
        d3.add(new Fraction(-2));
        d3.add(new Fraction(2));
        d4.add(new Fraction(1));
        d4.add(new Fraction(2));
        d4.add(new Fraction(-2));
        d4.add(new Fraction(1));
        k.add(new Fraction(9));
        k.add(new Fraction(9));
        k.add(new Fraction(9));
        k.add(new Fraction(9));
        ArrayList<ArrayList<Fraction>> ratio = new ArrayList<>();
        ratio.add(d1);
        ratio.add(d2);
        ratio.add(d3);
        ratio.add(d4);
        MatrixSLAU m = new MatrixSLAU(ratio, k);
        m.solve();
        m.print();
        m.printEq();
    }

    public static void main(String[] args) {
        test();
    }
}
