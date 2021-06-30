package ilde;

import slau.*;
import equation.*;

import java.util.ArrayList;

import static ilde.StringRatio.*;

public class ILDE {
    private ArrayList<Ratio> k;
    private String f;
    private String eq = "";
    private int kind;
    ChEquation lE;

    //Конструктор
    public ILDE(ArrayList<Ratio> k, String f, int kind) {
        makeInt(k);
        this.k = ratioToFullRatio(k);
        this.f = f;
        this.kind = kind;
        lE = new ChEquation(k);
        lE.solve();
    }

    private static int decimal (double d){
        String[] splitter = String.valueOf(d).split("\\.");
        return splitter[1].length();
    }

    private static void makeInt(ArrayList<Ratio> k) {
        int max = 0;
        for (int i = 0; i < k.size(); i++){
            int temp = decimal(k.get(i).getDValue());
            if (temp > max) max = temp;
        }
        for (int i = 0; i < k.size(); i++){
            k.get(i).makeInt(max);
        }
    }

    //Решение правой части
    private String rightE() {
        String rE = "";
        if (kind == 1) rE += k1();
        return rE;
    }

    //Преобразование списка Ratio в список Fraction
    private static ArrayList<Fraction> ratioToFraction(ArrayList<Ratio> r) {
        ArrayList<Fraction> f = new ArrayList<>();
        ArrayList<Ratio> nR = ratioToFullRatio(r);
        for (int i = 0; i < nR.size(); i++) {
            f.add(new Fraction(nR.get(i).getValue()));
        }
        return f;
    }

    //Функция дополнения коэффициентов до полных (например если коэффициент при x^2 равен 0, то он так и будет записан)
    private static ArrayList<Ratio> ratioToFullRatio(ArrayList<Ratio> r) {
        ArrayList<Ratio> nR = new ArrayList<>();
        sortRatio(r);
        int maxDegree = r.get(0).getDegree();
        for (int i = 0; i <= maxDegree; i++) {
            nR.add(new Ratio(maxDegree - i, 0));
        }

        for (int i = 0; i < r.size(); i++) {
            nR.set(maxDegree - r.get(i).getDegree(), new Ratio(r.get(i)));
        }

        return nR;
    }

    //Функция дополнения коэффициентов до полных + дополнение списка до коэффициента при x^degree
    private static ArrayList<Ratio> ratioToFullRatio(ArrayList<Ratio> r, int degree) {
        ArrayList<Ratio> nR = ratioToFullRatio(r);
        ArrayList<Ratio> nNR;
        if (nR.size() <= degree) {
            while (nR.size() <= degree) {
                nR.add(0, new Ratio(nR.size(), 0));
            }
            nNR = nR;
        } else {
            nNR = new ArrayList<>();
            for (int i = 0; i <= degree; i++) {
                nNR.add(new Ratio(nR.get(nR.size() - i - 1)));
            }
        }
        sortRatio(nNR);
        return nNR;
    }

    //Производная многочлена
    private static ArrayList<Ratio> derivativeK1(ArrayList<Ratio> r) {
        ArrayList<Ratio> nR = new ArrayList<>();
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).getDegree() != 0) {
                nR.add(new Ratio(r.get(i).getDegree() - 1, r.get(i).getValue() * r.get(i).getDegree()));
            }
        }
        sortRatio(nR);
        return nR;
    }

    //Преобразование левой части в матрицу для реализации метода неопределённых коэфициентов
    private static ArrayList<ArrayList<Fraction>> eqToMatrix(ArrayList<Ratio> k, int maxDegree, int s) {
        ArrayList<Ratio> nR = ratioToFullRatio(k, maxDegree + s);
        ArrayList<Ratio> tempR = new ArrayList<>();
        for (int i = s; i <= maxDegree + s; i++) {
            tempR.add(new Ratio(i, 1));
        }
        for (int i = 0; i < s; i++) {
            tempR = derivativeK1(tempR);
        }
        ArrayList<ArrayList<Fraction>> f = new ArrayList<>();

        for (int i = 0; i <= maxDegree; i++) {
            ArrayList<Fraction> temp = new ArrayList<>();
            for (int j = 0; j <= maxDegree; j++) {
                temp.add(new Fraction(0));
            }
            f.add(temp);
        }

        for (int i = 0; i <= maxDegree; i++) {
            for (int j = 0; j <= maxDegree; j++) {
                if (j + i > maxDegree) break;
                f.get(j + i).set(j, new Fraction(nR.get(maxDegree - i).getValue() * tempR.get(j).getValue()));
            }
            tempR = derivativeK1(tempR);
        }

        return f;
    }

    //Реализация метода неопределённых коэффициентов, если функция в правой части - это многочлен
    private String k1() {
        String rE = "";

        ArrayList<Ratio> temp = stringToRatio(f);
        int maxDegree = temp.get(0).getDegree();
        ArrayList<Fraction> nF = ratioToFraction(temp);

        int c = 0;
        for (int i = 0; i < lE.getEq().size(); i++) {
            if (lE.getEq().get(i).getValue() == 0) {
                c = lE.getEq().get(i).getQuantity();
                break;
            }
        }

        ArrayList<ArrayList<Fraction>> koef = eqToMatrix(k, maxDegree, c);
        MatrixSLAU r = new MatrixSLAU(koef, nF);
        r.solve();
        for (int i = 0; i < r.getEq().size(); i++) {
            if (!rE.equals(""))
                rE = rE + " + ";
            rE = rE + r.getEq().get(i).toString() + " * t^" + (maxDegree - i + c);
        }

        return rE;
    }

    //Метод решения
    public void solve() {
        int n = 1;
        for (int i = 0; i < lE.getEq().size(); i++) {
            if (!lE.getEq().get(i).isI()) {
                for (int j = 0; j < lE.getEq().get(i).getQuantity(); j++) {
                    if (!eq.equals("")) eq = eq + " + ";
                    if (j != 0)
                        eq = eq + "C" + n + " * t^" + j + " * e^(" + lE.getEq().get(i).getValue() + " * t)";
                    else
                        eq = eq + "C" + n + " * e^(" + lE.getEq().get(i).getValue() + " * t)";
                    n++;
                }
            }
            if (lE.getEq().get(i).isI()) {
                for (int j = 0; j < lE.getEq().get(i).getQuantity() / 2; j++) {
                    if (!eq.equals("")) eq = eq + " + ";
                    eq = eq + "e^(" + lE.getEq().get(i).getIValue().getR() + " * t)";
                    eq = eq + " * (C" + n + " * cos(" + lE.getEq().get(i).getIValue().getI() + " * t) + ";
                    eq = eq + "C" + (n + 1) + " * sin(" + lE.getEq().get(i).getIValue().getI() + " * t))";
                }
                n += 2;
            }
        }

        if (!f.equals("") && !f.equals("0")) {
            String rE = rightE();
            if (!rE.equals(""))
                eq += " + " + rE;
        }

        eq = "y = " + eq;
    }

    //Возвращает ответ
    public String getEq() {
        return eq;
    }

    private static void test() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(2, 1));
        k.add(new Ratio(1, -7));
        k.add(new Ratio(0, 12));
        ILDE f = new ILDE(k, "", 1);
        f.solve();
        System.out.println(f.getEq());
    }

    private static void test2() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(3, 3));
        k.add(new Ratio(2, 2));
        k.add(new Ratio(1, 4));
        k.add(new Ratio(0, 1));
        ArrayList<ArrayList<Fraction>> ratio = eqToMatrix(k, 4, 0);
        for (int i = 0; i < ratio.size(); i++) {
            for (int j = 0; j < ratio.size(); j++) {
                ratio.get(i).get(j).print();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void test3() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(4, 3));
        k.add(new Ratio(1, -7));
        k = ratioToFullRatio(k);
        for (int i = 0; i < k.size(); i++) {
            System.out.println(k.get(i).getDegree() + " " + k.get(i).getValue());
        }
    }

    private static void test4() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(2, 0.5));
        k.add(new Ratio(1, -3.5));
        k.add(new Ratio(0, 6));
        ILDE f = new ILDE(k, "", 1);
        f.solve();
        System.out.println(f.getEq());
    }

    private static void test5(){
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(2, 0.5));
        k.add(new Ratio(1, -3.5));
        k.add(new Ratio(0, 6));
        makeInt(k);
        for (int i = 0; i < k.size(); i++){
            System.out.println(k.get(i).getValue() + " " + k.get(i).getDegree());
        }
    }

    public static void main(String[] args) {
        test();
    }
}
