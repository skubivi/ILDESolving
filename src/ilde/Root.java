package ilde;

import equation.INumb;

//Вспомогательный класс, отображающий значение корня и его количество
public class Root {
    private double value;
    private INumb iValue;
    private int quantity;
    boolean I;

    //Конструктор рационального корня
    Root(double value, int quantity) {
        this.value = value;
        this.quantity = quantity;
        I = false;
    }

    //Конструктор комплексного корня
    Root(INumb iValue, int quantity) {
        this.iValue = iValue;
        this.quantity = quantity;
        I = true;
    }

    //Проверка комплексный ли корень
    public boolean isI() {
        return I;
    }

    //Геттеры
    public double getValue() {
        return value;
    }

    public INumb getIValue() {
        return iValue;
    }

    public int getQuantity() {
        return quantity;
    }

    //Увеличение кол-ва корней с данным значением
    public void increaseQ() {
        quantity++;
    }

    //Вывод для отладки
    public void print() {
        if (!isI()) System.out.println(getValue() + " " + getQuantity());
        else System.out.println(getIValue().getR() + " " + getIValue().getI() + " " + getQuantity());
    }
}
