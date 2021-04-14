package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.character.Player;

import java.io.IOException;

/**
 * The type Help controller.
 */
public class HelpController {

    private AnchorPane root;
    private Scene scene;

    @FXML
    private ImageView backIcon;

    /**
     * Back mouse clicked.
     *
     * @throws IOException the io exception
     */
    @FXML
    void backMouseClicked() throws IOException {
        this.scene.setRoot(this.root);
    }

    /**
     * Back mouse entered.
     */
    @FXML
    void backMouseEntered() {
        UtilsController.rescaleNode(this.scene, this.backIcon, 1.2);
    }

    /**
     * Back mouse exited.
     */
    @FXML
    void backMouseExited() {
        UtilsController.rescaleNode(this.scene, this.backIcon, 1);
        scene.setCursor(Cursor.DEFAULT);
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}