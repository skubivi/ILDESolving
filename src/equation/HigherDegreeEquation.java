package equation;

import java.util.ArrayList;

public class HigherDegreeEquation {
    private ArrayList<Ratio> k;
    private ArrayList<Double> eqR = new ArrayList<>();
    private ArrayList<INumb> eqI = new ArrayList<>();

    HigherDegreeEquation(ArrayList<Ratio> k) {
        this.k = k;
    }

    private int maxDegree(ArrayList<Ratio> k) {
        int max = 0;
        for (int i = 0; i < k.size(); i++) {
            if (k.get(i).getDegree() > max) max = k.get(i).getDegree();
        }
        return max;
    }

    private int findValueOfDegree(ArrayList<Ratio> k, int degree) {
        for (int i = 0; i < k.size(); i++) {
            if (k.get(i).getDegree() == degree) return k.get(i).getValue();
        }
        return 0;
    }

    private ArrayList<Integer> findDivisors(int a) {
        a = Math.abs(a);
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 1; i <= a; i++) {
            if (a % i == 0) d.add(i);
            if (a % i == 0) d.add(-i);
        }
        return d;
    }

    private int findRoot(ArrayList<Ratio> k) {
        int freeMember = findValueOfDegree(k, 0);
        ArrayList<Integer> d = findDivisors(freeMember);
        for (int i = 0; i < d.size(); i++) {
            int sum = 0;
            for (int j = 0; j < k.size(); j++) {
                sum += (int) (k.get(j).getValue() * Math.pow(d.get(i), k.get(j).getDegree()));
            }
            if (sum == 0) return d.get(i);
        }
        return -10000;
    }

    private ArrayList<Ratio> gScheme(ArrayList<Ratio> k) {
        ArrayList<Ratio> kN = new ArrayList<>();
        int root = findRoot(k);
        int sum = 0;
        for (int i = maxDegree(k); i > 0; i--) {
            sum =findValueOfDegree(k, i) + root * sum;
            kN.add(new Ratio(i - 1, sum));
        }
        return kN;
    }

    public void solve() {
        solve(this.k);
    }

    private void solve(ArrayList<Ratio> k) {
        if (maxDegree(k) > 2 && findRoot(k) != -10000) {
            eqR.add((double) findRoot(k));
            solve(gScheme(k));
        }
        if (maxDegree(k) == 2) {
            double a = findValueOfDegree(k, 2);
            double b = findValueOfDegree(k, 1);
            double c = findValueOfDegree(k, 0);
            double d = b * b - 4 * a * c;
            if (d > 0) {
                eqR.add(((-b + Math.sqrt(d)) / (2 * a)));
                eqR.add(((-b - Math.sqrt(d)) / (2 * a)));
            } else if (d == 0) {
                eqR.add(-b / (2 * a));
            } else if (d < 0) {
                eqI.add(new INumb(-b / (2 * a), Math.sqrt(Math.abs(d)) / (2 * a)));
                eqI.add(new INumb(-b / (2 * a), -Math.sqrt(Math.abs(d)) / (2 * a)));
            }
        }
    }

    public ArrayList<Double> getEqR() {
        return eqR;
    }

    public ArrayList<INumb> getEqI() {
        return eqI;
    }

    public static void main(String[] args) {
        ArrayList<Ratio> k = new ArrayList<>();
        k.add(new Ratio(4, 1));
        k.add(new Ratio(3, 4));
        k.add(new Ratio(2, -1));
        k.add(new Ratio(1, -16));
        k.add(new Ratio(0, -12));
        HigherDegreeEquation f = new HigherDegreeEquation(k);
        f.solve();
        System.out.println("Вещественные корни");
        for (int i = 0; i < f.getEqR().size(); i++) {
            System.out.println(f.getEqR().get(i));
        }
        System.out.println("Мнимые корни");
        for (int i = 0; i < f.getEqI().size(); i++) {
            System.out.println(f.getEqI().get(i).getR() + " " + f.getEqI().get(i).getI());
        }
    }
}
