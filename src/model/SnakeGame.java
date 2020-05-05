package model;

public interface SnakeGame {
    enum Direction {UP, DOWN, RIGHT, LEFT}
    void start();
    void removeSnake();
}
