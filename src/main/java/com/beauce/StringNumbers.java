package com.beauce;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StringNumbers {

    private static final int ZERO = 0;
    private static final String COMMA = ",";
    private static final String LINE_BREAK = "\n";
    private static final List<String> DEFAULT_SEPARATORS = List.of(COMMA, LINE_BREAK);

    private final String inputString;

    public StringNumbers(String numbers) {
        this.inputString = numbers;
    }

    public int calculateSumOfNumbers() {
        if (StringUtils.isEmpty(inputString)) {
            return ZERO;
        }

        var numbers = getNumbers(getNumbersAsString(), getSeparators()).toList();
        var negativeNumbers = numbers.stream()
                .filter(number -> number.startsWith("-"))
                .toList();
        if (negativeNumbers.isEmpty()) {
            return numbers.stream()
                    .mapToInt(Integer::parseInt)
                    .sum();
        } else {
            throw new RuntimeException("error: negatives not allowed: " + String.join(",", negativeNumbers));
        }

    }

    private boolean startsWithSpecialSeparator() {
        return inputString.startsWith("//");
    }

    private String getSpecialSeparator() {
        return inputString.substring(2, inputString.indexOf(LINE_BREAK));
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
