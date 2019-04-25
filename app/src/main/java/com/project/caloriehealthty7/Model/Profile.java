package com.project.caloriehealthty7.Model;

public class Profile {

    String high;
    String width;
    String age;
    String sex;
    String dh;

    public Profile(String high, String width, String age, String sex, String dh) {
        this.high = high;
        this.width = width;
        this.age = age;
        this.sex = sex;
        this.dh = dh;
    }

    public Profile() {

    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }
}
