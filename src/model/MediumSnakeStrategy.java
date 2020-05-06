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

public class MediumSnakeStrategy implements SnakeGame{

    private TextField score;

    private Timer timer;

    private int paneSide = 200;

    private List<SnakePart> snakeParts = new LinkedList<>();
    private SnakeFood snakeFood;
    private Pane pane;
    private Stage newWindow;

    private Stage aboutWindow = new Stage();
    private String about = "# SnakeGame\n" +
            "Сложности:\n" +
            "  1) Легкая - скорость маленькая, размер поля - 200x200 пикселей\n" +
            "  2) Средняя - скорость средняя, размер поля - 200x200 пикселей\n" +
            "  3) Сложная - скорость высокая, размер поля - 400x400 пикселей\n" +
            "Проигрыш происходит, если змейка врежется с себя\n" +
            "Выигрыш происходит, если змейка достигнет своего \n" +
            "максимального размера на поле\n";
    private Button buttonAbout;

    public MediumSnakeStrategy() {
        newScene();
        score = new TextField("");
        score.setPrefWidth(50);
        score.setEditable(false);
        score.setDisable(true);
        pane.getChildren().add(score);

        buttonAbout = new Button("About");
        buttonAbout.setOnAction(event -> {
            TextArea textArea = new TextArea(about);
            textArea.setEditable(false);
            Pane pane = new Pane();
            pane.getChildren().add(textArea);
            Scene aboutScene = new Scene(pane);
            aboutWindow.setTitle("About");
            aboutWindow.setScene(aboutScene);
            aboutWindow.setResizable(false);
            aboutWindow.show();
        });
        buttonAbout.setPrefWidth(paneSide);
        buttonAbout.setAlignment(Pos.CENTER);
        buttonAbout.setLayoutY(paneSide - 30);
    }

    public void start() {
        timerSchedule();
        newWindow.show();
    }

    private void timerSchedule() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> draw());
            }
        };
        timer.schedule(timerTask, 500, 200);
    }

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

    private void addOnPane() {
        int width = 20;
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

    private void changeDirection(Direction direction) {
        if (tryToChangeDirection(direction))
            for (SnakePart snakePart : snakeParts)
                snakePart.addPointToChangeDirection(direction, snakeParts.get(0).getX(), snakeParts.get(0).getY());
    }

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
                    pane.getChildren().remove(buttonAbout);
                    pane.getChildren().add(buttonAbout);
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
                pane.getChildren().remove(buttonAbout);
                pane.getChildren().add(buttonAbout);
            }
        }
        score.setText(snakeParts.size() + "");
    }

    public void removeSnake() {
        newWindow.close();
        aboutWindow.close();
    }

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
