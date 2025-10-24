package com.example.kassasystem;

public class Membership {

    private String tier;
    private int totalPoints;
    private int availablePoints;

    public Membership() {
        tier = "Bronze";
    }

    public Membership(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be less than 0");
        } else {
            totalPoints = points;
            availablePoints = points;
            if (points < 5000) {
                tier = "Bronze";
            }
            else if (points < 10000) {
                tier = "Silver";
            }
            else if (points < 25000) {
                tier = "Gold";
            }
            else {
                tier = "Platinum";
            }
        }
    }

    public Membership(String tier) {
        switch(tier) {
            case("Bronze"):
                totalPoints = 0;
                this.tier = "Bronze";
                break;
            case("Silver"):
                totalPoints = 5000;
                this.tier = "Silver";
                break;
            case("Gold"):
                totalPoints = 10000;
                this.tier = "Gold";
                break;
            case ("Platinum"):
                totalPoints = 25000;
                this.tier = "Platinum";
                break;
            default:
                throw new IllegalArgumentException("The membership you have chosen does not exist");
        }
    }

    public String getTier() {
        return tier;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void changeTier(int points) {
        if (points < 5000 && (!tier.equals("Bronze"))) {
            tier = "Bronze";
        }
        else if (points < 10000 && (!tier.equals("Silver"))) {
            tier = "Silver";
        }
        else if (points < 25000 && (!tier.equals("Gold"))) {
            tier = "Gold";
        }
        else if (points >= 25000 && (!tier.equals("Platinum"))) {
            tier = "Platinum";
        }
    }

    public void increaseTotalPoints(int points) {
        if (increasePoints(points)) {
            totalPoints += points;
            changeTier(totalPoints);
        }
    }

    public void decreaseTotalPoints(int points) {
        if (decreasePoints(points)) {
            totalPoints -= points;
            changeTier(totalPoints);
        }
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void decreaseAvailablePoints(int points) {
        if(decreasePoints(points)) {
            availablePoints -= points;
        }
    }

    public void increaseAvailablePoints(int points) {
        if (increasePoints(points)) {
                availablePoints += points;
        }
    }

    public boolean increasePoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("");
        }
        if (((long) totalPoints + points) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("You cannot increase TotalPoints this much");
        } return true;
    }

    public boolean decreasePoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("You cannot decrease AvailablePoints with a negative value");
        }
        if (points > totalPoints) {
            throw new IllegalArgumentException("You cannot decrease Available points below 0");
        } return true;
    }

}
