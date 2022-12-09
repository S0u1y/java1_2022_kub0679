package com.example.java1_2022_kub0679;

public class Score {
    private int coins = 750;
    private int currentCoins;
    private int bestScore;
    private int currentScore;

    private int coinsMultiplier = 1;

    public GameEvent event = new EmptyGameEvent();

    public Score() {
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
        event.valueChanged("coins", (Integer)coins );
    }

    public int getCurrentCoins() {
        return currentCoins;
    }

    public void setCurrentCoins(int currentCoins) {
        this.currentCoins = currentCoins;
        event.valueChanged("currentCoins", (Integer)currentCoins );
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        event.valueChanged("score", (Integer)currentScore );
    }

    public int getCoinsMultiplier() {
        return coinsMultiplier;
    }

    public void setCoinsMultiplier(int coinsMultiplier) {
        if(coinsMultiplier > 4) return;
        this.coinsMultiplier = coinsMultiplier;
        event.valueChanged("currentCoins", (Integer)currentCoins );
    }

    public void setEvent(GameEvent event) {
        this.event = event;
    }
}
