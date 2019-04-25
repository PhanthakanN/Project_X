package com.project.caloriehealthty7.Model;

public class Water {

    String time;
    String water;

    public Water(String time, String water) {
        this.time = time;
        this.water = water;
    }

    public Water() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }
}
