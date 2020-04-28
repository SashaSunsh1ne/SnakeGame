package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.SnakeGame.Direction;

import java.util.LinkedList;
import java.util.List;

public class SnakePart extends Rectangle {

    private Direction direction;

    private Pane pane;

    private double width = 20;

    private List<Double> pointsChangeDirectionX = new LinkedList<>();
    private List<Double> pointsChangeDirectionY = new LinkedList<>();
    private List<Direction> pointsChangeDirectionD = new LinkedList<>();

    public SnakePart(Pane pane, double x, double y) {
        super();
        super.setWidth(width);
        super.setHeight(width);
        super.setFill(Color.BLACK);
        direction = Direction.RIGHT;
        this.pane = pane;
        super.setX(x);
        super.setY(y);
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Double> getPointsChangeDirectionX() {
        return pointsChangeDirectionX;
    }

    public List<Double> getPointsChangeDirectionY() {
        return pointsChangeDirectionY;
    }

    public List<Direction> getPointsChangeDirectionD() {
        return pointsChangeDirectionD;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPointsChangeDirectionX(List<Double> pointsChangeDirectionX) {
        for (Double d: pointsChangeDirectionX) {
            this.pointsChangeDirectionX.add(d);
        }
    }

    public void setPointsChangeDirectionY(List<Double> pointsChangeDirectionY) {
        for (Double d: pointsChangeDirectionY) {
            this.pointsChangeDirectionY.add(d);
        }
    }

    public void setPointsChangeDirectionD(List<Direction> pointsChangeDirectionD) {
        for (Direction d: pointsChangeDirectionD) {
            this.pointsChangeDirectionD.add(d);
        }
    }

    public void addPointToChangeDirection(Direction direction, double x, double y) {
        pointsChangeDirectionX.add(x);
        pointsChangeDirectionY.add(y);
        pointsChangeDirectionD.add(direction);
    }

    private void removePointFromChangeDirection(int i) {
        pointsChangeDirectionX.remove(i);
        pointsChangeDirectionY.remove(i);
        pointsChangeDirectionD.remove(i);
    }



    public void move() {
        for (int i = 0; i < pointsChangeDirectionD.size(); i++) {
            if (super.getX() == pointsChangeDirectionX.get(i) && super.getY() == pointsChangeDirectionY.get(i)) {
                direction = pointsChangeDirectionD.get(i);
                removePointFromChangeDirection(i);
                break;
            }
        }

        if (direction == Direction.UP) {
            if (super.getY() - width < 0)
                super.setY(pane.getHeight() - width);
            else
                super.setY(super.getY() - width);
        }
        else if (direction == Direction.DOWN) {
            if (super.getY() + 2 * width > pane.getHeight())
                super.setY(0);
            else
                super.setY(super.getY() + width);
        }
        else if (direction == Direction.RIGHT) {
            if (super.getX() + 2 * width > pane.getWidth())
                super.setX(0);
            else
                super.setX(super.getX() + width);
        }
        else if (direction == Direction.LEFT) {
            if (super.getX() - width < 0)
                super.setX(pane.getWidth() - width);
            else
                super.setX(super.getX() - width);
        }

    }

}
