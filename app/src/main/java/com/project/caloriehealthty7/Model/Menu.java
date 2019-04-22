package com.project.caloriehealthty7.Model;

public class Menu {

    private String foodname;
    private String id;
    private int cal;
    private String postkey;
    private String date;
    private String time;

    public Menu(String foodname, String id, int cal, String date, String time) {
        this.foodname = foodname;
        this.id = id;
        this.cal = cal;
        this.date = date;
        this.time = time;
    }

    public Menu() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }
}
