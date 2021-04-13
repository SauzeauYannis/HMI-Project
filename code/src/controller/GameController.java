package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.character.Player;

import java.io.IOException;

public class GameController {

    private AnchorPane carnivalPane;
    private AnchorPane keyShopPane;

    private PlayerInfoController playerInfoController;

    private CarnivalController carnivalController;
    private KeyShopController keyShopController;

    private Player player;
    private Scene scene;

    @FXML
    private AnchorPane gameScene;

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
        this.keyShopController.setPlayerInfoController(playerInfoController);
    }

    public void setPlayer(Player player) {
        this.player = player;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/carnival.fxml"));
            this.carnivalPane = loader.load();
            this.carnivalController = loader.getController();
            this.carnivalController.setGameController(this);
            this.carnivalController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/keyShop.fxml"));
            this.keyShopPane = loader.load();
            this.keyShopController = loader.getController();
            this.keyShopController.setGameController(this);
            this.keyShopController.setPlayer(this.player);

            this.changePlace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.carnivalController.setScene(scene);
    }

    public void changePlace() {
        this.gameScene.getChildren().clear();
        switch (this.player.getPlace().getName()) {
            case "Carnival":
                this.carnivalController.reset();
                this.gameScene.getChildren().add(this.carnivalPane);
                break;
            case "Key shop":
                this.keyShopController.generateLabel();
                this.gameScene.getChildren().add(this.keyShopPane);
                break;
            default:
                System.out.println("TODO: faire la vue");
        }
    }
}
