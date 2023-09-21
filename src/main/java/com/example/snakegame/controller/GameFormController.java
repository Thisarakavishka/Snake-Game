package com.example.snakegame.controller;

import com.example.snakegame.util.Dimensions;
import com.example.snakegame.util.Direction;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Canvas canvas;

    private Direction direction = Direction.RIGHT;
    private int[][] grid = new int[Dimensions.WIDTH / Dimensions.SQUARE_SIZE][Dimensions.HEIGHT / Dimensions.SQUARE_SIZE];
    private int snakeX = 0;
    private int snakeY = 0;
    private int snakeLength = 3;

    private int[] snakeXs = new int[snakeLength];
    private int[] snakeYs = new int[snakeLength];
    private boolean gameOver = false;
    private int foodX;
    private int foodY;
    private AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        generateFood();
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) {
                    update();
                    draw(graphicsContext);
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();

        root.sceneProperty().addListener((observableValue, oldScene, newScene) -> {
            if (newScene != null) {
                setDirectionForKeyCode(newScene);
            }
        });
    }

    private void generateFood() {
        foodX = (int) (Math.random() * (Dimensions.WIDTH - Dimensions.SQUARE_SIZE) / Dimensions.SQUARE_SIZE) * Dimensions.SQUARE_SIZE;
        foodY = (int) (Math.random() * (Dimensions.HEIGHT - Dimensions.SQUARE_SIZE) / Dimensions.SQUARE_SIZE) * Dimensions.SQUARE_SIZE;
    }

    private void draw(GraphicsContext graphicsContext) {
        // Background Colour
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, Dimensions.WIDTH, Dimensions.HEIGHT);

        //Draw snake
        graphicsContext.setFill(Color.GREEN);
        for (int i = 0; i < snakeLength; i++) {
            graphicsContext.fillRect(snakeXs[i], snakeYs[i], Dimensions.SQUARE_SIZE, Dimensions.SQUARE_SIZE);
        }

        //Draw Food
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(foodX, foodY, Dimensions.SQUARE_SIZE, Dimensions.SQUARE_SIZE);

        //Draw Grid
        graphicsContext.setStroke(Color.GRAY);
        for (int i = 0; i < grid.length; i++) {
            graphicsContext.strokeLine(i * Dimensions.SQUARE_SIZE, 0, i * Dimensions.SQUARE_SIZE, Dimensions.HEIGHT);
        }
        for (int i = 0; i < grid[0].length; i++) {
            graphicsContext.strokeLine(0, i * Dimensions.SQUARE_SIZE, Dimensions.WIDTH, i * Dimensions.SQUARE_SIZE);
        }

        //when game over
        if (gameOver) {
            try {
                animationTimer.stop();
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/snakegame/view/GameOverForm.fxml"))));
                stage.centerOnScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void update() {
        if (gameOver) {
            return;
        }

        //update snake
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];
        }

        switch (direction) {
            case UP:
                snakeY -= Dimensions.SQUARE_SIZE;
                break;
            case RIGHT:
                snakeX += Dimensions.SQUARE_SIZE;
                break;
            case DOWN:
                snakeY += Dimensions.SQUARE_SIZE;
                break;
            case LEFT:
                snakeX -= Dimensions.SQUARE_SIZE;
                break;
        }

        snakeXs[0] = snakeX;
        snakeYs[0] = snakeY;

        //Check snake if snake has collided with food
        if (snakeX == foodX && snakeY == foodY) {
            snakeLength++;
            snakeXs = Arrays.copyOf(snakeXs, snakeLength);
            snakeYs = Arrays.copyOf(snakeYs, snakeLength);
            generateFood();
        }

        //Check snake if snake has collided with wall
        if (snakeX < 0 || snakeX >= Dimensions.WIDTH || snakeY < 0 || snakeY >= Dimensions.HEIGHT) {
            gameOver = true;
        }

        //Check snake if snake has collided with itself
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX == snakeXs[i] && snakeY == snakeYs[i]) {
                gameOver = true;
                break;
            }
        }
    }

    private void setDirectionForKeyCode(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP && direction != Direction.DOWN) {
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
