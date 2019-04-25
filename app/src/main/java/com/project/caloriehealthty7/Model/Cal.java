package com.project.caloriehealthty7.Model;

public class Cal {

    int number;
    String photo;

    public Cal(int number, String photo) {
        this.number = number;
        this.photo = photo;
    }

    public Cal() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
