package controller;

import controller.place.CarnivalController;
import controller.place.EndingController;
import controller.place.game.copper.FindNumberController;
import controller.place.game.copper.QTEController;
import controller.place.game.copper.RockPaperScissorsController;
import controller.place.game.gold.HanoiTowerController;
import controller.place.game.gold.RiddleController;
import controller.place.game.gold.TicTacToeController;
import controller.place.game.platinum.HangmanController;
import controller.place.game.platinum.KaraokeController;
import controller.place.game.platinum.QuestionsController;
import controller.place.hub.CopperHubController;
import controller.place.hub.GoldHubController;
import controller.place.hub.PlatinumHubController;
import controller.place.shop.FoodShopController;
import controller.place.shop.KeyShopController;
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
    private AnchorPane copperHubPane;
    private AnchorPane findNumberPane;
    private AnchorPane qtePane;
    private AnchorPane rockPaperScissorsPane;
    private AnchorPane goldHubPane;
    private AnchorPane hanoiTowerPane;
    private AnchorPane riddlePane;
    private AnchorPane ticTacToePane;
    private AnchorPane platinumHubPane;
    private AnchorPane hangmanHubPane;
    private AnchorPane karaokeHubPane;
    private AnchorPane questionsHubPane;
    private AnchorPane endingPane;

    private CarnivalController carnivalController;
    private KeyShopController keyShopController;
    private FoodShopController foodShopController;
    private CopperHubController copperHubController;
    private FindNumberController findNumberController;
    private QTEController qteController;
    private RockPaperScissorsController rockPaperScissorsController;
    private GoldHubController goldHubController;
    private HanoiTowerController hanoiTowerController;
    private RiddleController riddleController;
    private TicTacToeController ticTacToeController;
    private PlatinumHubController platinumHubController;
    private HangmanController hangmanController;
    private KaraokeController karaokeController;
    private QuestionsController questionsController;
    private EndingController endingController;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/place/carnival.fxml"));
            this.carnivalPane = loader.load();
            this.carnivalController = loader.getController();
            this.carnivalController.setGameController(this);
            this.carnivalController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/shop/keyShop.fxml"));
            this.keyShopPane = loader.load();
            this.keyShopController = loader.getController();
            this.keyShopController.setGameController(this);
            this.keyShopController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/shop/foodShop.fxml"));
            this.foodShopPane = loader.load();
            this.foodShopController = loader.getController();
            this.foodShopController.setGameController(this);
            this.foodShopController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/hub/copperHub.fxml"));
            this.copperHubPane = loader.load();
            this.copperHubController = loader.getController();
            this.copperHubController.setGameController(this);
            this.copperHubController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/copper/findNumber.fxml"));
            this.findNumberPane = loader.load();
            this.findNumberController = loader.getController();
            this.findNumberController.setGameController(this);
            this.findNumberController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/copper/qte.fxml"));
            this.qtePane = loader.load();
            this.qteController = loader.getController();
            this.qteController.setGameController(this);
            this.qteController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/copper/rockPaperScissors.fxml"));
            this.rockPaperScissorsPane = loader.load();
            this.rockPaperScissorsController = loader.getController();
            this.rockPaperScissorsController.setGameController(this);
            this.rockPaperScissorsController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/hub/goldHub.fxml"));
            this.goldHubPane = loader.load();
            this.goldHubController = loader.getController();
            this.goldHubController.setGameController(this);
            this.goldHubController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/gold/hanoiTower.fxml"));
            this.hanoiTowerPane = loader.load();
            this.hanoiTowerController = loader.getController();
            this.hanoiTowerController.setGameController(this);
            this.hanoiTowerController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/gold/riddle.fxml"));
            this.riddlePane = loader.load();
            this.riddleController = loader.getController();
            this.riddleController.setGameController(this);
            this.riddleController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/gold/ticTacToe.fxml"));
            this.ticTacToePane = loader.load();
            this.ticTacToeController = loader.getController();
            this.ticTacToeController.setGameController(this);
            this.ticTacToeController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/hub/platinumHub.fxml"));
            this.platinumHubPane = loader.load();
            this.platinumHubController = loader.getController();
            this.platinumHubController.setGameController(this);
            this.platinumHubController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/hangman.fxml"));
            this.hangmanHubPane = loader.load();
            this.hangmanController = loader.getController();
            this.hangmanController.setGameController(this);
            this.hangmanController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/karaoke.fxml"));
            this.karaokeHubPane = loader.load();
            this.karaokeController = loader.getController();
            this.karaokeController.setGameController(this);
            this.karaokeController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/questions.fxml"));
            this.questionsHubPane = loader.load();
            this.questionsController = loader.getController();
            this.questionsController.setGameController(this);
            this.questionsController.setPlayer(player);

            loader = new FXMLLoader(getClass().getResource("../view/place/ending.fxml"));
            this.endingPane = loader.load();
            this.endingController = loader.getController();
            this.endingController.setGameController(this);
            this.endingController.setPlayer(player);

            this.gameScene.getChildren().add(this.carnivalPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.carnivalController.setScene(scene);
        this.keyShopController.setScene(scene);
        this.foodShopController.setScene(scene);
        this.copperHubController.setScene(scene);
        this.findNumberController.setScene(scene);
        this.qteController.setScene(scene);
        this.rockPaperScissorsController.setScene(scene);
        this.goldHubController.setScene(scene);
        this.hanoiTowerController.setScene(scene);
        this.riddleController.setScene(scene);
        this.ticTacToeController.setScene(scene);
        this.platinumHubController.setScene(scene);
        this.hangmanController.setScene(scene);
        this.karaokeController.setScene(scene);
        this.questionsController.setScene(scene);
        this.endingController.setScene(scene);
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
            case "Copper hub":
                this.copperHubController.generatePadlocks();
                this.gameScene.getChildren().add(this.copperHubPane);
                break;
            case "Find Number":
                this.findNumberController.reset();
                this.gameScene.getChildren().add(this.findNumberPane);
                break;
            case "QTE":
                this.qteController.reset();
                this.gameScene.getChildren().add(this.qtePane);
                break;
            case "Rock paper scissors":
                this.rockPaperScissorsController.reset();
                this.gameScene.getChildren().add(this.rockPaperScissorsPane);
                break;
            case "Gold hub":
                this.goldHubController.generatePadlocks();
                this.gameScene.getChildren().add(this.goldHubPane);
                break;
            case "Hanoi tower":
                this.hanoiTowerController.reset();
                this.gameScene.getChildren().add(this.hanoiTowerPane);
                break;
            case "Riddle":
                this.riddleController.reset();
                this.gameScene.getChildren().add(this.riddlePane);
                break;
            case "Tic Tac Toe":
                this.gameScene.getChildren().add(this.ticTacToePane);
                break;
            case "Platinum hub":
                this.platinumHubController.generatePadlocks();
                this.gameScene.getChildren().add(this.platinumHubPane);
                break;
            case "Hangman":
                this.gameScene.getChildren().add(this.hangmanHubPane);
                break;
            case "Karaoke":
                this.gameScene.getChildren().add(this.karaokeHubPane);
                break;
            case "Questions":
                this.gameScene.getChildren().add(this.questionsHubPane);
                break;
            case "Sparkling caravan":
                this.gameScene.getChildren().add(this.endingPane);
                break;
        }
    }
}
