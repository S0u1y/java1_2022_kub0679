package com.example.java1_2022_kub0679;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class SpriteAnimation extends Transition {

    private int count;
    private int startAt = 1;
    private int endAt;
    private ImageView image;

    public SpriteAnimation(int Count, ImageView Image, Duration duration){
        count = Count;
        endAt = count;
        image = Image;

        setCycleDuration(duration);

    }

    @Override
    protected void interpolate(double v) {
//        System.out.println(v);
//        final int index = Math.min((int)Math.floor(count*v), count);
//        final int x = (int)((index%count)*image.getViewport().getWidth());
        final int index = (int)(((endAt - 1 - (startAt - 1))*v+(startAt-1))); // 7v + 8
        final int x = (int)(index*image.getViewport().getWidth());
        image.setViewport(new Rectangle2D(x, 0, image.getViewport().getWidth(), image.getViewport().getHeight()));
    }

    protected void setViewPort(Rectangle2D viewPort){
        image.setViewport(viewPort);
    }

    protected Rectangle2D getViewPort(){
        return image.getViewport();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }
}
