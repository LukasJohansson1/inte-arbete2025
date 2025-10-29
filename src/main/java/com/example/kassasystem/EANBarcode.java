package com.example.kassasystem;

/**
 * @param code Sparar barcoden i string format
 */
public record EANBarcode(String code) {

    private static final int SHORT_EAN_LENGTH = 8;
    private static final int LONG_EAN_LENGTH = 13;

    public EANBarcode {
        if (code == null) {
            throw new IllegalArgumentException("The barcode can not be null");
        }

        validateLength(code);
        validateOnlyDigits(code);
        validateCheckDigit(code);

    }

    private void validateLength(String code) {
        if (code.length() != SHORT_EAN_LENGTH && code.length() != LONG_EAN_LENGTH) {
            throw new IllegalArgumentException("The length of the barcode must be 8 or 13");
        }
    }

    private void validateOnlyDigits(String code) {
        if (!code.matches("\\d+")) {
            throw new IllegalArgumentException("The barcode must consist of digits only");
        }
    }

    private void validateCheckDigit(String code) {  // Räknar ut om kontrollsiffran är korrekt enligt EAN-standard
        int lastDigitIndex;

        if (code.length() == LONG_EAN_LENGTH) {
            lastDigitIndex = LONG_EAN_LENGTH - 1;
        } else {
            lastDigitIndex = SHORT_EAN_LENGTH - 1;
        }

        int checkSum = 0;
        int weight;

        for (int i = 0; i < lastDigitIndex; i++) {
            weight = getIndexWeight(code, i);
            checkSum += Character.getNumericValue(code.charAt(i)) * weight;
        }

        int expectedLastDigit = calculateCheckDigit(checkSum);

        if (expectedLastDigit != Character.getNumericValue(code.charAt(lastDigitIndex))) {
            throw new IllegalArgumentException("The barcode has an invalid check digit");
        }
    }

    private int calculateCheckDigit(int checkSum) {
        return (checkSum + 9) / 10 * 10 - checkSum;
    }

    private int getIndexWeight(String code, int i) { // Hämtar weight för varje position i talet enligt EAN-standard
        if (code.length() == LONG_EAN_LENGTH) {
            return i % 2 == 0 ? 1 : 3;
        } else {
            return i % 2 == 0 ? 3 : 1;
        }
    }
}