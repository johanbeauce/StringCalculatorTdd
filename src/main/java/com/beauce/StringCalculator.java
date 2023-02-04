package com.beauce;

import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

    private static final String COMMA = ",";

    public int add(String numbersAsString) {
        if (StringUtils.isEmpty(numbersAsString)) {
            return 0;
        } else {
            var numbers = numbersAsString.split(COMMA);
            var sum = 0;
            for (String number : numbers) {
                sum += Integer.parseInt(number);
            }
            return sum;
        }
    }
}
