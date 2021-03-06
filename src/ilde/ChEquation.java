package ilde;

import equation.*;

import java.util.ArrayList;

//Класс для решения характеристического уравнения
public class ChEquation {
    private ArrayList<Ratio> k;
    private ArrayList<Root> eq = new ArrayList<>();

    //Конструктор
    ChEquation(ArrayList<Ratio> k) {
        this.k = k;
    }

    //проверка на существование рационального корня
    private boolean exists(double d, ArrayList<Root> r) {
        boolean exist = false;
        for (int i = 0; i < r.size(); i++) {
            if (!r.get(i).isI()) {
                if (r.get(i).getValue() == d) exist = true;
            }
        }
        return exist;
    }

    //Проверка на существование комплексного корня
    private boolean exists(INumb d, ArrayList<Root> r) {
        boolean exist = false;
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).isI()) {
                if (r.get(i).getIValue().equal(d)) exist = true;
            }
        }
        return exist;
    }

    //Возвращает позицию рационального корня в списке
    private int findValue(double d, ArrayList<Root> r) {
        int f = -1;
        for (int i = 0; i < r.size(); i++) {
            if (!r.get(i).isI()) {
                if (r.get(i).getValue() == d) f = i;
            }
        }
        return f;
    }

    //Возвращает позицию комплексного корня в списке
    private int findValue(INumb d, ArrayList<Root> r) {
        int f = -1;
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).isI()) {
                if (r.get(i).getIValue().equal(d)) f = i;
            }
        }
        return f;
    }

    //Добавляет рациональный корень в список
    private void addRoot(double d, ArrayList<Root> r) {
        if (!exists(d, r)) r.add(new Root(d, 1));
        else {
            r.get(findValue(d, r)).increaseQ();
        }
    }

    //Добавляет комплексный корень в список
    private void addRoot(INumb d, ArrayList<Root> r) {
        if (!exists(d, r)) r.add(new Root(d, 1));
        else {
            r.get(findValue(d, r)).increaseQ();
        }
    }

    //Преобразует список рациональных корней в список корней с помощью класса Root
    private ArrayList<Root> changeRoot(ArrayList<Double> k) {
        ArrayList<Root> nR = new ArrayList<>();
        for (int i = 0; i < k.size(); i++) {
            addRoot(k.get(i), nR);
        }
        return nR;
    }

    //Преобразует список комплексных корней в список корней с помощью класса Root
    private ArrayList<Root> changeRootI(ArrayList<INumb> k) {
        ArrayList<Root> nR = new ArrayList<>();
        for (int i = 0; i < k.size(); i++) {
            addRoot(k.get(i), nR);
        }
        return nR;
    }

    //Решение характеристического уравнения с помощью класса HigherDegreeEquation
    //и преобразование его корней в список Root
    public void solve() {
        HigherDegreeEquation equation = new HigherDegreeEquation(k);
        equation.solve();
        ArrayList<Root> nR = changeRoot(equation.getEqR());
        for (int i = 0; i < nR.size(); i++) {
            eq.add(nR.get(i));
        }
        nR = changeRootI(equation.getEqI());
        for (int i = 0; i < nR.size(); i++) {
            eq.add(nR.get(i));
        }
    }

    //Возвращает корни
    public ArrayList<Root> getEq() {
        return eq;
    }

    private static void test() {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(3, 4));
        k.add(new Ratio(2, 16));
        k.add(new Ratio(1, -1));
        k.add(new Ratio(0, -4));
        ChEquation f = new ChEquation(k);
        f.solve();
        for (int i = 0; i < f.getEq().size(); i++) {
            f.getEq().get(i).print();
        }
    }

    public static void main(String[] args) {
        test();
    }
}
