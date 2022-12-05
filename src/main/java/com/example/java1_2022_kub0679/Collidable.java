package com.example.java1_2022_kub0679;

import javafx.geometry.Rectangle2D;

public interface Collidable {
    Rectangle2D getBoundingBox();
    boolean overlaps(Rectangle2D otherBox);
    void hit(Collidable another);

}
