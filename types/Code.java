package types;

import java.util.Arrays;
import java.util.Random;

public class Code {

    public static void main(String[] args) {

        int[] numbers = {1, 3, -2, 9};

//        System.out.println(sum(numbers)); // 11
//        System.out.println(average(numbers));
//        System.out.println(asString(numbers));
    }

    public static int sum(int[] numbers) {
        int sum = 0;

        for (int item : numbers) {
            sum += item;
        }

        return sum;
    }

    public static double average(int[] numbers) {
        return Double.valueOf(sum(numbers)) / numbers.length;
    }

    public static Integer minimumElement(int[] integers) {
        if (integers.length == 0) {
            return null;
        }

        int minimum = integers[0];
        for (int number : integers) {
            if (number < minimum) {
                minimum = number;
            }
        }
        return minimum;
    }

    public static String asString(int[] elements) {
        String result = "";
        if (elements.length == 0) {
            return result;
        }
        result += elements[0];
        for (int i = 1; i < elements.length; i++) {
            result += ", " + elements[i];
        }

        return result;
    }

    public static Character mode(String input) {
        int modeCount = 0;
        Character mode = null;

        for (char c1 : input.toCharArray()) {
            int count = 0;
            for (int i = 0; i < input.length(); i++) {
                if (c1 == input.charAt(i)) {
                    count++;
                }
            }
            if (count > modeCount) {
                modeCount = count;
                mode = c1;
            }
        }
        return mode;
    }

    public static String squareDigits(String s) {
        String result = "";
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (Character.isDigit(c)) {
                int number = Integer.parseInt(Character.toString(c));
                int squared = number * number;
                result += squared;
            } else {
                result += c;
            }
        }
        return result;
    }

    public static int isolatedSquareCount() {
        int isolatedCount = 0;

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (isIsolated(i, j)) {
                    isolatedCount++;
                }
            }
        }

        return isolatedCount;
    }

    public static boolean isIsolated(int row, int col) {
        boolean[][] matrix = getSampleMatrix();
        // printMatrix(matrix);

        int[] directions = {-1, 0, 1};

        for (int rowDirection : directions) {
            for (int colDirection : directions) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }
                int newRow = row + rowDirection;
                int newCol = col + colDirection;

                if (newRow <= 9 && newRow >= 0 && newCol <= 9 && newCol >= 0) {
                    if (matrix[newRow][newCol]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void printMatrix(boolean[][] matrix) {
        for (boolean[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static boolean[][] getSampleMatrix() {
        boolean[][] matrix = new boolean[10][10];

        Random r = new Random(5);
        for (boolean[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = r.nextInt(5) < 2;
            }
        }

        return matrix;
    }
}
