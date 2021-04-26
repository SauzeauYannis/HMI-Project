package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {

    private SceneController sceneController;

    @FXML
    private ImageView backIcon;

    @FXML
    void backMouseClicked() {
        sceneController.setCurrentPane("main");
    }

    @FXML
    void backMouseEntered() {
        UtilsController.rescaleNode(this.backIcon, 1.2);
    }

    @FXML
    void backMouseExited() {
        UtilsController.defaultScaleNode(this.backIcon);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.backIcon.setCursor(Cursor.HAND);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
}