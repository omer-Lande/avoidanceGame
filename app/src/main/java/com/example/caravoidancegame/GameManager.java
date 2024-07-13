package com.example.caravoidancegame;


import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.caravoidancegame.DB.AppDatabase;
import com.example.caravoidancegame.DB.AppScore;
import com.example.caravoidancegame.Util.MySP;
import com.google.gson.Gson;

public class GameManager {
    private int score = 0;
    private int lives;
    private int initialLives;
    private int currentRow = 5;
    private int currentCarLane = 2;
    private String playerName;
    private ImageView[][] cells;
    private final String SCORE = "scores";


    public GameManager(int initialLives,String playerName) {
        this.playerName = playerName;
        this.score = 0;
        if (initialLives > 0 && initialLives <= 3) {
            this.lives = initialLives;
            this.initialLives = initialLives;
        }
        else {
            this.lives = 3;
            this.initialLives = 3;
        }
    }

    public void decreaseLive() {
        if (lives > 0) {
            lives--;
        }
    }

    public int increaseScore(){
        score += 10;
        return score;
    }

    public int getinitialLives() {
        return this.initialLives;
    }

    public int getLives() {
        return lives;
    }

    public int moveCarSensors(int i){
        if(i==1&& getCurrentCarLane() < 4)
            this.currentCarLane++;
        else if(i==-1&& getCurrentCarLane() > 0)
            this.currentCarLane--;
        return getCurrentCarLane();
    }

    public int getCurrentCarLane() {
        return currentCarLane;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore(){
        return score;
    }


    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setCurrentCarLane(int currentCarLane) {
        this.currentCarLane = currentCarLane;
    }

    public int getInitialLives() {
        return initialLives;
    }

    public void setInitialLives(int initialLives) {
        this.initialLives = initialLives;
    }


    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentRow() {
        return currentRow;
    }


    public ImageView[][] getCells() {
        return cells;
    }

    public void setCells(ImageView[][] cells) {
        this.cells = cells;
    }

    public void save(double longitude, double latitude) {
        AppDatabase appDB;
        String json = MySP.getInstance().getStrSP(SCORE,"");
        appDB = new Gson().fromJson(json, AppDatabase.class);
        if(appDB==null) {
            appDB = new AppDatabase();
        }
        AppScore scr = createScore(longitude,latitude);
        appDB.getScores().add(scr);
        MySP.getInstance().putString(SCORE,new Gson().toJson(appDB));
    }

    private AppScore createScore(double longitude, double latitude){
        Log.d(TAG,"player name before save: " + playerName);
        return new AppScore().setPlayerName(playerName).setScore(score).setLatitude(latitude).setLongitude(longitude);
    }

    public void moveCarLeft() {
        if (currentCarLane > 0) {
            cells[currentRow][currentCarLane].setVisibility(View.INVISIBLE);
            this.currentCarLane--;
            cells[currentRow][currentCarLane].setVisibility(View.VISIBLE);
        }
    }

    public void moveCarRight() {
        if (currentCarLane < 4) {
            cells[currentRow][currentCarLane].setVisibility(View.INVISIBLE);
            this.currentCarLane++;// Move right
            Log.d(TAG,"current lane of car " + currentCarLane);
            cells[currentRow][currentCarLane].setVisibility(View.VISIBLE);
        }
    }
}