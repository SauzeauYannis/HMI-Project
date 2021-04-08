package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import modele.character.Player;

import java.io.IOException;

public class GameController {

    private CarnivalController carnivalController;
    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane gameScene;

    public void setPlayer(Player player) {
        this.player = player;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/carnival.fxml"));
        try {
            AnchorPane carnivalScene = loader.load();
            carnivalController = loader.getController();
            carnivalController.setPlayer(player);
            this.gameScene.getChildren().add(carnivalScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        carnivalController.setScene(scene);
    }
}
