package com.example.kassasystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MembershipTest {

    @Test
    void testEmptyConstructor_shouldSetTierToBronze() {
        Membership membership = new Membership();
        assertEquals("Bronze", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -9867, Integer.MIN_VALUE})
    void testConstructor_withNegativePoints_shouldThrowException(int points) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Membership(points);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1243, 4999})
    void testConstructor_withPointsBetween0And4999_shouldGiveBronzeTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Bronze", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {5000, 6789, 9999})
    void testConstructor_withPointsBetween5000And9999_shouldGiveSilverTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Silver", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {10000, 23456, 24999})
    void testConstructor_withPointsBetween10000And9999_shouldGiveGoldTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Gold", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {25000, 234567, Integer.MAX_VALUE})
    void testConstructor_withPointsAbove250000_shouldGivePlatinumTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Platinum", membership.getTier());
    }

    @ParameterizedTest
    @CsvSource({
            "Bronze, 0",
            "Silver, 5000",
            "Gold, 10000",
            "Platinum, 25000"
    })
    void testConstructor_withTier_shouldSetTotalPointsBasedOnTier(String tier, int points) {
        Membership membership = new Membership(tier);
        assertEquals(points, membership.getTotalPoints());
    }

    @Test
    void testConstructor_withInvalidTier_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Membership("notATier");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bronze", "Silver", "Gold", "Platinum" })
    void testConstructor_withTier_shouldSetCorrectTier(String tier) {
        Membership membership = new Membership(tier);
        assertEquals(tier, membership.getTier());
    }

    @Test
    void increaseTotalPointsByAPositiveNumber() {
        Membership membership = new Membership();
        membership.increaseTotalPoints(500);
        assertEquals(500, membership.getTotalPoints());
    }

    @Test
    void increaseTotalPointsWithANegativeNumber_shouldThrowException() {
        Membership membership = new Membership();
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseTotalPoints(-500);
        });
    }

    @Test
    void increaseTotalPointsAboveMAX_VALUE_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseTotalPoints(1);
        });
    }

    @Test
    void decreaseTotalPointsByAPositiveNumber() {
        Membership membership = new Membership(10000);
        membership.decreaseTotalPoints(500);
        assertEquals(9500, membership.getTotalPoints());
    }

    @Test
    void decreaseTotalPointsByANegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseTotalPoints(-500);
        });
    }

    @Test
    void decreaseTotalPointsBeyond0_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseTotalPoints(10001);
        });
    }

    @Test
    void decreaseAvailablePoints_byAValidAmount() {
        Membership membership = new Membership(10000);
        membership.decreaseAvailablePoints(500);
        assertEquals(9500, membership.getAvailablePoints());
    }

    @Test
    void decreaseAvailablePointsByaNegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseAvailablePoints(-500);
        });
    }

    @Test
    void decreaseAvailablePointsBelow0_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseAvailablePoints(10001);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5000, Silver",
            "10000, Gold",
            "25000, Platinum",

    })
    void totalPointsIncreasingToThresholdForNextTier_shouldIncreaseTier(int points, String tier) {
        Membership membership = new Membership();
        membership.increaseTotalPoints(points);
        assertEquals(tier, membership.getTier());
    }

    @ParameterizedTest
    @CsvSource({
            "1, Gold",
            "15001, Silver",
            "20001, Bronze"
    })
    void totalPointsDecreasingToThresholdForLowerTier_shouldDecreaseTier(int points, String tier) {
        Membership membership = new Membership(25000);
        membership.decreaseTotalPoints(points);
        assertEquals(tier, membership.getTier());
    }

    @ParameterizedTest
    @CsvSource({
            "4999, Bronze",
            "9999, Silver",
            "24999, Gold",
            "25001, Platinum"
    })
    void totalPointsIncreasingToBelowThreshold_shouldStayInSameTier(int points, String tier) {
        Membership membership = new Membership();
        membership.increaseTotalPoints(points);
        assertEquals(tier, membership.getTier());
    }

    @Test
    void increaseAvailablePoints() {
        Membership membership = new Membership(10000);
        membership.increaseAvailablePoints(1000);
        assertEquals(11000, membership.getAvailablePoints());
    }

    @Test
    void increaseAvailablePointsByANegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseAvailablePoints(-1);
        });
    }

    @Test
    void increaseAvailablePointsAboveMAX_Value_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseAvailablePoints(1);
        });
    }

    @Test
    void increaseBothTypesOfPointsByAPositiveAmount() {
        Membership membership = new Membership(0);
        membership.increaseBothTypesOfPoints(10000);
        assertEquals(10000, membership.getAvailablePoints());
        assertEquals(membership.getTotalPoints(), membership.getAvailablePoints());
    }

    @Test
    void increaseBothTypesOfPointsByAPositiveAmount_bothPointsStartWithDifferentAmounts() {
        Membership membership = new Membership(10000);
        membership.decreaseAvailablePoints(5000);
        membership.increaseBothTypesOfPoints(10000);
        assertEquals(15000, membership.getAvailablePoints());
        assertEquals(20000, membership.getTotalPoints());
    }

    @Test
    void increaseBothTypesOfPointsByANegativeValue_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseBothTypesOfPoints(-1000);
        });
    }

    @Test
    void increaseBothTypesOfPointsAboveMAX_Value_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
           membership.increaseBothTypesOfPoints(1);
        });
    }

    @Test
    void decreaseBothTypesOfPoints() {
        Membership membership = new Membership(10000);
        membership.decreaseBothTypesOfPoints(5000);
        assertEquals(5000, membership.getTotalPoints());
        assertEquals(5000, membership.getAvailablePoints());
    }

    @Test
    void decreaseBothTypesOfPointsWithANegativeValue_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseBothTypesOfPoints(-1000);
        });
    }

    @Test
    void decreaseBothTypesOfPointsBelow0_shouldThrowException() {
        Membership membership = new Membership(0);
        assertThrows(IllegalArgumentException.class, () -> {
           membership.decreaseBothTypesOfPoints(1);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5000, Silver",
            "10000, Gold",
            "25000, Platinum"
    })
    void increaseBothTypesOfPoints_totalPointsReachesThresholdForNewTier_tierShouldIncrease(int points, String tier) {
        Membership membership = new Membership(0);
        membership.increaseBothTypesOfPoints(points);
        assertEquals(tier, membership.getTier());
    }

    @ParameterizedTest
    @CsvSource({
            "1, Gold",
            "15001, Silver",
            "20001, Bronze",
    })
    void decreaseBothTypesOfPoints_totalPointsGoesBelowThresholdForCurrentTier_tierShouldDecrease(int points, String tier) {
        Membership membership = new Membership(25000);
        membership.decreaseBothTypesOfPoints(points);
        assertEquals(tier, membership.getTier());
    }
}
