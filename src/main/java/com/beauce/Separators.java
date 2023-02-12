package com.beauce;

import java.util.List;
import java.util.regex.Pattern;

public class Separators {

    private static final String COMMA = ",";
    private static final String LINE_BREAK = "\n";
    private static final String OPEN_BRACKET = "[";
    private static final String CLOSED_BRACKET = "]";
    private static final String DOUBLE_SLASH = "//";
    private static final List<String> DEFAULT_SEPARATORS = List.of(COMMA, LINE_BREAK);

    private final String inputString;

    public Separators(String inputString) {
        this.inputString = inputString;
    }

    public List<String> getAll() {
        return startsWithSpecialSeparator()
                ? List.of(getSpecialSeparator())
                : DEFAULT_SEPARATORS;
    }

    private boolean startsWithSpecialSeparator() {
        return inputString.startsWith(DOUBLE_SLASH);
    }

    private String getSpecialSeparator() {
        var specialSeparator = extractSpecialSeparatorFromHeader();
        if (specialSeparatorIsSurroundedByBrackets(specialSeparator)) {
            return escapePattern(removeBracketsFromSpecialSeparator(specialSeparator));
        } else {
            return escapePattern(specialSeparator);
        }
    }

    private String extractSpecialSeparatorFromHeader() {
        return inputString.substring(2, inputString.indexOf(LINE_BREAK));
    }

    private static boolean specialSeparatorIsSurroundedByBrackets(String specialSeparator) {
        return specialSeparator.startsWith(OPEN_BRACKET) && specialSeparator.endsWith(CLOSED_BRACKET);
    }

    private static String escapePattern(String specialSeparator) {
        return Pattern.quote(specialSeparator);
    }

    private static String removeBracketsFromSpecialSeparator(String specialSeparator) {
        return specialSeparator.substring(1, specialSeparator.length() - 1);
    }

    public String removeSpecialSeparatorFromInputString() {
        return startsWithSpecialSeparator()
                ? inputString.substring(inputString.indexOf(LINE_BREAK) + 1)
                : inputString;
    }

}
