package types;

public class Pmd {

    public static void main(String[] args) {
        boolean[][] matrix = getSampleMatrix();

        printMatrix(matrix);

        System.out.println(containsTrueCell(matrix));
        System.out.println(findFirstTrueCell(matrix));
        System.out.println(countTrueRow(matrix));
    }

    private static void printMatrix(boolean[][] sampleMatrix) {
        for (boolean[] row : sampleMatrix) {
            for (boolean element : row) {
                System.out.print(element ? "X" : "O");
            }
            System.out.println();
        }
    }

    public static boolean containsTrueCell(boolean[][] matrix) {
        for (boolean[] row : matrix) {
            for (boolean element : row) {
                if (element) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int findFirstTrueCell(boolean[][] matrix) {
        int count = 0;
        for (boolean[] row : matrix) {
            for (boolean element : row) {
                count += 1;
                if (element) {
                    return count;
                }
            }

        }
        return -1;
    }

    // intentionally bad code
    public static int countTrueRow(boolean[][] matrix) {
        for (boolean[] row : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (row[j]) {
                    return countTrueCellsOnRow(matrix, row);
                }
            }
        }
        return -1;
    }

    private static int countTrueCellsOnRow(boolean[][] matrix, boolean[] row) {
        int count = 0;
        for (int k = 0; k < matrix.length; k++) {
            if (row[k]) {
                count++;
            }
        }

        return count;
    }

    private static boolean[][] getSampleMatrix() {
        boolean[][] matrix = new boolean[3][3];

        matrix[2][1] = true;
        matrix[2][2] = true;

        return matrix;
    }

}
