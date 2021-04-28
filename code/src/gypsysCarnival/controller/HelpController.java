package gypsysCarnival.controller;

import javafx.fxml.FXML;

/**
 * The Help gypsysCarnival.controller.
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
     * Sets scene gypsysCarnival.controller.
     *
     * @param sceneController the scene gypsysCarnival.controller
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}