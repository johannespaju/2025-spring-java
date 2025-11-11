package exceptions.numbers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NumberConverter {
    private final Properties properties;

    public NumberConverter(String lang) {
        String formatLangFile = "src/exceptions/numbers/numbers_%s.properties".formatted(lang);
        properties = new Properties();
        FileInputStream is = null;

        try {
            is = new FileInputStream(formatLangFile);
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.ISO_8859_1);
            properties.load(reader);

            if (properties.isEmpty()) {
                throw new MissingTranslationException(lang);
            }
        } catch (IOException e) {
            throw new MissingLanguageFileException(lang, e);
        } catch (IllegalArgumentException e) {
            throw new BrokenLanguageFileException(lang, e);
        } finally {
            close(is);
        }
    }

    private static void close(FileInputStream is) {
        if (is == null) {
            return;
        }

        try {
            is.close();
        } catch (IOException ignore) {
            System.out.println("Unable to close file " + is);
        }
    }

    public String numberInWords(Integer number) {
        String directTranslation = getDirectTranslation(number);
        if (directTranslation != null) {
            return directTranslation;
        }

        int hundreds = number / 100;
        int remainder = number % 100;

        StringBuilder result = new StringBuilder();

        if (hundreds > 0) {
            appendHundredsComponent(result, hundreds);

            if (remainder > 0) {
                result.append(properties.getProperty("hundred-after-delimiter", " "));
            }
        }

        if (remainder > 0) {
            appendRemainder(result, remainder);
        }

        return result.toString();
    }

    private String getDirectTranslation(int number) {
        String key = String.valueOf(number);
        return properties.containsKey(key) ? properties.getProperty(key) : null;
    }

    private void appendHundredsComponent(StringBuilder result, int hundreds) {
        result.append(properties.getProperty(String.valueOf(hundreds)));
        result.append(properties.getProperty("hundred-before-delimiter", " "));
        result.append(properties.getProperty("hundred"));
    }

    private void appendRemainder(StringBuilder result, int remainder) {
        String directRemainder = getDirectTranslation(remainder);
        if (directRemainder != null) {
            result.append(directRemainder);
            return;
        }

        int tens = remainder / 10;
        int ones = remainder % 10;

        if (tens == 1) {
            appendTeenNumber(result, ones);
        } else {
            appendTensAndOnes(result, tens, ones);
        }
    }

    private void appendTeenNumber(StringBuilder result, int ones) {
        String teenKey = "1" + ones;
        if (properties.containsKey(teenKey)) {
            result.append(properties.getProperty(teenKey));
        } else {
            result.append(properties.getProperty(String.valueOf(ones)));
            result.append(properties.getProperty("teen"));
        }
    }

    private void appendTensAndOnes(StringBuilder result, int tens, int ones) {
        if (tens > 0) {
            String tensKey = String.valueOf(tens * 10);
            if (properties.containsKey(tensKey)) {
                result.append(properties.getProperty(tensKey));
            } else {
                result.append(properties.getProperty(String.valueOf(tens)));
                result.append(properties.getProperty("tens-suffix"));
            }

            if (ones > 0) {
                result.append(properties.getProperty("tens-after-delimiter", "-")); // Default to "-" if not defined
                result.append(properties.getProperty(String.valueOf(ones)));
            }
        } else if (ones > 0) {
            result.append(properties.getProperty(String.valueOf(ones)));
        }
    }
}
