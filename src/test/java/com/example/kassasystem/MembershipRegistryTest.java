package com.example.kassasystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MembershipRegistryTest {

    private MembershipRegistry membershipRegistry;

    @BeforeEach
    void SetUp() {
        membershipRegistry = new MembershipRegistry();
    }

    @Test
    void addMember_shouldStoreInCorrectTier() {
        Customer customer = new Customer(
        "John", "Eriksson", "Kyrkgränd 14",
"20020305-5523", "0739654522", "JohnEriksson@hotmail.com"
        );

        membershipRegistry.addMember(customer);
        assertTrue(membershipRegistry.hasTier(customer.getMembershipTier()));
        List<Customer> bronzeMembers = membershipRegistry.getMembersInTier(customer.getMembershipTier());
        assertTrue(bronzeMembers.contains(customer));
        }

    @ParameterizedTest
    @CsvSource({
            "5000, Silver",
            "10000, Gold",
            "25000, Platinum"
    })
    void addMemberWithPoints_shouldStoreInCorrectTier(int points, String tier) {
        Customer customer = new Customer(
            "John", "Eriksson", "Kyrkgränd 14",
        "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", points
        );

        membershipRegistry.addMember(customer);
        assertTrue(membershipRegistry.hasTier(customer.getMembershipTier()));
        List<Customer> members = membershipRegistry.getMembersInTier(customer.getMembershipTier());
        assertTrue(members.contains(customer));
        assertEquals(tier, customer.getMembershipTier());

    }

    @ParameterizedTest
    @ValueSource(strings = {"Bronze", "Silver", "Gold", "Platinum"})
    void addMemberWithTier_shouldStoreInCorrectTier(String tier) {
        Customer customer = new Customer(
                "John", "Eriksson", "Kyrkgränd 14",
                "20020305-5523", "0739654522", "JohnEriksson@hotmail.com", tier
        );

        membershipRegistry.addMember(customer);
        assertTrue(membershipRegistry.hasTier(customer.getMembershipTier()));
        List<Customer> members = membershipRegistry.getMembersInTier(customer.getMembershipTier());
        assertTrue(members.contains(customer));
        assertEquals(tier, customer.getMembershipTier());
    }
}
