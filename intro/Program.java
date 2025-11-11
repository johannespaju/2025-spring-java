package intro;

public class Program {

    public static void main(String[] args) {

        int integer = asInteger("11001101");

        System.out.println(integer); // 205

        System.out.println(asBinaryString(205)); // 11001101
    }

    private static String reverse(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            result = input.charAt(i) + result;
        }
        return result;
    }

    public static String asBinaryString(int input) {
        String result = "";
        while (input > 0) {
            if (input % 2 == 0) {
                result += '0';
            } else {
                result += '1';
            }

            input /= 2;
        }
        return reverse(result);
    }

    public static int asInteger(String input) {
        int result = 0;
        String reversed = reverse(input);
        for (int i = 0; i < input.length(); i++) {
            if (reversed.charAt(i) == '1') {
                result += pow(2, i);
            }
        }
        return result;
    }

    private static int pow(int arg, int power) {
        // Java has Math.pow() but this time write your own implementation.
        int result = 1;
        for (int i = 0; i < power; i++) {
            result *= arg;
        }

        return result;
    }
}
