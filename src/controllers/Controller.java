package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import model.*;

import java.io.IOException;
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
        snakeGame = new EasySnakeStrategy();
    }

    public void startSnake(SnakeGame snakeGameBuilder) {
        snakeGame = snakeGameBuilder;
        snakeGame.start();
        start.setText("RESTART");
    }

    public void restart(ActionEvent actionEvent) throws IOException {
        snakeGame.removeSnake();
        if (easy.isSelected())
            startSnake(new EasySnakeStrategy());
        else if (medium.isSelected())
            startSnake(new MediumSnakeStrategy());
        else if (hard.isSelected())
            startSnake(new HardSnakeStrategy());
    }
}
