package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import model.character.Player;

import java.io.IOException;

/**
 * The type Help controller.
 */
public class HelpController {

    private Player player;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();

        scene.setRoot(root);

        mainController.setPlayer(this.player);
        mainController.setScene(this.scene);
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

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets scene.
     *
     * @param scene the scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}