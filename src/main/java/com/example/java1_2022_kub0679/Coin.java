package com.example.java1_2022_kub0679;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Coin extends FallingObject{

    private final Image COIN_IMAGE = new Image(getClass().getResource("images/88_fall.Coin_coinImg.png").toString());

    private final Sound pickupSound = new Sound("coinPickup.mp3");

    private int count = 16;
    private double speed = 75;

    private double value = 1;

    public Coin(Game game, Pane pane){
        super(game);
        this.image = new ImageView(COIN_IMAGE);
        pane.getChildren().add(1,this.image);
        construct();
    }

    private void construct(){
        image.setViewport(new Rectangle2D(0,0,16,16));
        image.setFitWidth(16);
        image.setFitHeight(16);
        this.lookVector = Point2D.ZERO;
        animation = new SpriteAnimation(count, image, Duration.millis(1000));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setEndAt(9);
        animation.setOnFinished(actionEvent -> destroy());
    }
    @Override
    public void hit(Collidable another) {
        if(hasHit) return;
        if(another instanceof Player){
            pickupSound.playSound();
            setLookVector(new Point2D(0,-25));
            animation.stop();
            animation.setStartAt(9);
            animation.setEndAt(17);
            animation.setCycleCount(1);
            animation.play();
        }
    }



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
