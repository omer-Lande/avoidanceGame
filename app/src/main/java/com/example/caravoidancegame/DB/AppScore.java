package com.example.caravoidancegame.DB;

public class AppScore {
    private int score = 0;
    private String playerName = "";
    private double latitude = 0.0;
    private double longitude = 0.0;

    public AppScore() {
    }

    public int getScore() {
        return score;
    }

    public AppScore setScore(int score) {
        this.score = score;
        return this;
    }

    public String getUser() {
        return playerName;
    }

    public AppScore setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public AppScore setLatitude(double lat) {
        this.latitude = lat;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public AppScore setLongitude(double lon) {
        this.longitude = lon;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("User:").append(playerName);
        sb.append("\nScore:").append(score);
        return sb.toString();

    }
}