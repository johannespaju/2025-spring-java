package inheritance.stack;

import java.util.Stack;

public class LoggingStack extends Stack<Integer> {

    @Override
    public Integer pop() {
        Integer result = super.pop();
        System.out.println("Pop result: " + result);
        return result;
    }

    @Override
    public Integer push(Integer item) {
        Integer result = super.push(item);
        System.out.println("Pushed " + result);
        return result;
    }
    public void pushAll(int... items) {
        for (int item : items) {
            push(item);
        }
    }
}
