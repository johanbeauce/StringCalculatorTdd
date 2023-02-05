package com.beauce;

public class StringCalculator {
    public int add(String numbersAsString) {
        StringNumbers stringNumbers = new StringNumbers(numbersAsString);
        return stringNumbers.calculateSumOfNumbers();
    }
}
