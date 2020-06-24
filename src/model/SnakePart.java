package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Snake.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс по управлению частью змейки
 * @see Rectangle (Класс-родитель)
 * @author Golikov Alexandr
 * @version 1
 */
public class SnakePart extends Rectangle {

    /**
     * Текущее направление части змейки
     */
    private Direction direction;

    /**
     * Панель для отображения части змейки
     */
    private Pane pane;

    /**
     * Ширина змейки
     */
    private double width = 20;

    /**
     * Координаты X на которых нужно изменить направление части змейки
     */
    private List<Double> pointsChangeDirectionX = new LinkedList<>();
    /**
     * Координаты Y на которых нужно изменить направление части змейки
     */
    private List<Double> pointsChangeDirectionY = new LinkedList<>();
    /**
     * Новые направления части змейки
     */
    private List<Direction> pointsChangeDirectionD = new LinkedList<>();

    /**
     * Конструктор класса, принимающий 3 переменные
     * @param pane Панель для отрисовки
     * @param x Координата X
     * @param y Координата Y
     */
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

    /**
     * @return Возвращает текущее направление
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return Возвращает список координат X для изменения направления
     */
    public List<Double> getPointsChangeDirectionX() {
        return pointsChangeDirectionX;
    }
    /**
     * @return Возвращает список координат Y для изменения направления
     */
    public List<Double> getPointsChangeDirectionY() {
        return pointsChangeDirectionY;
    }
    /**
     * @return Возвращает список новых направлений
     */
    public List<Direction> getPointsChangeDirectionD() {
        return pointsChangeDirectionD;
    }

    /**
     * Изменяет направление части змейки
     * @param direction Новое направление
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Изменяет список координат X для изменения направлений
     * @param pointsChangeDirectionX
     */
    public void setPointsChangeDirectionX(List<Double> pointsChangeDirectionX) {
        for (Double d: pointsChangeDirectionX) {
            this.pointsChangeDirectionX.add(d);
        }
    }
    /**
     * Изменяет список координат Y для изменения направлений
     * @param pointsChangeDirectionY
     */
    public void setPointsChangeDirectionY(List<Double> pointsChangeDirectionY) {
        for (Double d: pointsChangeDirectionY) {
            this.pointsChangeDirectionY.add(d);
        }
    }
    /**
     * Изменяет список новых направлений
     * @param pointsChangeDirectionD
     */
    public void setPointsChangeDirectionD(List<Direction> pointsChangeDirectionD) {
        for (Direction d: pointsChangeDirectionD) {
            this.pointsChangeDirectionD.add(d);
        }
    }

    /**
     * Добавляет новую точку и новое направление
     * @param direction Новое направление
     * @param x Координата X новой точки
     * @param y Коорданата Y новой точки
     */
    public void addPointToChangeDirection(Direction direction, double x, double y) {
        pointsChangeDirectionX.add(x);
        pointsChangeDirectionY.add(y);
        pointsChangeDirectionD.add(direction);
    }

    /**
     * Удаляет точку изменения направления
     * @param i Индекс точки
     */
    private void removePointFromChangeDirection(int i) {
        pointsChangeDirectionX.remove(i);
        pointsChangeDirectionY.remove(i);
        pointsChangeDirectionD.remove(i);
    }


    /**
     * Перемещает часть змейки на новую координату в зависимости от текущего направления
     */
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
                super.setY(pane.getPrefHeight() - width);
            else
                super.setY(super.getY() - width);
        }
        else if (direction == Direction.DOWN) {
            if (super.getY() + 2 * width > pane.getPrefHeight())
                super.setY(0);
            else
                super.setY(super.getY() + width);
        }
        else if (direction == Direction.RIGHT) {
            if (super.getX() + 2 * width > pane.getPrefWidth())
                super.setX(0);
            else
                super.setX(super.getX() + width);
        }
        else if (direction == Direction.LEFT) {
            if (super.getX() - width < 0)
                super.setX(pane.getPrefWidth() - width);
            else
                super.setX(super.getX() - width);
        }

    }

}
