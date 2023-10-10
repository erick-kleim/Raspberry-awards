package com.raspberry.awards.dto;

public class WinnerIntervalDTO {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

    public String getProducer() {
        return producer;
    }
    public int getInterval() {
        return interval;
    }
    public int getFollowingWin() {
        return followingWin;
    }
    public int getPreviousWin() {return previousWin;}
    public void setInterval(int interval) {
        this.interval = interval;
    }
    public void setPreviousWin(int previousWin) {this.previousWin = previousWin;}
    public WinnerIntervalDTO(String producer, int followingWin) {
        this.producer = producer;
        this.followingWin = followingWin;
    }
}
