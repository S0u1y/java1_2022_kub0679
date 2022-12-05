package com.example.java1_2022_kub0679;

import javafx.scene.input.KeyCode;

public class Game {

    private HelloController controller;
    private MainGameHandler collectingGame;
    private Score score = new Score();

    private Upgrades upgrades = new Upgrades();

    private Status state = Status.COLLECTING;

    public enum Status{
        COLLECTING,
        SHOPPING,
        MAINMENU,

    }

    private GameEvent gameListener = new EmptyGameEvent();


    public void addCoins(double coins){
        score.setCurrentCoins(score.getCurrentCoins() + coins);
    }

    public void addScore(double score){this.score.setCurrentScore(this.score.getCurrentScore() + score);}

    public Score getScore(){return score;}

    public Upgrades getUpgrades() {
        return upgrades;
    }

    public Game(HelloController controller){
        this.controller = controller;


        construct();
    }

    private void construct(){

        score.setEvent((valueName, value) -> gameListener.valueChanged(valueName, value));

    }

    public void beginCollection(){
        collectingGame = new MainGameHandler(this);
        System.gc();
        state = Status.COLLECTING;
    }
    public void endCollection(Status status){
        state = status;
        collectingGame.destroyAllEntities();
    }
    public void simulate(double deltaT){

        switch (state){
            case COLLECTING -> collectingGame.simulate(deltaT);

        }

    }

    public void draw(){
        switch(state){
            case COLLECTING -> collectingGame.draw();
        }
    }

    public void keyHandler(KeyCode code, char keyEvent){

        switch(state){
            case COLLECTING -> collectingGame.keyHandler(code,keyEvent);
        }

    }
    public Status getState() {
        return state;
    }

    public void setGameListener(GameEvent gameListener) {
        this.gameListener = gameListener;
    }

    public GameEvent getGameListener() {
        return gameListener;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public HelloController getController() {
        return controller;
    }

    public MainGameHandler getCollectingGame() {
        return collectingGame;
    }


}
