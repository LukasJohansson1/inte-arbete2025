package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EANBarcodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"400638133393", "50123456789001", "9638507", "735135374"})
    public void testEANBarcodeConstructor_invalidLength_throwsException(String code) {
        assertThrows(IllegalArgumentException.class, () -> new EANBarcode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+00638133393", "400638s333931", "5012D45678900", "9638t074", "7351A537", "400638!333931", "7351@537"})
    public void testEANBarcodeConstructor_invalidCharacters_throwsException(String code) {
        assertThrows(IllegalArgumentException.class, () -> new EANBarcode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"8901234567891", "1234567890123", "23456780", "12345671"})
    public void testEANBarcodeConstructor_invalidCheckDigit_throwsException(String code) {
        assertThrows(IllegalArgumentException.class, () -> new EANBarcode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEANBarcodeConstructor_validBarcodes(String code) {
        EANBarcode ean = new EANBarcode(code);
        assertEquals(code, ean.code());
    }

    @Test
    public void testEANBarcodeConstructor_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new EANBarcode(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEquals_shouldBeEqual(String code) {
        assertEquals(new EANBarcode(code), new EANBarcode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEquals_sameObject(String code) {
        EANBarcode ean = new EANBarcode(code);
        assertEquals(ean, ean);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEquals_differentCodes_notEquals(String code) {
        assertFalse(new EANBarcode("4003994155486").equals(new EANBarcode(code)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEquals_differentType_notEquals(String code) {
        assertFalse(new EANBarcode(code).equals(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testEquals_null_notEquals(String code) {
        assertFalse(new EANBarcode(code).equals(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"4006381333931", "5012345678900", "96385074", "73513537"})
    public void testHashCode_equalsObjects(String code) {
        EANBarcode ean1 = new EANBarcode(code);
        EANBarcode ean2 = new EANBarcode(code);
        assertEquals(ean1.hashCode(), ean2.hashCode());
    }
}
