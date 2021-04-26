package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import view.ClickableImage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController {

    private SceneController sceneController;

    @FXML
    void backMouseClicked() {
        sceneController.setCurrentPane("main");
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}