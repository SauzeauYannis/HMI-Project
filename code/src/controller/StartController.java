package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The Start controller.
 */
public class StartController {

    /*--------------------- Private members -------------------------*/

    private SceneController sceneController;

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    /*--------------------- Public methods -------------------------*/

    /**
     * Play mouse clicked.
     */
    public void playMouseClicked() {
        this.sceneController.setCurrentPane("main");
    }

    /**
     * Quit mouse clicked.
     */
    @FXML
    public void quitMouseClicked() {
        Platform.exit();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets scene controller.
     *
     * @param sceneController the scene controller
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}
