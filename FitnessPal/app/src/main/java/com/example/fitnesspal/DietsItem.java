package com.example.fitnesspal;

public class DietsItem {
    String dietID;
    String dietName;
    String breakfast;
    String lunch;
    String dinner;
    String water;
    String dietGoals;

    public DietsItem() {
    }

    public DietsItem(String dietID, String dietName, String breakfast, String lunch, String dinner, String water, String dietGoals) {
        this.dietID = dietID;
        this.dietName = dietName;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.water = water;
        this.dietGoals = dietGoals;
    }

    public String getDietID() {
        return dietID;
    }

    public void setDietID(String dietID) {
        this.dietID = dietID;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getDietGoals() {
        return dietGoals;
    }

    public void setDietGoals(String dietGoals) {
        this.dietGoals = dietGoals;
    }
}
