package com.example.java1_2022_kub0679;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class HelloController {

    private Game game;
    @FXML
    private Pane mainPane;
    @FXML
    private AnchorPane centerHolder;
    @FXML
    private AnchorPane collectionPane;
    @FXML
    private AnchorPane gameOverPane;
    @FXML
    private AnchorPane mainMenuPane;
    @FXML
    private AnchorPane upgradeShop;
    @FXML
    private AnchorPane statsUpgrade;
    @FXML
    private AnchorPane skillsUpgrade;

    private AnchorPane[] upgradeMenus;

    @FXML
    private Label coinsOwned;
    @FXML
    private ImageView speedUpgradeMeter;
    @FXML
    private ImageView jumpUpgradeMeter;

    @FXML
    private Label speedUpgradeText;

    @FXML
    private Label jumpUpgradeText;

    @FXML
    private ImageView scoreBoard;
    @FXML
    private Text scoreText;
    @FXML
    private Text coinsText;

    @FXML
    private Text currentScoreText;
    @FXML
    private Text highScoreText;

    @FXML
    private ImageView backGround;

    @FXML
    private ImageView pauseScreen;
    @FXML
    private ImageView skillImage;

    private AnimationTimer animationTimer;

    private Sound gameOverMusic = new Sound("gameOverMusic.mp3", MediaPlayer.INDEFINITE);
    private Sound mainMenuMusic = new Sound("mainMenuMusic.mp3", MediaPlayer.INDEFINITE);
    private Sound dayMusic = new Sound("BaseGameMusic.mp3", MediaPlayer.INDEFINITE);
    private Sound upgradeMusic = new Sound("upgradeMusic.mp3", MediaPlayer.INDEFINITE);
    private Sound currentSong;
    private Sound[] songs = new Sound[]{
      gameOverMusic,mainMenuMusic,dayMusic,upgradeMusic,
    };

    private long lastTime;
    public void startGame(){

        upgradeMenus = new AnchorPane[]{
                statsUpgrade,skillsUpgrade,
        };

        playOnlySound(mainMenuMusic);
        currentSong = mainMenuMusic;
        game = new Game(this);
        game.setState(Game.Status.MAINMENU);
        showOnlyScreen(mainMenuPane);

//        pane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("119_UpgradeVisuals_BACKGROUND_1.png").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(lastTime>0){
                    double deltaT = (now-lastTime)/1e9;
                    game.simulate(deltaT);
                }
                game.draw();
                lastTime = now;
            }
        };


        game.setGameListener((valueName, value) -> {
            if(value instanceof Double val){
                switch(valueName){
                    case "score":{

                        scoreText.setText("Score: "+val.intValue());

                        break;
                    }
                    case "currentCoins":{

                        coinsText.setText(String.format("Coins: %d (x%d)", val.intValue(), game.getScore().getCoinsMultiplier()));

                        break;
                    }
                    case "coins":{

                        coinsOwned.setText("" + val.doubleValue());

                        break;
                    }
                }
            } else{
                switch (valueName){
                    case "pause":{
                        if(value instanceof Boolean bool){
                            if(bool) {
//                                animationTimer.stop();
                                pauseScreen.setVisible(true);
                            }
                            else {
//                                animationTimer.start();
                                pauseScreen.setVisible(false);
                            }
                        }
                        break;
                    }
                    case "playerDeath" :{

                        dayMusic.stopSound();
                        gameOverMusic.playSound();

                        Score score = game.getScore();

                        collectionPane.setVisible(false);
                        gameOverPane.setVisible(true);

                        if(score.getBestScore() < score.getCurrentScore()){
                            score.setBestScore(score.getCurrentScore());
                        }

                        currentScoreText.setText("Score:\n"+score.getCurrentScore());
                        highScoreText.setText("High Score:\n"+score.getBestScore());

                        score.setCurrentScore(0);
                        score.setCoins(score.getCoins() + score.getCurrentCoins());
                        score.setCurrentCoins(0);

                        game.endCollection(Game.Status.MAINMENU);
                        animationTimer.stop();
                        break;
                    }
                }
            }
        });

    }//create a drawing thread so we can make player move in it on its own :)


    public Text getCurrentScoreText() {
        return currentScoreText;
    }

    public void setCurrentScoreText(Text currentScoreText) {
        this.currentScoreText = currentScoreText;
    }

    public Text getHighScoreText() {
        return highScoreText;
    }

    public void setHighScoreText(Text highScoreText) {
        this.highScoreText = highScoreText;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public AnchorPane getCenterHolder() {
        return centerHolder;
    }

    public AnchorPane getCollectionPane() {
        return collectionPane;
    }

    public ImageView getSkillImage() {
        return skillImage;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private KeyCode currentkey;

    @FXML
    private void keyPressed(KeyEvent event){
        KeyCode code = event.getCode();
        game.keyHandler(code, 'D');
        if(code != currentkey)
            currentkey = code;
    }

    @FXML
    private void keyUpped(KeyEvent event){
        KeyCode code = event.getCode();
        if(code == currentkey){
            game.keyHandler(code, 'U');
        }
    }

    @FXML
    private void playGameButton(){
        showOnlyScreen(collectionPane);
        playOnlySound(dayMusic);
        animationTimer.start();
        game.beginCollection();
    }

    private int speedIndex = 1;
    private int jumpIndex = 1;

    @FXML
    private void upgradeButton(){
        game.setState(Game.Status.SHOPPING);
        showOnlyScreen(upgradeShop);
        playOnlySound(upgradeMusic);

        speedIndex = (int)(game.getUpgrades().getSpeed() - 1) ;
        jumpIndex = (int)(game.getUpgrades().getJumpPower() -1);
        speedUpgradeMeter.setViewport(new Rectangle2D(0, 16 * speedIndex, speedUpgradeMeter.getViewport().getWidth(), speedUpgradeMeter.getViewport().getHeight()));
        jumpUpgradeMeter.setViewport(new Rectangle2D(0, 16 * jumpIndex, jumpUpgradeMeter.getViewport().getWidth(), jumpUpgradeMeter.getViewport().getHeight()));

        updateStatsUpgradeLabels();
        showOnlyUpgradeScreen(statsUpgrade);
    }

    @FXML
    private void mainMenuButton(){
        playOnlySound(mainMenuMusic);
        showOnlyScreen(mainMenuPane);
        game.setState(Game.Status.MAINMENU);
    }
    private int currentUpgradeMenu = 0;
    @FXML
    private void onMousePressedNext(Event kek){
        switch(((Node)kek.getTarget()).getId()){
            case "nextSpeed" ->{
                if(16 * speedIndex <= 48 ){
                    speedIndex++;
                    speedUpgradeMeter.setViewport(new Rectangle2D(0, 16 * speedIndex, speedUpgradeMeter.getViewport().getWidth(), speedUpgradeMeter.getViewport().getHeight()));

                    updateStatsUpgradeLabels();

                }
            }
            case "prevSpeed" ->{
                if(16 * speedIndex > 0 ){
                    speedIndex--;
                    speedUpgradeMeter.setViewport(new Rectangle2D(0, 16 * speedIndex, speedUpgradeMeter.getViewport().getWidth(), speedUpgradeMeter.getViewport().getHeight()));

                    updateStatsUpgradeLabels();

                }
            }
            case "nextJump" ->{
                if(16 * jumpIndex <= 48 ){
                    jumpIndex++;
                    jumpUpgradeMeter.setViewport(new Rectangle2D(0, 16 * jumpIndex, speedUpgradeMeter.getViewport().getWidth(), speedUpgradeMeter.getViewport().getHeight()));

                    updateStatsUpgradeLabels();

                }
            }
            case "prevJump" ->{
                if(16 * jumpIndex > 0 ){
                    jumpIndex--;
                    jumpUpgradeMeter.setViewport(new Rectangle2D(0, 16 * jumpIndex, speedUpgradeMeter.getViewport().getWidth(), speedUpgradeMeter.getViewport().getHeight()));

                    updateStatsUpgradeLabels();

                }
            }
            case "nextMenu" ->{
                currentUpgradeMenu++;

                if(currentUpgradeMenu > upgradeMenus.length-1){
                    currentUpgradeMenu = 0;
                }
                System.out.println(currentUpgradeMenu);
                showOnlyUpgradeScreen(upgradeMenus[currentUpgradeMenu]);
            }
            case "prevMenu" ->{
                currentUpgradeMenu--;
                if(currentUpgradeMenu < 0){
                    currentUpgradeMenu = upgradeMenus.length-1;
                }
                showOnlyUpgradeScreen(upgradeMenus[currentUpgradeMenu]);
            }
        }
    }

    @FXML
    private void onPurchaseButton(Event kek){
        switch(((Node)kek.getTarget()).getId()){
            case "speed" -> {
                if(speedIndex == 0 || game.getUpgrades().speedOwned(speedIndex+1)){
                    game.getUpgrades().setSpeed(speedIndex+1);
                    updateStatsUpgradeLabels();
                    break;
                }
                if(!game.getUpgrades().speedOwned(speedIndex) ){//stat is too high <=> stat is locked
                    break;
                }
                if(game.getScore().getCoins() >= 100*(speedIndex+1)){//make purchase
                    game.getUpgrades().setSpeed(speedIndex+1);
                    game.getUpgrades().setSpeedOwned(game.getUpgrades().getSpeed());
                    game.getScore().setCoins(game.getScore().getCoins() - 100*(speedIndex+1));
                }
                updateStatsUpgradeLabels();
            }
            case "jump" ->{
                if(jumpIndex == 0 || game.getUpgrades().jumpOwned(jumpIndex+1)){
                    game.getUpgrades().setJumpPower(jumpIndex+1);
                    updateStatsUpgradeLabels();
                    break;
                }
                if(!game.getUpgrades().jumpOwned(jumpIndex)){//stat is too high <=> stat is locked
                    break;
                }
                if(game.getScore().getCoins() >= 100*(jumpIndex+1)){//make purchase
                    game.getUpgrades().setJumpPower(jumpIndex+1);
                    game.getUpgrades().setJumpOwned(game.getUpgrades().getJumpPower());
                    game.getScore().setCoins(game.getScore().getCoins() - 100*(jumpIndex+1));
                }
                updateStatsUpgradeLabels();
            }
            case "meteorDestruction" ->{
                if(game.getScore().getCoins() >= 750){
                    if(!game.getUpgrades().skillOwned("meteorDestruction")){
                        game.getUpgrades().setSkill("meteorDestruction");
                        game.getUpgrades().addSkill("meteorDestruction");
                        game.getScore().setCoins(game.getScore().getCoins() - 750);
                    }

                }
            }
        }
    }


    private void showOnlyScreen(Pane pane){
        pane.setVisible(true);
        for(Node node : centerHolder.getChildren()){
            if(node instanceof Pane pane2){
                if(pane2 != pane){
                    pane2.setVisible(false);
                }
            }
        }
        if(!game.getUpgrades().getSkill().equals("")) skillImage.setVisible(true);
        else skillImage.setVisible(false);

        showOnlyUpgradeScreen(null);
    }

    private void showOnlyUpgradeScreen(Pane pane){
        if(pane != null)pane.setVisible(true);
        for(Node node : upgradeShop.getChildren()){
            if(node instanceof Pane pane2){
                if(pane2 != pane){
                    pane2.setVisible(false);
                }
            }
        }
    }

    private void playOnlySound(Sound sound){
        if(sound != null) {
            sound.playSound();
            currentSong = sound;
        }
        for(Sound song : songs){
            if(song != sound){
                song.stopSound();
            }
        }
    }
    public void switchSong(){
        if(currentSong.isPlaying()) currentSong.stopSound();
        else currentSong.playSound();
    }
    private void updateStatsUpgradeLabels(){

        if(!game.getUpgrades().speedOwned(speedIndex+1)){
            speedUpgradeText.setText("Speed - " + 100*(speedIndex+1));
        }else speedUpgradeText.setText("Speed - OWNED");

        if(!game.getUpgrades().jumpOwned(jumpIndex+1)){
            jumpUpgradeText.setText("JumpPower - " + 100*(jumpIndex+1));
        }else jumpUpgradeText.setText("JumpPower - OWNED");

    }

}