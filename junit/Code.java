package junit;

public class Code {
    public static void main(String[] args) {

    }

    public static boolean isSpecial(int candidate) {
        return (candidate % 11) <= 3;
    }

    public static int longestStreak(String inputString) {
        if (inputString.isEmpty()) {
            return 0;
        }
        if (inputString.length() == 1) {
            return 1;
        }

        int record = 0;
        int streakCounter = 0;
        for (int i = 0; i < inputString.length(); i++) {
            char charFromString = inputString.charAt(i);
            for (int j = i; j < inputString.length(); j++) {
                char charAfterSearchableChar = inputString.charAt(j);
                if (charFromString == charAfterSearchableChar) {
                    streakCounter++;
                }
                else {
                    break;
                }
            }
            if (streakCounter > record) {
                record = streakCounter;
            }
            streakCounter = 0;
        }
        return record;
    }

    public static Character mode(String input) {
        if (input == null) {
            return null;
        }

        int modeCount = 0;
        Character mode = null;

        for (int i = 0; i < input.length(); i++) {
            int count = getCharacterCount(input, input.charAt(i));
            if (count > modeCount) {
                mode = input.charAt(i);
                modeCount = count;
            }
        }
        return mode;
    }

    public static int getCharacterCount(String allCharacters, char targetCharacter) {
        int currentCharCount = 0;
        for (int i = 0; i < allCharacters.length(); i++) {
            if (allCharacters.charAt(i) == targetCharacter) {
                currentCharCount++;
            }
        }
        return currentCharCount;
    }
    public static int integerInArrayCount(int integer, int[] array) {
        int count = 0;
        for (int i : array) {
            if (i == integer) {
                count++;
            }
        }
        return count;
    }

    public static int[] addNewSlotToArray(int element, int[] inputArray) {
        int[] outputArray = new int[inputArray.length + 1];
        int index = 0;
        for (int integer : inputArray) {
            outputArray[index] = integer;
            index++;
        }
        outputArray[index] = element;
        return outputArray;
    }

    public static int[] removeDuplicates(int[] integers) {
        int[] outputArray = new int[0];
        for (int integer : integers) {
            if (integerInArrayCount(integer, outputArray) == 0) {
                outputArray = addNewSlotToArray(integer, outputArray);
            }
        }
        return outputArray;
    }

    public static int sumIgnoringDuplicates(int[] integers) {
        int[] withoutDuplicates = removeDuplicates(integers);
        int sum = 0;
        for (int i : withoutDuplicates) {
            sum += i;
        }
        return sum;
    }

}
