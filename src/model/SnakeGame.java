package model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeGame {

    public enum Direction {UP, DOWN, RIGHT, LEFT}
    public enum Difficulty {EASY, MEDIUM, HARD}

    private Timer timer;
    private TimerTask timerTask;

    private int speed;

    private List<SnakePart> snakeParts = new LinkedList<>();
    private SnakeFood snakeFood;
    private Difficulty difficulty;
    private Pane pane;

    public SnakeGame(Difficulty difficulty, Pane pane) {
        this.difficulty = difficulty;
        this.pane = pane;
        for (int i = 300; i >= 220; i = i - 20) {
            SnakePart snakePart = new SnakePart(pane, i, 300);
            snakeParts.add(snakePart);
            pane.getChildren().add(snakePart);
        }
        snakeFood = new SnakeFood(pane, snakeParts);
        setSpeed();
        timerSchedule(500, speed);
    }

    private void timerSchedule(int delay, int speed) {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        draw();
                    }
                });
            }
        };
        timer.schedule(timerTask, delay, speed);
    }

    public void addPart() {
        SnakePart snakePart = new SnakePart(pane, snakeParts.get(snakeParts.size() - 1).getX(), snakeParts.get(snakeParts.size() - 1).getY());
        snakePart.setDirection(snakeParts.get(snakeParts.size() - 1).getDirection());
        snakePart.setPointsChangeDirectionD(snakeParts.get(snakeParts.size() - 1).getPointsChangeDirectionD());
        snakePart.setPointsChangeDirectionX(snakeParts.get(snakeParts.size() - 1).getPointsChangeDirectionX());
        snakePart.setPointsChangeDirectionY(snakeParts.get(snakeParts.size() - 1).getPointsChangeDirectionY());
        if (snakePart.getDirection() == Direction.UP)
            snakePart.setY(snakePart.getY() + 20);
        if (snakePart.getDirection() == Direction.DOWN)
            snakePart.setY(snakePart.getY() - 20);
        if (snakePart.getDirection() == Direction.RIGHT)
            snakePart.setX(snakePart.getX() - 20);
        if (snakePart.getDirection() == Direction.LEFT)
            snakePart.setX(snakePart.getX() + 20);
        pane.getChildren().add(snakePart);
        snakeParts.add(snakePart);
    }

    private void setSpeed() {
        if (difficulty == Difficulty.EASY)
            speed = 500;
        else if (difficulty == Difficulty.MEDIUM)
            speed = 300;
        else if (difficulty == Difficulty.HARD)
            speed = 100;
    }

    private boolean tryToChangeDirection(Direction direction) {
        if (direction == Direction.UP && snakeParts.get(0).getDirection() != Direction.UP && snakeParts.get(0).getDirection() != Direction.DOWN)
            return true;
        else if (direction == Direction.DOWN && snakeParts.get(0).getDirection() != Direction.UP && snakeParts.get(0).getDirection() != Direction.DOWN)
            return true;
        else if (direction == Direction.RIGHT && snakeParts.get(0).getDirection() != Direction.LEFT && snakeParts.get(0).getDirection() != Direction.RIGHT)
            return true;
        else if (direction == Direction.LEFT && snakeParts.get(0).getDirection() != Direction.LEFT && snakeParts.get(0).getDirection() != Direction.RIGHT)
            return true;
        return false;
    }

    public void changeDirection(Direction direction) {
        if (tryToChangeDirection(direction))
            for (SnakePart snakePart : snakeParts)
                snakePart.addPointToChangeDirection(direction, snakeParts.get(0).getX(), snakeParts.get(0).getY());
    }

    private void draw() {
        for (SnakePart snakePart : snakeParts) {
            if (snakePart != snakeParts.get(0))
                if (snakeParts.get(0).getX() == snakePart.getX() && snakeParts.get(0).getY() == snakePart.getY()) {
                    System.out.println("Game Over " + snakeParts.size());
                    timer.cancel();
                }
            snakePart.move();
        }
        if (snakeParts.get(0).getX() == snakeFood.getX() && snakeParts.get(0).getY() == snakeFood.getY()) {
            snakeFood.replaceFood();
            addPart();
        }

    }

    public void removeSnake() {
        for (SnakePart snakePart : snakeParts) {
            pane.getChildren().remove(snakePart);
        }
        pane.getChildren().remove(snakeFood);
    }
}
