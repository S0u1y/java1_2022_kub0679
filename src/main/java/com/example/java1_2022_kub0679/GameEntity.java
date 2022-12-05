package com.example.java1_2022_kub0679;

import javafx.animation.Animation;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public abstract class GameEntity implements Collidable, Simulable{

    protected Game game;
    protected ImageView image;
    protected SpriteAnimation animation;
    protected Point2D lookVector;
    protected double gravity = 0.30;



    public enum Status {
        STANDING,
        JUMPING,
        FALLING;
        Status() {
        }
    }

    GameEntity(Game game){
        ((GameEntityReadOnlyProperty)this.statusProperty()).set(Status.STANDING);
        this.game = game;
        this.lookVector = Point2D.ZERO;
    }

    private ReadOnlyObjectProperty<Status> status;

    protected Status getStatus(){
        return status.get();
    }

    protected boolean isJumping(){
        return this.getStatus() == Status.JUMPING;
    }

    protected boolean isFalling(){
        return this.getStatus() == Status.FALLING;
    }

    protected boolean isStanding(){
        return this.getStatus() == Status.STANDING;
    }

    public final ReadOnlyObjectProperty<Status> statusProperty() {
        if (this.status == null) {
            this.status = new GameEntityReadOnlyProperty("status", Status.STANDING);
        }

        return this.status;
    }

    public void setStatus(Status var1){
        if (this.status != null) {
            ((GameEntityReadOnlyProperty)this.statusProperty()).set(var1);
        }
    }

//    public void onChanged(){
//        ((GameEntity.GameEntityReadOnlyProperty)this.statusProperty()).addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object o, Object t1) {
//                System.out.println(observableValue.getValue()+" "+o+" "+t1);
//            }
//        });
//    }

    private class GameEntityReadOnlyProperty<T> extends ReadOnlyObjectPropertyBase<T> {
        private final String name;
        private T value;

        private GameEntityReadOnlyProperty(String var2, T var3) {
            this.name = var2;
            this.value = var3;
        }

        public Object getBean() {
            return GameEntity.this;
        }

        public String getName() {
            return this.name;
        }

        public T get() {
            return this.value;
        }

        private void set(T var1) {
            this.value = var1;
            this.fireValueChangedEvent();
        }
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public ImageView getImage() {
        return image;
    }

    public Point2D getLookVector() {
        return lookVector;
    }

    public void setLookVector(Point2D lookVector) {
        this.lookVector = lookVector;
    }

    public Rectangle2D getBoundingBox(){
        if(this.image == null) return Rectangle2D.EMPTY;
        return new Rectangle2D(image.getX() + image.getLayoutX(), image.getY() + image.getLayoutY(), image.getFitWidth(), image.getFitHeight());
    }

    public boolean overlaps(Rectangle2D otherBox) {
        return getBoundingBox().intersects(otherBox);
    }

    public Animation.Status getAnimationStatus(){
        return animation.getStatus();
    }

    public Animation getAnimation() {
        return animation;
    }

    public void startAnimation(){
        if(!isFalling() || !isJumping() ){
            animation.play();
        }
    }
    public void pauseAnimation(){
        animation.pause();
    }

    protected void setViewPortMin(double x, double y){
        if(image.getViewport() != null){
            image.setViewport(new Rectangle2D(x,y,image.getViewport().getWidth(),image.getViewport().getHeight()));
        }else{
            image.setViewport(new Rectangle2D(x,y,0,0));
        }
    }
    protected void setViewPortMax(double x, double y){
        if(image.getViewport() != null){
            image.setViewport(new Rectangle2D(image.getViewport().getMinX(),image.getViewport().getMinY(),x,y));
        }else{
            image.setViewport(new Rectangle2D(0,0,x,y));
        }
    }
    public void setAnimationFrame(double frame){
        setViewPortMin(frame,0);
    }

    public void spawn(double x, double y){
        this.image.setX(x);
        this.image.setY(y);
        setStatus(Status.FALLING);
    }


}
