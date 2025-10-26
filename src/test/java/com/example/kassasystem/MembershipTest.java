package com.example.kassasystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MembershipTest {

    /*@BeforeEach
    void setUp() {
    Membership membership = new Membership();
    } */

    @Test
    public void testEmptyConstructor_shouldSetTierToBronze() {
        Membership membership = new Membership();
        assertEquals("Bronze", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -9867, Integer.MIN_VALUE})
    public void testConstructor_withNegativePoints_shouldThrowException(int points) {
        assertThrows(IllegalArgumentException.class, () -> {
            Membership membership = new Membership(points);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1243, 4999})
    public void testConstructor_withPointsBetween0And4999_shouldGiveBronzeTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Bronze", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {5000, 6789, 9999})
    public void testConstructor_withPointsBetween5000And9999_shouldGiveSilverTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Silver", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {10000, 23456, 24999})
    public void testConstructor_withPointsBetween10000And9999_shouldGiveGoldTier(int points) {
        Membership membership = new Membership(points);
        assertEquals("Gold", membership.getTier());
    }

    @ParameterizedTest
    @ValueSource(ints = {25000, 234567, Integer.MAX_VALUE})
    public void testConstructor_withPointsAbove250000_shouldGivePlatinumTier(int points) {
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
    public void testConstructor_withTier_shouldSetTotalPointsBasedOnTier(String tier, int points) {
        Membership membership = new Membership(tier);
        assertEquals(points, membership.getTotalPoints());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bronze", "Silver", "Gold", "Platinum" })
    public void testConstructor_withTier_shouldSetCorrectTier(String tier) {
        Membership membership = new Membership(tier);
        assertEquals(tier, membership.getTier());
    }

    @Test
    public void increaseTotalPointsByAPositiveNumber() {
        Membership membership = new Membership();
        membership.increaseTotalPoints(500);
        assertEquals(500, membership.getTotalPoints());
    }

    @Test
    public void increaseTotalPointsWithANegativeNumber_shouldThrowException() {
        Membership membership = new Membership();
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseTotalPoints(-500);
        });
    }

    @Test
    public void increaseTotalPointsAboveMAX_VALUE_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseTotalPoints(1);
        });
    }

    @Test
    public void decreaseTotalPointsByAPositiveNumber() {
        Membership membership = new Membership(10000);
        membership.decreaseTotalPoints(500);
        assertEquals(9500, membership.getTotalPoints());
    }

    @Test
    public void decreaseTotalPointsByANegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseTotalPoints(-500);
        });
    }

    @Test
    public void decreaseTotalPointsBeyond0_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseTotalPoints(10001);
        });
    }

    @Test
    public void decreaseAvailablePoints_byAValidAmount() {
        Membership membership = new Membership(10000);
        membership.decreaseAvailablePoints(500);
        assertEquals(9500, membership.getAvailablePoints());
    }

    @Test
    public void decreaseAvailablePointsByaNegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseAvailablePoints(-500);
        });
    }

    @Test
    public void decreaseAvailablePointsBelow0_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseAvailablePoints(10001);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5000, Silver",
            "10000, Gold",
            "25000, Platinum"
    })
    public void totalPointsIncreasingToThresholdForNextTier_shouldIncreaseTier(int points, String tier) {
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
    public void totalPointsDecreasingToThresholdForLowerTier_shouldDecreaseTier(int points, String tier) {
        Membership membership = new Membership(25000);
        membership.decreaseTotalPoints(points);
        assertEquals(tier, membership.getTier());
    }

    @Test
    public void increaseAvailablePoints() {
        Membership membership = new Membership(10000);
        membership.increaseAvailablePoints(1000);
        assertEquals(11000, membership.getAvailablePoints());
    }

    @Test
    public void increaseAvailablePointsByANegativeNumber_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseAvailablePoints(-1);
        });
    }

    @Test
    public void increaseAvailablePointsAboveMAX_Value_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseAvailablePoints(1);
        });
    }

    @Test
    public void increaseBothTypesOfPointsByAPositiveAmount() {
        Membership membership = new Membership(0);
        membership.increaseBothTypesOfPoints(10000);
        assertEquals(10000, membership.getAvailablePoints());
        assertEquals(membership.getTotalPoints(), membership.getAvailablePoints());
    }

    @Test
    public void increaseBothTypesOfPointsByAPositiveAmount_bothPointsStartWithDifferentAmounts() {
        Membership membership = new Membership(10000);
        membership.decreaseAvailablePoints(5000);
        membership.increaseBothTypesOfPoints(10000);
        assertEquals(15000, membership.getAvailablePoints());
        assertEquals(20000, membership.getTotalPoints());
    }

    @Test
    public void increaseBothTypesOfPointsByANegativeValue_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.increaseBothTypesOfPoints(-1000);
        });
    }

    @Test
    public void increaseBothTypesOfPointsAboveMAX_Value_shouldThrowException() {
        Membership membership = new Membership(Integer.MAX_VALUE);
        assertThrows(IllegalArgumentException.class, () -> {
           membership.increaseBothTypesOfPoints(1);
        });
    }

    @Test
    public void decreaseBothTypesOfPoints() {
        Membership membership = new Membership(10000);
        membership.decreaseBothTypesOfPoints(5000);
        assertEquals(5000, membership.getTotalPoints());
        assertEquals(5000, membership.getAvailablePoints());
    }

    @Test
    public void decreaseBothTypesOfPointsWithANegativeValue_shouldThrowException() {
        Membership membership = new Membership(10000);
        assertThrows(IllegalArgumentException.class, () -> {
            membership.decreaseBothTypesOfPoints(-1000);
        });
    }

    @Test
    public void decreaseBothTypesOfPointsBelow0_shouldThrowException() {
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
    public void increaseBothTypesOfPoints_totalPointsReachesThresholdForNewTier_tierShouldIncrease(int points, String tier) {
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
    public void decreaseBothTypesOfPoints_totalPointsGoesBelowThresholdForCurrentTier_tierShouldDecrease(int points, String tier) {
        Membership membership = new Membership(25000);
        membership.decreaseBothTypesOfPoints(points);
        assertEquals(tier, membership.getTier());
    }

}
