package ilde;

import equation.Ratio;

import java.util.ArrayList;

public class StringRatio {
    public static String ratioToString(ArrayList<Ratio> r) {
        sortRatio(r);
        String nR = "";

        for (int i = 0; i < r.size(); i++) {
            if (!nR.equals("")) {
                if (r.get(i).getValue() < 0)
                    nR = nR + " - ";
                else
                    nR = nR + " + ";
            } else if (r.get(i).getValue() < 0)
                nR = nR + "-";
            if (r.get(i).getDegree() != 0)
                nR = nR + Math.abs(r.get(i).getValue()) + " * t^" + r.get(i).getDegree();
            else
                nR = nR + Math.abs(r.get(i).getValue());
        }

        return nR;
    }

    public static void sortRatio(ArrayList<Ratio> r) {
        for (int i = 0; i < r.size(); i++) {
            for (int j = 0; j < r.size() - 1; j++) {
                if (r.get(j).getDegree() < r.get(j + 1).getDegree()) {
                    Ratio temp = new Ratio(r.get(j));
                    r.get(j).copy(r.get(j + 1));
                    r.get(j + 1).copy(temp);
                }
            }
        }
    }

    public static ArrayList<Ratio> stringToRatio(String r) {
        ArrayList<Ratio> nR = new ArrayList<>();
        String r2 = deleteSpace(r);

        while (r2.length() > 0) {
            int c1 = findC(r2, '^');
            int c2 = findPM(r2);
            if (c2 != -1) {
                String r2s1 = r2.substring(0, c1 - 2);
                String r2s2 = r2.substring(c1 + 1, c2);
                int v = Integer.parseInt(r2s1);
                int d = Integer.parseInt(r2s2);
                nR.add(new Ratio(d, v));
                r2 = r2.substring(c2);
            } else {
                if (c1 != -1) {
                    String r2s1 = r2.substring(0, c1 - 2);
                    String r2s2 = r2.substring(c1 + 1);
                    int v = Integer.parseInt(r2s1);
                    int d = Integer.parseInt(r2s2);
                    nR.add(new Ratio(d, v));
                } else {
                    int v = Integer.parseInt(r2);
                    nR.add(new Ratio(0, v));
                }
                r2 = "";
            }
        }

        sortRatio(nR);
        return nR;
    }

    private static String deleteSpace(String r) {
        return r.replaceAll("\\s+", "");
    }

    private static int findPM(String r) {
        for (int i = 1; i < r.length(); i++) {
            if (r.charAt(i) == '+' || r.charAt(i) == '-') return i;
        }
        return -1;
    }

    private static int findC(String r, char ch) {
        for (int i = 0; i < r.length(); i++) {
            if (r.charAt(i) == ch) return i;
        }
        return -1;
    }


    private static void test() {
        ArrayList<Ratio> r = new ArrayList<>();
        r.add(new Ratio(1, 1));
        String s = ratioToString(r);
        r = stringToRatio(s);
        s = ratioToString(r);
        System.out.println(s);
    }

    public static void main(String[] args) {
        test();
    }
}
