package model;

/**
 * Стратегия.
 * Сложная сложность змейки.
 * Класс создает новую змейку сложной сложности.
 * @see model.Snake (Класс-родитель)
 * @author Golikov Alexandr
 * @version 1
 */
public class HardSnakeStrategy extends Snake {

    /**
     * Конструктор класса создает змейку (Сложность - сложная)
     */
    public HardSnakeStrategy() {
        super(400, 100);
    }

}