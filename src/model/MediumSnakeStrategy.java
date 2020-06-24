package model;

/**
 * Стратегия.
 * Средняя сложность змейки.
 * Класс создает новую змейку средней сложности.
 * @see model.Snake (Класс-родитель)
 * @author Golikov Alexandr
 * @version 1
 */
public class MediumSnakeStrategy extends Snake {

    /**
     * Конструктор класса создает змейку (Сложность - средняя)
     */
    public MediumSnakeStrategy() {
        super(200, 200);
    }

}