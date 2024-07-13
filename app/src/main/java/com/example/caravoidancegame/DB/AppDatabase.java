package com.example.caravoidancegame.DB;

import java.util.ArrayList;

public class AppDatabase {
    private ArrayList<AppScore> results;
    private final int LIMIT_TOP10 = 10;

    public AppDatabase() {
        this.results = new ArrayList<>();
    }

    public ArrayList<AppScore> getScores() {
        results.sort((r1, r2) -> r2.getScore() - r1.getScore());
        if (results.size() == LIMIT_TOP10) {
            results.remove(LIMIT_TOP10 - 1);
        }

        return results;
    }

    public AppDatabase setScores(ArrayList<AppScore> results) {
        this.results = results;
        return this;
    }
}
