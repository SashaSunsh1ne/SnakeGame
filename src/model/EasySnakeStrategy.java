package model;

/**
 * Стратегия.
 * Легкая сложность змейки.
 * Класс создает новую змейку легкой сложности.
 * @see model.Snake (Класс-родитель)
 * @author Golikov Alexandr
 * @version 1
 */
public class EasySnakeStrategy extends Snake {

    /**
     * Конструктор класса создает змейку (Сложность - легкая)
     */
    public EasySnakeStrategy() {
        super(200, 500);
    }

}
