package ilde;

import slau.*;
import equation.*;

import java.util.ArrayList;

public class ILDE {
    private ArrayList<Ratio> k;
    private String f;
    private String eq = "";
    private int kind;

    ILDE(ArrayList<Ratio> k, String f, int kind) {
        this.k = k;
        this.f = f;
        this.kind = kind;
    }

    public void solve() {
        ChEquation lE = new ChEquation(k);
        lE.solve();
        int n = 1;
        for (int i = 0; i < lE.getEq().size(); i++) {
            if (!lE.getEq().get(i).isI()) {
                for (int j = 0; j < lE.getEq().get(i).getQuantity(); j++) {
                    if (!eq.equals("")) eq = eq + " + ";
                    if (j != 0)
                        eq = eq + "C" + n + " * x^" + j + " * e^(" + lE.getEq().get(i).getValue() + " * x)";
                    else
                        eq = eq + "C" + n + " * e^(" + lE.getEq().get(i).getValue() + " * x)";
                    n++;
                }
            }
            if (lE.getEq().get(i).isI()) {
                for (int j = 0; j < lE.getEq().get(i).getQuantity() / 2; j++) {
                    if (!eq.equals("")) eq = eq + " + ";
                    eq = eq + "e^(" + lE.getEq().get(i).getIValue().getR() + " * x)";
                    eq = eq + " * (C" + n + " * cos(" + lE.getEq().get(i).getIValue().getI() + " * x) + ";
                    eq = eq + "C" + (n+1) + " * sin(" + lE.getEq().get(i).getIValue().getI() + " * x))";
                }
                n += 2;
            }
        }

        eq = "y = " + eq;
    }

    public String getEq() {
        return eq;
    }

    private static void test() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(2, 1));
        k.add(new Ratio(1, -4));
        k.add(new Ratio(0, 5));
        ILDE f = new ILDE(k, "", 1);
        f.solve();
        System.out.println(f.getEq());
    }

    public static void main(String[] args) {
        test();
    }
}
