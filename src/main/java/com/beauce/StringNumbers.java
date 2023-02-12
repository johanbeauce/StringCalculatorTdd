package com.beauce;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

public class StringNumbers {

    private static final int ZERO = 0;
    private static final IntPredicate NOT_GREATER_THAN_1000 = number -> number <= 1000;
    private static final String NEGATIVE_SIGN = "-";

    private final String inputString;

    public StringNumbers(String numbers) {
        this.inputString = numbers;
    }

    public int calculateSumOfNumbers() {
        if (StringUtils.isEmpty(inputString)) {
            return ZERO;
        }

        var numbers = getNumbers();
        assertNoNegativeNumbers(numbers);
        return calculateSumOfNumbersLessThanOneThousand(numbers);
    }

    private List<String> getNumbers() {
        var separators = new Separators(inputString);
        Stream<String> numbers = Stream.of(separators.removeSpecialSeparatorFromInputString());
        for (String separator : separators.getAll()) {
            numbers = getNumbers(numbers, separator);
        }
        return numbers.toList();
    }

    private Stream<String> getNumbers(Stream<String> numbersAsString,
                                      String separator) {
        return numbersAsString
                .flatMap(number -> getNumbers(number, separator));
    }

    private Stream<String> getNumbers(String numbersAsString,
                                      String specialSeparator) {
        return Arrays.stream(numbersAsString.split(specialSeparator));
    }

    private void assertNoNegativeNumbers(List<String> numbers) {
        var negativeNumbers = getNegativeNumbers(numbers);
        if (!negativeNumbers.isEmpty()) {
            throw new NegativesNotAllowedException(negativeNumbers);
        }
    }

    private List<String> getNegativeNumbers(List<String> numbers) {
        return numbers
                .stream()
                .filter(number -> number.startsWith(NEGATIVE_SIGN))
                .toList();
    }

    private static int calculateSumOfNumbersLessThanOneThousand(List<String> numbers) {
        return numbers.stream()
                .mapToInt(Integer::parseInt)
                .filter(NOT_GREATER_THAN_1000)
                .sum();
    }
}
