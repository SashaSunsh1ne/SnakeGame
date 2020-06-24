package model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, создающий змейку по переданным параметрам из стратегии.
 * @author Golikov Alexandr
 * @version 1
 */
public class Snake implements SnakeGame {

    /**
     * Перечисления, в котором хранятся все возможные направления змейки (ВВЕРХ, ВНИЗ, ВЛЕВО, ВПРАВО)
     */
    enum Direction {UP, DOWN, RIGHT, LEFT}

    /**
     * Переменная, хранящая счет игрока
     * @see TextField - Текстовое поле
     */
    private TextField score;

    /**
     * Переменная, хранящая таймер игры, запускающийся
     * при начале новои игры и отключающийся при ее завершении.
     * @see Timer - Таймер
     */
    private Timer timer;

    /**
     * Переменная, хранящая сторону панели (в пикселях),
     * на которую будет выведена змейка
     */
    private int paneSide;
    /**
     * Переменная, хранящая скорость зейки
     * (задержка между перемещениями в миллисекундах)
     */
    private int speed;

    /**
     * Список, который хранит все части змейки (ячейки)
     * @see SnakePart - Часть змейки
     */
    private List<SnakePart> snakeParts = new LinkedList<>();
    /**
     * Переменная "еда змеи" для отображения ее на панели
     * @see SnakeFood - "Еда" змейки
     */
    private SnakeFood snakeFood;
    /**
     * Панель для отображения всех элементов игры
     * @see Pane - Панель
     */
    private Pane pane;
    /**
     * Переменная в которую помещается новое окно с игрой
     * @see Stage - Окно
     */
    private Stage newWindow;

    /**
     * Конструктор, создающий экземпляр класса со значениями,
     * которые передает класс-стратегия
     * @param paneSide
     * @param speed
     */
    public Snake(int paneSide, int speed) {
        this.paneSide = paneSide;
        this.speed = speed;
        newScene();
        score = new TextField("");
        score.setPrefWidth(50);
        score.setEditable(false);
        score.setDisable(true);
        pane.getChildren().add(score);
    }

    /**
     * Метод, отвечающий за начало игры
     * (запуск таймера и открытие окна с игрой)
     */
    public void start() {
        timerSchedule();
        newWindow.show();
    }

    /**
     * Метод, устанавливающийсть скорость змейки
     * (устанавливает расписание теймера)
     */
    private void timerSchedule() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> draw());
            }
        };
        timer.schedule(timerTask, 500, speed);
    }

    /**
     * Метод для добавления новой части змейки
     */
    private void addPart() {
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

    /**
     * Метод, который добавляет на панель начальную змейку
     * (длина равна половине ширины панели)
     */
    private void addOnPane() {
        int width = 20;
        for (double i = pane.getPrefWidth() / 2; i >= 0; i = i - width) {
            SnakePart snakePart = new SnakePart(pane, i, pane.getPrefHeight() / 2);
            snakeParts.add(snakePart);
            pane.getChildren().add(snakePart);
        }
        snakeFood = new SnakeFood(pane, snakeParts);
    }

    /**
     * Метод, предназначенный для проверки возможности
     * изменения направления змейки в указанную сторону
     * @param direction принимает новое направление
     * @return Возвращает истину или ложь (Можно ли изменить направление или нет)
     */
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

    /**
     * Метод для изменения напрвавления
     * @param direction принимает новое направление
     */
    private void changeDirection(Direction direction) {
        if (tryToChangeDirection(direction))
            for (SnakePart snakePart : snakeParts)
                snakePart.addPointToChangeDirection(direction, snakeParts.get(0).getX(), snakeParts.get(0).getY());
    }

    /**
     * Метод для отображения всех элеметов игры на панели
     */
    private void draw() {
        for (SnakePart snakePart : snakeParts) {
            if (snakePart != snakeParts.get(0))
                if (snakeParts.get(0).getX() == snakePart.getX() && snakeParts.get(0).getY() == snakePart.getY()) {
                    Text text = new Text("GAME OVER");
                    text.setFont(new Font(40));
                    text.setFill(Color.RED);
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLayoutY(pane.getPrefHeight() / 2);
                    text.setWrappingWidth(pane.getPrefWidth());
                    pane.getChildren().add(text);
                    timer.cancel();
                    pane.getChildren().remove(score);
                    pane.getChildren().add(score);
                }
            snakePart.move();
        }
        if (snakeParts.get(0).getX() == snakeFood.getX() && snakeParts.get(0).getY() == snakeFood.getY()) {
            addPart();
            snakeFood.replaceFood();
            if (snakeParts.size() >= Math.pow(paneSide / 20, 2)) {
                Text text = new Text("YOU WIN");
                text.setFont(new Font(40));
                text.setFill(Color.GREEN);
                text.setTextAlignment(TextAlignment.CENTER);
                text.setLayoutY(pane.getPrefHeight() / 2);
                text.setWrappingWidth(pane.getPrefWidth());
                pane.getChildren().add(text);
                timer.cancel();
                pane.getChildren().remove(score);
                pane.getChildren().add(score);
            }
        }
        score.setText(snakeParts.size() + "");
    }

    /**
     * Метод для удаления элементов игры с панели
     */
    public void removeSnake() {
        newWindow.close();
    }

    /**
     * Метод создающий новое окно игры
     */
    private void newScene() {
        pane = new Pane();
        pane.setPrefWidth(paneSide);
        pane.setPrefHeight(paneSide);

        Button button = new Button();
        button.setPrefHeight(0);
        button.setPrefWidth(0);
        button.setLayoutX(-50);
        button.setLayoutY(-50);

        button.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP)
                changeDirection(Direction.UP);
            if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN)
                changeDirection(Direction.DOWN);
            if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT)
                changeDirection(Direction.RIGHT);
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT)
                changeDirection(Direction.LEFT);
        });
        pane.getChildren().add(button);
        Polyline borderLine = new Polyline(0, 0, paneSide, 0, paneSide, paneSide, 0, paneSide, 0, 0);
        borderLine.setStrokeWidth(5);
        borderLine.setStroke(Color.BLACK);
        pane.getChildren().add(borderLine);

        addOnPane();

        Scene snakeScene = new Scene(pane);

        newWindow = new Stage();
        newWindow.setTitle("SnakeGame");
        newWindow.setScene(snakeScene);
        newWindow.setResizable(false);
        newWindow.setOnCloseRequest(event -> timer.cancel());
    }
}
