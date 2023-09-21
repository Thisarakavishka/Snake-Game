package com.example.snakegame.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOverFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnExit;

    @FXML
    private JFXButton btnPlay;

    private Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            stage = (Stage) root.getScene().getWindow();
        });
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/snakegame/view/LoginForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlayOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/snakegame/view/GameForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
