package com.example.snakegame.controller;

import com.example.snakegame.util.Direction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Canvas canvas;

    private Direction direction = Direction.LEFT;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            if(newScene != null){
                setDirectionForKeyCode(newScene);
            }
        });
    }

    private void setDirectionForKeyCode(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP && direction != Direction.DOWN){
                direction = Direction.UP;
                System.out.println(direction);
            } else if (keyEvent.getCode() == KeyCode.DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
                System.out.println(direction);
            } else if (keyEvent.getCode() == KeyCode.LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
                System.out.println(direction);
            } else if (keyEvent.getCode() == KeyCode.RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
                System.out.println(direction);
            }
            //sanke can go up ,right ,down ,left, but he's once going to up he can't go to down because he cross his body
        });
    }
}
