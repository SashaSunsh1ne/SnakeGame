package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.SnakeGame;
import model.SnakeGame.Direction;
import model.SnakeGame.Difficulty;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Pane pane = new Pane();
    public RadioButton easy;
    public RadioButton medium;
    public RadioButton hard;
    public Button start;
    private ToggleGroup toggleGroup = new ToggleGroup();
    SnakeGame snakeGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        easy.setToggleGroup(toggleGroup);
        medium.setToggleGroup(toggleGroup);
        hard.setToggleGroup(toggleGroup);
        medium.setSelected(true);
        snakeGame = new SnakeGame(Difficulty.EASY, pane);
        snakeGame.removeSnake();
    }

    public void startSnake(Difficulty difficulty) {
        easy.setVisible(false);
        medium.setVisible(false);
        hard.setVisible(false);
        snakeGame = new SnakeGame(difficulty, pane);
        start.setText("RESTART");
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP)
            snakeGame.changeDirection(Direction.UP);
        if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN)
            snakeGame.changeDirection(Direction.DOWN);
        if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT)
            snakeGame.changeDirection(Direction.RIGHT);
        if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT)
            snakeGame.changeDirection(Direction.LEFT);
    }

    public void up(ActionEvent actionEvent) {
        snakeGame.changeDirection(Direction.UP);
    }

    public void right(ActionEvent actionEvent) {
        snakeGame.changeDirection(Direction.RIGHT);
    }

    public void left(ActionEvent actionEvent) {
        snakeGame.changeDirection(Direction.LEFT);
    }

    public void down(ActionEvent actionEvent) {
        snakeGame.changeDirection(Direction.DOWN);
    }

    public void restart(ActionEvent actionEvent) {
        snakeGame.removeSnake();
        if (easy.isSelected())
            startSnake(Difficulty.EASY);
        else if (medium.isSelected())
            startSnake(Difficulty.MEDIUM);
        else if (hard.isSelected())
            startSnake(Difficulty.HARD);
    }
}
