package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

/**
 * Класс по управлению "едой" змейки
 * @see Rectangle (Класс-родитель)
 * @author Golikov Alexandr
 * @version 1
 */
public class SnakeFood extends Rectangle {

    /**
     * Панель для отображения "еды"
     * @see Pane - Панель
     */
    private Pane pane;
    /**
     * Список, который хранит все части змейки (ячейки)
     * @see SnakePart - Часть змейки
     */
    private List<SnakePart> snakeParts;
    /**
     * Переменная создающая случайные числа и значения
     */
    private Random random = new Random();
    /**
     * Переменная, хранящая ширину "еды"
     */
    final private double width = 20;

    /**
     * Конструктор класса, принимающий две переменные
     * @param pane
     * @param snakeParts
     */
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
    }

    /**
     * Метод для изменения координат "еды"
     */
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
