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
    private AnchorPane foodShopPane;

    private CarnivalController carnivalController;
    private KeyShopController keyShopController;
    private FoodShopController foodShopController;

    private Player player;

    @FXML
    private AnchorPane gameScene;

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.keyShopController.setPlayerInfoController(playerInfoController);
        this.foodShopController.setPlayerInfoController(playerInfoController);
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
            this.keyShopController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/foodShop.fxml"));
            this.foodShopPane = loader.load();
            this.foodShopController = loader.getController();
            this.foodShopController.setGameController(this);
            this.foodShopController.setPlayer(player);

            this.changePlace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.carnivalController.setScene(scene);
        this.keyShopController.setScene(scene);
        this.foodShopController.setScene(scene);
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
            case "Food shop":
                this.foodShopController.generateLabel();
                this.gameScene.getChildren().add(this.foodShopPane);
                break;
            default:
                System.out.println("TODO: faire la vue");
        }
    }
}
