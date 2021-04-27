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

/**
 * The Start controller.
 */
public class StartController implements Initializable {

    /*--------------------- Private members -------------------------*/

    private SceneController sceneController;

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.playButton.setCursor(Cursor.HAND);
        this.playButton.setBackground(this.setFocusBackground(false));

        this.quitButton.setCursor(Cursor.HAND);
        this.quitButton.setBackground(this.setFocusBackground(false));
    }

    /**
     * Play mouse clicked.
     */
    public void playMouseClicked() {
        this.sceneController.setCurrentPane("main");
    }

    /**
     * Play mouse entered.
     */
    @FXML
    public void playMouseEntered() {
        this.buttonFocusOn(this.playButton);
    }

    /**
     * Play mouse exited.
     */
    @FXML
    public void playMouseExited() {
        this.buttonFocusOff(this.playButton);
    }

    /**
     * Quit mouse clicked.
     */
    @FXML
    public void quitMouseClicked() {
        Platform.exit();
    }

    /**
     * Quit mouse entered.
     */
    @FXML
    public void quitMouseEntered() {
        this.buttonFocusOn(this.quitButton);
    }

    /**
     * Quit mouse exited.
     */
    @FXML
    public void quitMouseExited() {
        this.buttonFocusOff(this.quitButton);
    }

    /**
     * Sets scene controller.
     *
     * @param sceneController the scene controller
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Button focus on.
     *
     * @param button the button
     */
    private void buttonFocusOn(Button button) {
        button.setScaleX(1.1);
        button.setScaleY(1.1);
        button.setBackground(this.setFocusBackground(true));
    }

    /**
     * Button focus off.
     *
     * @param button the button
     */
    private void buttonFocusOff(Button button) {
        button.setScaleX(1);
        button.setScaleY(1);
        button.setBackground(this.setFocusBackground(false));
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets focus background.
     *
     * @param isFocus the is focus
     * @return the focus background
     */
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
