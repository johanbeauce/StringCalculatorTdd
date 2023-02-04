package com.beauce;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    private static final int ZERO = 0;
    private static final String COMMA = ",";
    private static final String LINE_BREAK = "\n";

    public int add(String numbersAsString) {
        if (StringUtils.isEmpty(numbersAsString)) {
            return ZERO;
        } else {
            var numbers = getListOfNumbers(numbersAsString);
            return calculateSumOfNumbers(numbers);
        }
    }

    private static ArrayList<Integer> getListOfNumbers(String numbersAsString) {
        var numbers = new ArrayList<Integer>();
        var numbersFromComma = numbersAsString.split(COMMA);
        for (String numberFromComma : numbersFromComma) {
            var numbersFromBreak = numberFromComma.split(LINE_BREAK);
            for (String numberFromBreak : numbersFromBreak) {
                numbers.add(Integer.parseInt(numberFromBreak));
            }
        }
        return numbers;
    }

    private static int calculateSumOfNumbers(List<Integer> numbers) {
        var sum = ZERO;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum;
    }
}
