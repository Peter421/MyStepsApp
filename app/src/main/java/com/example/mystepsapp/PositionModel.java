package com.example.mystepsapp;

public class PositionModel {

    private String position, username, days, dailysteps, totalsteps;




    public PositionModel(String position, String username, String days, String dailysteps, String totalsteps){

        this.position=position;
        this.username=username;
        this.days=days;
        this.dailysteps=dailysteps;
        this.totalsteps=totalsteps;
    }

    public PositionModel(){}

    public String getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }

    public String getDays() {
        return days;
    }

    public String getDailysteps() {
        return dailysteps;
    }

    public String getTotalsteps() {
        return totalsteps;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
