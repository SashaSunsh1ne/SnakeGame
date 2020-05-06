package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HardSnakeStrategy implements SnakeGame {

    private TextField score;

    private Timer timer;
    private TimerTask timerTask;

    private int speed = 100;
    private int width = 20;
    private int paneSide = 400;

    private List<SnakePart> snakeParts = new LinkedList<>();
    private SnakeFood snakeFood;
    private Pane pane;
    private Stage newWindow;

    private Stage aboutWindow = new Stage();
    private String about = "# SnakeGame\n" +
            "Snake Game / Golikov Alexander \n" +
            "Стандартная игра змейка\n" +
            "\n" +
            "Сложности:\n" +
            "  1) Легкая - скорость маленькая, размер поля - 200x200 пикселей\n" +
            "  2) Средняя - скорость средняя, размер поля - 200x200 пикселей\n" +
            "  3) Сложная - скорость высокая, размер поля - 400x400 пикселей\n" +
            "\n" +
            "Функционал: \n" +
            "  1) Кнопки для начала и окончания игры\n" +
            "  2) Выбор сложности\n" +
            "  3) Запускается змейка в отдельном окне, чтобы не быть зависимой от окна пользователя, так же это дает возможность реализовать несколько игр, которые будут открываться в разых окнах, а не рисовать все в одной панели.\n" +
            "  3) Реализация сложностей по паттерну \"Стратегия\" (интерфейс с общими методами и тремя классами, которые влияют на сложность змейки)\n" +
            "  4) При закритии приложения любым способом закрываются все фоновые процессы.\n" +
            "  5) При выходе за пределы поля змейка переместится в противоположную сторону.\n" +
            "  6) Управлять змейкой можно только с помощью клавиатуры с помощью нажатий на клавиши W(вверх), A(влево), S(вниз) и D(вправо), так же управление работает на стрелки.\n" +
            "  7) Происходит проверка перед каждым поворотом змеи поэтому если она двигается вертикально, то повернуль можно только так, чтобы она начала двигаться горизантально и наоборот\n" +
            "  8) Победа достигается если змейка заполнит все поле и \"еде\" негде будет появляться\n" +
            "  9) При поражении появляется кнопка About в которой содержится описание игры.\n" +
            "\n" +
            "Архитектура:\n" +
            "\n" +
            "  Model:\n" +
            "  1) Интерфейс змейки (SnakeGame) с общими методами\n" +
            "  2) Три класса змейки, которые содержат похожее, но различное наполнение для реализации стратегии сложностей (EasySnakeStrategy, MediumSnakeStrategy и HardSnakeStrategy)\n" +
            "  3) Класс SnakePart, который будет являться одним звеном будущей змейки\n" +
            "  4) Класс SnakeFood, который будет отрисовывать на панель еду змейки\n" +
            "\n" +
            "  View:\n" +
            "  1) Sample.fxml - файл, который хранит интерфейс пользовательского приложения\n" +
            "  \n" +
            "  Controller: \n" +
            "  1) Controller.java - файл, в котором хранятся реализации для всех элементов на пользовательском интерфейсе Sample\n";
    private Button buttonAbout;

    public HardSnakeStrategy() {
        newScene();
        score = new TextField("");
        score.setPrefWidth(50);
        score.setEditable(false);
        score.setDisable(true);
        pane.getChildren().add(score);

        buttonAbout = new Button("About");
        buttonAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextArea textArea = new TextArea(about);
                Pane pane = new Pane();
                pane.getChildren().add(textArea);
                Scene aboutScene = new Scene(pane);
                aboutWindow = new Stage();
                aboutWindow.setTitle("About");
                aboutWindow.setScene(aboutScene);
                aboutWindow.setResizable(false);
                aboutWindow.show();
            }
        });
        buttonAbout.setLayoutX(paneSide - 50);
    }

    public void start() {
        timerSchedule(500, speed);
        newWindow.show();
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

    public void addOnPane() {
        for (double i = pane.getPrefWidth() / 2; i >= 0; i = i - width) {
            SnakePart snakePart = new SnakePart(pane, i, pane.getPrefHeight() / 2);
            snakeParts.add(snakePart);
            pane.getChildren().add(snakePart);
        }
        snakeFood = new SnakeFood(pane, snakeParts);
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
                    Text text = new Text("GAME OVER");
                    text.setFont(new Font(65));
                    text.setFill(Color.RED);
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLayoutY(pane.getPrefHeight() / 2);
                    text.setWrappingWidth(pane.getPrefWidth());
                    pane.getChildren().add(text);
                    timer.cancel();
                    pane.getChildren().remove(score);
                    pane.getChildren().add(score);
                    pane.getChildren().remove(buttonAbout);
                    pane.getChildren().add(buttonAbout);
                }
            snakePart.move();
        }
        if (snakeParts.get(0).getX() == snakeFood.getX() && snakeParts.get(0).getY() == snakeFood.getY()) {
            snakeFood.replaceFood();
            addPart();
        }
        score.setText(snakeParts.size()+"");
    }

    public void removeSnake() {
        newWindow.close();
        aboutWindow.close();
    }

    @Override
    public String getAbout() {
        return about;
    }

    public void newScene() {
        pane = new Pane();
        pane.setPrefWidth(paneSide);
        pane.setPrefHeight(paneSide);
        pane.setStyle("-fx-border-color: black");

        Button button = new Button();
        button.setPrefHeight(0);
        button.setPrefWidth(0);
        button.setLayoutX(-50);
        button.setLayoutY(-50);

        button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP)
                    changeDirection(Direction.UP);
                if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN)
                    changeDirection(Direction.DOWN);
                if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT)
                    changeDirection(Direction.RIGHT);
                if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT)
                    changeDirection(Direction.LEFT);
            }
        });
        pane.getChildren().add(button);
        Polyline borderLine = new Polyline(0, 0, paneSide, 0, paneSide, paneSide, 0, paneSide, 0, 0);
        borderLine.setStrokeWidth(5);
        borderLine.setStroke(Color.BLACK);
        pane.getChildren().add(borderLine);
        addOnPane();

        Scene snakeScene = new Scene(pane, paneSide, paneSide);

        newWindow = new Stage();
        newWindow.setTitle("SnakeGame");
        newWindow.setScene(snakeScene);
        newWindow.setResizable(false);
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.cancel();
            }
        });
    }
}
