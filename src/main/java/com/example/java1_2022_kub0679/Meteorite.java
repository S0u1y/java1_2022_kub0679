package com.example.java1_2022_kub0679;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.random.RandomGenerator;

public class Meteorite extends FallingObject{
    private int count = 8;

    Meteorite(Game game, Pane pane) {
        super(game);
        this.image = new ImageView(Resources.METEOR_IMAGES[RandomGenerator.getDefault().nextInt(0,2)]);
        pane.getChildren().add(1,this.image);
        construct();
    }

    private void construct(){
        image.setViewport(new Rectangle2D(0,0,16,16));
        image.setFitHeight(16);
        image.setFitWidth(16);
        this.lookVector = Point2D.ZERO;
        animation = new SpriteAnimation(count, image, Duration.millis(400));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setEndAt(4);
        animation.setOnFinished(actionEvent -> destroy());
    }
    @Override
    public void hit(Collidable another) {
        if(hasHit) return;
        if(another instanceof Player){
            Resources.METEORHITSOUND.playSound();
            setLookVector(new Point2D(0,-25));
            animation.stop();
            animation.setStartAt(4);
            animation.setEndAt(count);
            animation.setCycleCount(1);
            animation.play();
        }
    }



}
