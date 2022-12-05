package com.example.java1_2022_kub0679;

import javafx.animation.Interpolator;

public abstract class FallingObject extends GameEntity{

    boolean hasHit = false;

    FallingObject(Game game) {
        super(game);
    }

    @Override
    public void simulate(double deltaT) {
        if(image == null) return;
        if (isFalling() || isJumping()) {
            lookVector = lookVector.add(0, gravity);
            if (image.getY() >= 500) {
                destroy();
                return;
            }
        }

        this.image.setX(image.getX() + lookVector.getX() * deltaT);
        this.image.setY(image.getY() + lookVector.getY() * deltaT);
    }

    public void destroy(){
        game.getCollectingGame().destroyEntity(this);
        animation.stop();
        Interpolator inter = animation.getInterpolator();
        inter = null;
        animation = null;
        image = null;
    }

    public boolean hasHit() {
        return hasHit;
    }

    public void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }
}
