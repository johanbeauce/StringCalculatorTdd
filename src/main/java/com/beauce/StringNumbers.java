package com.beauce;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringNumbers {

    private static final int ZERO = 0;
    private static final String COMMA = ",";
    private static final String LINE_BREAK = "\n";
    private static final List<String> DEFAULT_SEPARATORS = List.of(COMMA, LINE_BREAK);
    private static final IntPredicate NOT_GREATER_THAN_1000 = number -> number <= 1000;

    private final String inputString;

    public StringNumbers(String numbers) {
        this.inputString = numbers;
    }

    public int calculateSumOfNumbers() {
        if (StringUtils.isEmpty(inputString)) {
            return ZERO;
        }

        var numbers = getNumbers(getNumbersAsString(), getSeparators()).toList();
        assertNoNegativeNumbers(numbers);
        return numbers.stream()
                .mapToInt(Integer::parseInt)
                .filter(NOT_GREATER_THAN_1000)
                .sum();
    }

    private void assertNoNegativeNumbers(List<String> numbers) {
        var negativeNumbers = getNegativeNumbers(numbers);
        if (!negativeNumbers.isEmpty()) {
            throw new NegativesNotAllowedException(negativeNumbers);
        }
    }

    private List<String> getNegativeNumbers(List<String> numbers) {
        return numbers.stream()
                .filter(number -> number.startsWith("-"))
                .toList();
    }

    private boolean startsWithSpecialSeparator() {
        return inputString.startsWith("//");
    }

    private String getSpecialSeparator() {
        var specialSeparator = inputString.substring(2, inputString.indexOf(LINE_BREAK));
        if (specialSeparator.startsWith("[") && specialSeparator.endsWith("]")) {
            return escapePattern(specialSeparator.substring(1, specialSeparator.length() - 1));
        } else {
            return escapePattern(specialSeparator);
        }
    }

    private String escapePattern(String specialSeparator) {
        return Pattern.quote(specialSeparator);
    }

    private String getNumbersAsString() {
        return startsWithSpecialSeparator()
                ? extractSpecialSeparator()
                : inputString;
    }

    private String extractSpecialSeparator() {
        return inputString.substring(inputString.indexOf(LINE_BREAK) + 1);
    }

    private List<String> getSeparators() {
        return startsWithSpecialSeparator()
                ? List.of(getSpecialSeparator())
                : DEFAULT_SEPARATORS;
    }

    private Stream<String> getNumbers(String numbersAsString,
                                      List<String> separators) {
        Stream<String> numbers = Stream.of(numbersAsString);
        for (String separator : separators) {
            numbers = getNumbers(numbers, separator);
        }
        return numbers;
    }

    private Stream<String> getNumbers(String numbersAsString,
                                      String specialSeparator) {
        return Arrays.stream(numbersAsString.split(specialSeparator));
    }

    private Stream<String> getNumbers(Stream<String> numbersAsString,
                                      String separator) {
        return numbersAsString
                .flatMap(number -> getNumbers(number, separator));
    }
}
