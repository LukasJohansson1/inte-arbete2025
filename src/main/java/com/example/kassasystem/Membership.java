package com.example.kassasystem;

public class Membership {

    private String tier;
    //totalt antal poäng som en Customer har tjänat in. Bestämmer kundens Tier.
    private int totalPoints;
    //antalet poäng som en Customer har just nu. Kan minska genom att de byts ut mot ex. presentkort
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
        if (points < 5000) {
            tier = "Bronze";
        }
        else if (points < 10000) {
            tier = "Silver";
        }
        else if (points < 25000) {
            tier = "Gold";
        }
        else tier = "Platinum";
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
