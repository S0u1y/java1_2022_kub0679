package com.example.java1_2022_kub0679;

public class Score {
    private double coins = 750;
    private double currentCoins;
    private double bestScore;
    private double currentScore;

    private int coinsMultiplier = 1;

    public GameEvent event = new EmptyGameEvent();

    public Score() {
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
        event.valueChanged("coins", (Double)coins );
    }

    public double getCurrentCoins() {
        return currentCoins;
    }

    public void setCurrentCoins(double currentCoins) {
        this.currentCoins = currentCoins;
        event.valueChanged("currentCoins", (Double)currentCoins );
    }

    public double getBestScore() {
        return bestScore;
    }

    public void setBestScore(double bestScore) {
        this.bestScore = bestScore;
    }

    public double getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(double currentScore) {
        this.currentScore = currentScore;
        event.valueChanged("score", (Double)currentScore );
    }

    public int getCoinsMultiplier() {
        return coinsMultiplier;
    }

    public void setCoinsMultiplier(int coinsMultiplier) {
        if(coinsMultiplier > 4) return;
        this.coinsMultiplier = coinsMultiplier;
        event.valueChanged("currentCoins", (Double)currentCoins );
    }

    public void setEvent(GameEvent event) {
        this.event = event;
    }
}
