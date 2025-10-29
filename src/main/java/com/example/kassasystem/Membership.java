package com.example.kassasystem;

public class Membership {

    private static final String BRONZE = "Bronze";
    private static final String SILVER = "Silver";
    private static final String GOLD = "Gold";
    private static final String PLATINUM = "Platinum";

    private String tier;
    //totalt antal poäng som en Customer har tjänat in. Bestämmer kundens Tier.
    private int totalPoints;
    //antalet poäng som en Customer har just nu. Kan minska genom att de byts ut mot ex. presentkort
    private int availablePoints;

    public Membership() {
        tier = BRONZE;
    }

    public Membership(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be less than 0");
        } else {
            totalPoints = points;
            availablePoints = points;
            if (points < 5000) {
                tier = BRONZE;
            }
            else if (points < 10000) {
                tier = SILVER;
            }
            else if (points < 25000) {
                tier = GOLD;
            }
            else {
                tier = PLATINUM;
            }
        }
    }

    public Membership(String tier) {
        switch(tier) {
            case(BRONZE):
                totalPoints = 0;
                this.tier = BRONZE;
                break;
            case(SILVER):
                totalPoints = 5000;
                this.tier = SILVER;
                break;
            case(GOLD):
                totalPoints = 10000;
                this.tier = GOLD;
                break;
            case (PLATINUM):
                totalPoints = 25000;
                this.tier = PLATINUM;
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
        if (points < 5000) {
            tier = BRONZE;
        }
        else if (points < 10000) {
            tier = SILVER;
        }
        else if (points < 25000) {
            tier = GOLD;
        }
        else tier = PLATINUM;
    }

    //fixa kodupprepning

    public void increaseTotalPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("");
        }
        if (((long) totalPoints + points) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("You cannot increase TotalPoints this much");
        }
            totalPoints += points;
            changeTier(totalPoints);
    }

    public void decreaseTotalPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("You cannot decrease TotalPoints with a negative value");
        }
        if (points > totalPoints) {
            throw new IllegalArgumentException("You cannot decrease TotalPoints points below 0");
        }
            totalPoints -= points;
            changeTier(totalPoints);
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void decreaseAvailablePoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("You cannot decrease AvailablePoints with a negative value");
        }
        if (points > availablePoints) {
            throw new IllegalArgumentException("You cannot decrease Available points below 0");
        }
            availablePoints -= points;
    }

    public void increaseAvailablePoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("");
        }
        if (((long) availablePoints + points) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("You cannot increase Available points this much");
        }
            availablePoints += points;
    }

    public void increaseBothTypesOfPoints(int points) {
        increaseTotalPoints(points);
        increaseAvailablePoints(points);
    }

    public void decreaseBothTypesOfPoints(int points) {
        decreaseTotalPoints(points);
        decreaseAvailablePoints(points);
    }

}
