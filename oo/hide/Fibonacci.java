package oo.hide;

public class Fibonacci {
    private int oneTrailing = 0;
    private int value = 0;

    public int nextValue() {
        int result = value;
        int twoTrailing = oneTrailing;
        oneTrailing = value;
        if (value < 1) {
            value++;
        }
        else {
            value = oneTrailing + twoTrailing;
        }
        return result;
    }
}
