package ilde;

import equation.INumb;

public class Root {
    private double value;
    private INumb iValue;
    private int quantity;
    boolean I;

    Root(double value, int quantity) {
        this.value = value;
        this.quantity = quantity;
        I = false;
    }

    Root(INumb iValue, int quantity) {
        this.iValue = iValue;
        this.quantity = quantity;
        I = true;
    }

    public boolean isI() {
        return I;
    }

    public double getValue() {
        return value;
    }

    public INumb getIValue() {
        return iValue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQ() {
        quantity++;
    }

    public void print() {
        if (!isI()) System.out.println(getValue() + " " + getQuantity());
        else System.out.println(getIValue().getR() + " " + getIValue().getI() + " " + getQuantity());
    }
}
