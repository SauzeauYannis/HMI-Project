package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import modele.character.Player;

import java.io.IOException;

public class GameController {

    private CarnivalController carnivalController;
    private KeyShopController keyShopController;

    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane gameScene;

    public void setPlayer(Player player) {
        this.player = player;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/carnival.fxml"));
        try {
            AnchorPane carnivalScene = loader.load();
            this.carnivalController = loader.getController();

            this.carnivalController.setGameController(this);
            this.carnivalController.setPlayer(player);

            this.gameScene.getChildren().add(carnivalScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        carnivalController.setScene(scene);
    }

    // TODO: 10-Apr-21 A remplacer par une grosse fonction qui change la vue selon le lieu ou se trouve le joueur
    public void goToKeyShop() {
        FXMLLoader loader = new FXMLLoader(GameController.class.getResource("../vue/keyShop.fxml"));
        try {
            AnchorPane keyShopScene = loader.load();

            this.keyShopController = loader.getController();
            this.keyShopController.setPlayer(this.player);

            this.gameScene.getChildren().clear();
            this.gameScene.getChildren().add(keyShopScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
