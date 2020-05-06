package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

public class SnakeFood extends Rectangle {

    private Pane pane;
    private List<SnakePart> snakeParts;
    private Random random = new Random();
    final private double width = 20;

    public SnakeFood (Pane pane, List<SnakePart> snakeParts) {
        super();
        super.setHeight(width);
        super.setWidth(width);
        super.setFill(Color.RED);
        int x = (int) (pane.getPrefWidth() - width);
        int y = (int) (pane.getPrefHeight() / 2);
        super.setX(x);
        super.setY(y);
        this.snakeParts = snakeParts;
        pane.getChildren().add(this);
        this.pane = pane;
        //replaceFood();
    }

    public void replaceFood() {
        boolean isMatch;
        int x = 0;
        int y = 0;
        do {
            isMatch = false;
            x = (int) (random.nextInt((int) (pane.getPrefWidth() / width)) * width);
            y = (int) (random.nextInt((int) (pane.getPrefWidth() / width)) * width);
            for (SnakePart snakePart : snakeParts) {
                if (snakePart.getX() == x && snakePart.getY() == y)
                    isMatch = true;
            }
        }
        while (isMatch);
        super.setX(x);
        super.setY(y);
    }

}
