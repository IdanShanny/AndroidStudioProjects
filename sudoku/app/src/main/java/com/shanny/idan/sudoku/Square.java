package com.shanny.idan.sudoku;

public class Square {
    String number;

    public Square() {
        String number = "0";
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
