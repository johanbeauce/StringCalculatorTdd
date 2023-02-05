package com.beauce;

import java.util.List;

public class NegativesNotAllowedException extends RuntimeException {

    private static final String ERROR_NEGATIVES_NOT_ALLOWED = "error: negatives not allowed: ";
    private static final String DELIMITER = ",";

    public NegativesNotAllowedException(List<String> negativeNumbers) {
        super(ERROR_NEGATIVES_NOT_ALLOWED + String.join(DELIMITER, negativeNumbers));
    }
}
