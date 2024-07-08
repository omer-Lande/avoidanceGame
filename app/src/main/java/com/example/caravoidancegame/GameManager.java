package com.example.caravoidancegame;


public class GameManager {
    private int score = 0;
    private int lives;
    private int initialLives;

    private int currentCarLane = 2; // Start at lane 3, which is index 2 in a zero-indexed array



    public GameManager(int initialLives) {

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

    public void increaseLane(int currentCarLane){
        currentCarLane++;
        this.currentCarLane = currentCarLane;
    }

    public void decreaseLane(int currentCarLane){
        currentCarLane--;
        this.currentCarLane = currentCarLane;
    }
    public int getinitialLives() {
        return this.initialLives;
    }

    public int getLives() {
        return lives;
    }


    public int getCurrentCarLane() {
        return currentCarLane;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setCurrentCarLane(int currentCarLane) {
        this.currentCarLane = currentCarLane;
    }


}