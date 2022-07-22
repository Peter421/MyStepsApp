package com.example.mystepsapp;

public class StatsModel {
    private String statistic;
    public StatsModel(String statistic){
        this.statistic=statistic;
    }
    public StatsModel(){}

    public  String getStat() {
        return statistic;
    }
}
