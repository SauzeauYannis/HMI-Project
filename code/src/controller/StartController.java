package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    private SceneController sceneController;

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    public void playMouseClicked() {
        this.sceneController.setCurrentPane("main");
    }

    @FXML
    void playMouseEntered() {
        this.buttonFocusOn(this.playButton);
    }

    @FXML
    void playMouseExited() {
        this.buttonFocusOff(this.playButton);
    }

    @FXML
    void quitMouseClicked() {
        Platform.exit();
    }

    @FXML
    void quitMouseEntered() {
        this.buttonFocusOn(this.quitButton);
    }

    @FXML
    void quitMouseExited() {
        this.buttonFocusOff(this.quitButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playButton.setCursor(Cursor.HAND);
        this.playButton.setBackground(this.setFocusBackground(false));

        this.quitButton.setCursor(Cursor.HAND);
        this.quitButton.setBackground(this.setFocusBackground(false));
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    private void buttonFocusOn(Button button) {
        UtilsController.rescaleNode(button, 1.1);
        button.setBackground(this.setFocusBackground(true));
    }

    private void buttonFocusOff(Button button) {
        UtilsController.defaultScaleNode(button);
        button.setBackground(this.setFocusBackground(false));
    }

    private Background setFocusBackground(boolean isFocus) {
        Color color;

        if (isFocus)
            color = Color.rgb(100, 100, 255);
        else
            color = Color.rgb(140, 140, 255);

        return new Background(
                new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)
        );
    }
}
