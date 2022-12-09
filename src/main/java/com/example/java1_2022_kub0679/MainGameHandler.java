package com.example.java1_2022_kub0679;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainGameHandler{
    private Game game;
    private Player player;
    private ArrayList<GameEntity> entities;

    private Random random = new Random();

    private boolean paused = false;

    public MainGameHandler(Game game) {
        this.game = game;
        player = new Player(game, game.getController().getCollectionPane());
        player.setSpeed(player.getSpeed() * ((game.getUpgrades().getSpeed()+3)/4));
        player.setJumpPower(player.getJumpPower() * ((game.getUpgrades().getJumpPower()+3)/4));

        entities = new ArrayList<>();
        entities.add(player);

    }


    public void simulate(double deltaT){
        if(paused) return;
        //thread safe ArrayList for concurrent change errors..
        CopyOnWriteArrayList<GameEntity> copy1 = new CopyOnWriteArrayList<>(entities);
        for(GameEntity simul : copy1){
            if(paused) return;
            simul.simulate(deltaT);
            CopyOnWriteArrayList<GameEntity> copy2 = new CopyOnWriteArrayList<>(entities);
            for(GameEntity simul2 : copy2){
                if(paused) return;
                if(simul != simul2){
                    if(simul.overlaps(simul2.getBoundingBox())){
                        simul.hit(simul2);
                        simul2.hit(simul);
                    }
                }
            }
        }
    }


    private Coin spawnCoin(){
        Coin newCoin = new Coin(game, game.getController().getCollectionPane());
        newCoin.startAnimation();
        newCoin.spawn(random.nextDouble(game.getController().getCollectionPane().getWidth()-17),-17);
        entities.add(newCoin);
        return newCoin;
    }
    private void spawnCoin(double gravity){
        Coin newCoin = spawnCoin();
        newCoin.setGravity(gravity);
        entities.add(newCoin);
    }
    private Meteorite spawnMeteor(){
        Meteorite newMeteor = new Meteorite(game, game.getController().getCollectionPane());
        newMeteor.startAnimation();
        newMeteor.spawn(random.nextDouble(game.getController().getCollectionPane().getWidth()-17),-17);
        entities.add(newMeteor);
        return newMeteor;
    }
    private void spawnMeteor(double gravity){
        Meteorite newMeteor = spawnMeteor();
        newMeteor.setGravity(gravity);
        entities.add(newMeteor);
    }

    private double maxCoinInterval = 2000;
    private double coinInterval = System.currentTimeMillis() + maxCoinInterval;

    private double maxMeteoriteInterval = 1000;
    private double meteoriteInterval = System.currentTimeMillis() + maxMeteoriteInterval;

    public void draw(){
        if(paused) return;

        if(System.currentTimeMillis() >= coinInterval){
            Coin coin = spawnCoin();
            coin.setValue(coin.getValue() * game.getScore().getCoinsMultiplier());
            coinInterval = System.currentTimeMillis() + maxCoinInterval + random.nextDouble(-(500+1),500);
        }
        if(System.currentTimeMillis() >= meteoriteInterval){
            Meteorite meteor = spawnMeteor();
            meteor.setGravity(meteor.gravity * random.nextDouble(1,3 + game.getScore().getCoinsMultiplier()));
            meteoriteInterval = System.currentTimeMillis() + maxMeteoriteInterval + random.nextDouble(-(250+1),250);
        }

        if(System.currentTimeMillis() >= player.getSkillCooldown()){
            URL resource = getClass().getResource("images/"+game.getUpgrades().getSkill()+".png");
            if(resource != null)
                game.getController().getSkillImage().setImage(new Image(resource.toString()));
        }
        game.getScore().setCoinsMultiplier((int)((game.getScore().getCurrentScore()/500)+1));
        game.addScore(1);
    }


    public void keyHandler(KeyCode code, char keyEvent){

        switch(keyEvent){
            case 'D':{
                switch (code){
                    case D:{
                        if(paused) return;
                        player.goRight();
                        break;
                    }
                    case A:{
                        if(paused) return;
                        player.goLeft();
                        break;
                    }
                    case W:{
                        if(paused) return;
                        player.jump();
                        break;
                    }
                    case P:{
                        paused = !paused;
                        game.getGameListener().valueChanged("pause", (Boolean)paused);
                        break;
                    }
                    case SPACE:{
                        //use special ability
                        if(paused) return;
                        if(System.currentTimeMillis() < player.getSkillCooldown())return;

                        switch(game.getUpgrades().getSkill()){
                            case "meteorDestruction" ->{
                                //thread safe ArrayList for concurrent change errors..
                                CopyOnWriteArrayList<GameEntity> copy1 = new CopyOnWriteArrayList<>(entities);
                                for(GameEntity simul : copy1){
                                    if(simul instanceof Meteorite){
                                        destroyEntity(simul);
                                    }
                                }
                                game.getController().getSkillImage().setImage(new Image(getClass().getResource("images/skillDownImg.png").toString()));
                                player.setSkillCooldown(System.currentTimeMillis() + 80000); //cd is 80 seconds.
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 'U':{
                switch(code){
                    case P ->{
                        return;
                    }
                    case C -> game.getController().switchSong();
                }
                if(paused) return;
                if(player.getAnimationStatus() != Animation.Status.PAUSED){
                    player.setLookVector(Point2D.ZERO);
                    player.setAnimationFrame(0);
                    player.pauseAnimation();
                }
            }
        }
    }

    public void destroyEntity(GameEntity entity){
//        System.out.println(controller.getCollectionPane().getChildren());
        game.getController().getCollectionPane().getChildren().remove(entity.getImage());
        entities.remove(entity);
//        System.out.println(controller.getCollectionPane().getChildren());
    }

    public void destroyAllEntities(){
        paused = true;
        CopyOnWriteArrayList<GameEntity> copy1 = new CopyOnWriteArrayList<>(entities);
        for(GameEntity entity : copy1){
            game.getController().getCollectionPane().getChildren().remove(entity.getImage());
            entities.remove(entity);
        }
    }

}
