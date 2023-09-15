package com.example.snakegame.controller;

import com.example.snakegame.util.Dimentions;
import com.example.snakegame.util.Direction;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Canvas canvas;

    private Direction direction = Direction.LEFT;

    private int foodX;

    private int foodY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        generateFood();
        new AnimationTimer(){
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(now - lastUpdate >= 100_000_000){
                    update();
                    draw(graphicsContext);
                    lastUpdate = now;
                }
            }
        }.start();

        root.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            if(newScene != null){
                setDirectionForKeyCode(newScene);
            }
        });
    }

    private void generateFood() {
        foodX = (int) (Math.random() * (Dimentions.WIDTH - Dimentions.SQUARE_SIZE) / Dimentions.SQUARE_SIZE) * Dimentions.SQUARE_SIZE;
        foodY = (int) (Math.random() * (Dimentions.HEIGHT - Dimentions.SQUARE_SIZE) / Dimentions.SQUARE_SIZE) * Dimentions.SQUARE_SIZE;
    }

    private void draw(GraphicsContext graphicsContext) {
        // Background Colour
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(0,0,Dimentions.WIDTH,Dimentions.HEIGHT);

    }

    private void update() {
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
            //snake can go up ,right ,down ,left, but he's once going to up he can't go to down because he cross his body.
        });
    }
}
