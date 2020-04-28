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
        super.setX(random.nextInt(30) * 20);
        super.setY(random.nextInt(30) * 20);
        this.snakeParts = snakeParts;
        pane.getChildren().add(this);
        this.pane = pane;
    }

    public void replaceFood() {
        boolean isMatch;
        int x = 0;
        int y = 0;
        do {
            isMatch = false;
            x = random.nextInt(30) * 20;
            y = random.nextInt(30) * 20;
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
