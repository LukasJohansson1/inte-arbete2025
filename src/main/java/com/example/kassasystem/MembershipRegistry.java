package com.example.kassasystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Memberships läggs inte till automatiskt i members.

public class MembershipRegistry {

    private static Map<String, List<Customer>> members = new HashMap<>();

    public void addMember(Customer customer) {
        members.computeIfAbsent(customer.getMembershipTier(), k -> new ArrayList<>()).add(customer);
    }

    public List<Customer> getMembersInTier(String tier) {
        return members.get(tier);
    }

    public boolean hasTier(String tier) {
        return members.containsKey(tier);
    }
}
