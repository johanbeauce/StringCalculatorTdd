package com.beauce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ATOMIC_REFERENCE_FIELD_UPDATER;

@DisplayName("Given StringCalculator class")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    void when_empty_should_return_0() {
        assertThat(stringCalculator.add("")).isZero();
    }

    @ParameterizedTest(name = "\"{0}\" should return {1}")
    @MethodSource("getSingleNumber")
    void when_add_a_number_as_string_should_return_this_number_as_int(String number, int sum) {
        assertThat(stringCalculator.add(number)).isEqualTo(sum);
    }

    public static Stream<Arguments> getSingleNumber() {
        return Stream.of(
                Arguments.of("4", 4),
                Arguments.of("5", 5)
        );
    }

    @ParameterizedTest(name = "\"{0}\" should return {1}")
    @MethodSource("getMultipleNumberSeparatedByComma")
    void when_give_list_number_separated_by_comma_give_sum_of_these_numbers(String numbers, int sum) {
        assertThat(stringCalculator.add(numbers)).isEqualTo(sum);
    }

    public static Stream<Arguments> getMultipleNumberSeparatedByComma() {
        return Stream.of(
                Arguments.of("2,3", 5),
                Arguments.of("4,3", 7),
                Arguments.of("1,2,3,4,5,6,7,8,9", 45)
        );
    }
}
