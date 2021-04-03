package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modele.character.Player;

import java.io.IOException;

/**
 * The type Help controller.
 */
public class HelpController {

    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView backIcon;

    @FXML
    private Label todo;

    /**
     * Back mouse clicked.
     *
     * @param event the event
     */
    @FXML
    void backMouseClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/start.fxml"));
        Parent root = loader.load();
        StartController startController = loader.getController();

        scene.setRoot(root);

        startController.setPlayer(this.player);
        startController.setScene(this.scene);
    }

    /**
     * Back mouse entered.
     *
     * @param event the event
     */
    @FXML
    void backMouseEntered(MouseEvent event) {
        UtilsController.rescaleNode(this.scene, this.backIcon, 1.2);
    }

    /**
     * Back mouse exited.
     *
     * @param event the event
     */
    @FXML
    void backMouseExited(MouseEvent event) {
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