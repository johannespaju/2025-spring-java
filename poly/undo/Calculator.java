package poly.undo;

import java.util.Stack;
import java.util.function.Function;

public class Calculator {

    private Stack<Function<Double, Double>>
            undoStack = new Stack<>();

    private double value;

    public void input(double value) {
        undoStack.add(x -> x - value);


        this.value = value;
    }

    public void add(double addend) {
        undoStack.add(x -> x - addend);

        value += addend;
    }

    public void multiply(double multiplier) {
        undoStack.add(x -> x / multiplier);

        value *= multiplier;
    }

    public double getResult() {
        return value;
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        value = undoStack.pop().apply(value);
    }
}
