package controller;

import javafx.fxml.FXML;

/**
 * The Help controller.
 */
public class HelpController {

    /*--------------------- Private members -------------------------*/

    private SceneController sceneController;

    /*--------------------- Public methods -------------------------*/

    /**
     * Back mouse clicked.
     */
    @FXML
    public void backMouseClicked() {
        sceneController.setCurrentPane("main");
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