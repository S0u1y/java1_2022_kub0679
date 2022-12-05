package com.example.java1_2022_kub0679;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Player extends GameEntity{

    private final Image PLAYER_IMAGE = new Image(getClass().getResource("images/playerImg.png").toString());
    private Sound jumpSound = new Sound("playerJump.mp3");
    private Sound useSkillSound = new Sound("skillUseSound.mp3");

    private int count = 4;
    private double speed = 75;
    private double jumpPower = -25;
    private int health = 1;
    private long skillCooldown = 0;
    private GameEvent deathEvent = new EmptyGameEvent();
    public Player(Game game, Pane pane) {
        super(game);
        this.image = new ImageView(PLAYER_IMAGE);
        pane.getChildren().add(1,image);

        construct();
    }

    private void construct(){
        image.setVisible(true);
        setViewPortMax(24,28);
        image.setFitWidth(37);
        image.setFitHeight(41);
        image.setLayoutX(50);
        image.setLayoutY(186);
        image.setRotationAxis(new Point3D(0,1,0));
        this.lookVector = Point2D.ZERO;
        animation = new SpriteAnimation(count, image, Duration.millis(200));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    public void jump(){
        if(!isFalling()){
            jumpSound.playSound();
            lookVector = lookVector.add(new Point2D(0, jumpPower));
            setStatus(Status.JUMPING);
            setStatus(Status.FALLING);
            pauseAnimation();
        }
    }

    public void goLeft(){
        image.setRotate(180);
        setLookVector(new Point2D(-speed, lookVector.getY()));
        startAnimation();
    }

    public void goRight(){
        image.setRotate(0);
        setLookVector(new Point2D(speed, lookVector.getY()));
        startAnimation();
    }

    private int jumpedFrames = 0;
    public void simulate(double deltaT) {
        double viewportWidth = image.getViewport().getWidth();
        if (isFalling() || isJumping()) {
            lookVector = lookVector.add(0, gravity);
            if (image.getY() >= 0 && jumpedFrames > 1) {
                image.setY(0);
                lookVector = new Point2D(0, 0);
                setStatus(Status.STANDING);
                jumpedFrames = 0;
                setViewPortMin(0,0);
                pauseAnimation();
            }
            jumpedFrames++;
            if (lookVector.getY() < 0) {
                setViewPortMin(viewportWidth * 4, 0);
            } else {
                setViewPortMin(viewportWidth * 5, 0);
            }
        }
        double currentX = image.getLayoutX() + image.getX();
        if(currentX < 0){
            lookVector = lookVector.add( 150, 0);
        } else if (currentX > image.getScene().getWidth() - image.getFitWidth()) {
            lookVector = lookVector.add( -150, 0);
        }




        this.image.setX(image.getX() + lookVector.getX() * deltaT);
        this.image.setY(image.getY() + lookVector.getY() * deltaT);
    }

    public long getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(long skillCooldown) {
        useSkillSound.playSound();
        this.skillCooldown = skillCooldown;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(double jumpPower) {
        this.jumpPower = jumpPower;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setLookVector(Point2D lookVector) {
        if(!isJumping()){
            this.lookVector = lookVector;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void hit(Collidable another) {
        if(another instanceof FallingObject fallingObject){
            if(fallingObject.hasHit()) return;
            if(another instanceof Coin coin){
                game.addCoins(coin.getValue());
            } else if (another instanceof Meteorite) {
                health--;
                if(health <= 0){
                    game.getGameListener().valueChanged("playerDeath", null);
                    deathEvent.valueChanged("playerDeath", null);
                }
            }
            fallingObject.hit(this);
            fallingObject.setHasHit(true);
        }

    }

}
