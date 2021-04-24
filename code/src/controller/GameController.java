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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import model.character.Player;

import java.io.IOException;

public class GameController {

    private final Tab gameTab;
    private final Player player;

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

    public GameController(Tab gameTab, Player player)  {
        this.player = player;
        this.gameTab = gameTab;

        try {
            FXMLLoader carnivalLoader = new FXMLLoader(getClass().getResource("../view/place/carnival.fxml"));
            this.carnivalPane = carnivalLoader.load();
            this.carnivalController = carnivalLoader.getController();
            this.carnivalController.setGameController(this);
            this.carnivalController.setPlayer(player);

            FXMLLoader keyShopLoader = new FXMLLoader(getClass().getResource("../view/place/shop/keyShop.fxml"));
            this.keyShopPane = keyShopLoader.load();
            this.keyShopController = keyShopLoader.getController();
            this.keyShopController.setGameController(this);
            this.keyShopController.setPlayer(player);

            FXMLLoader foodShopLoader = new FXMLLoader(getClass().getResource("../view/place/shop/foodShop.fxml"));
            this.foodShopPane = foodShopLoader.load();
            this.foodShopController = foodShopLoader.getController();
            this.foodShopController.setGameController(this);
            this.foodShopController.setPlayer(player);

            FXMLLoader copperHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/copperHub.fxml"));
            this.copperHubPane = copperHubLoader.load();
            this.copperHubController = copperHubLoader.getController();
            this.copperHubController.setGameController(this);
            this.copperHubController.setPlayer(player);

            FXMLLoader findNumberLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/findNumber.fxml"));
            this.findNumberPane = findNumberLoader.load();
            this.findNumberController = findNumberLoader.getController();
            this.findNumberController.setGameController(this);
            this.findNumberController.setPlayer(player);

            FXMLLoader qteLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/qte.fxml"));
            this.qtePane = qteLoader.load();
            this.qteController = qteLoader.getController();
            this.qteController.setGameController(this);
            this.qteController.setPlayer(player);

            FXMLLoader rockPaperScissorsLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/rockPaperScissors.fxml"));
            this.rockPaperScissorsPane = rockPaperScissorsLoader.load();
            this.rockPaperScissorsController = rockPaperScissorsLoader.getController();
            this.rockPaperScissorsController.setGameController(this);
            this.rockPaperScissorsController.setPlayer(player);

            FXMLLoader goldHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/goldHub.fxml"));
            this.goldHubPane = goldHubLoader.load();
            this.goldHubController = goldHubLoader.getController();
            this.goldHubController.setGameController(this);
            this.goldHubController.setPlayer(player);

            FXMLLoader hanoiTowerLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/hanoiTower.fxml"));
            this.hanoiTowerPane = hanoiTowerLoader.load();
            this.hanoiTowerController = hanoiTowerLoader.getController();
            this.hanoiTowerController.setGameController(this);
            this.hanoiTowerController.setPlayer(player);

            FXMLLoader riddleLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/riddle.fxml"));
            this.riddlePane = riddleLoader.load();
            this.riddleController = riddleLoader.getController();
            this.riddleController.setGameController(this);
            this.riddleController.setPlayer(player);

            FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/ticTacToe.fxml"));
            this.ticTacToePane = ticTacToeLoader.load();
            this.ticTacToeController = ticTacToeLoader.getController();
            this.ticTacToeController.setGameController(this);
            this.ticTacToeController.setPlayer(player);

            FXMLLoader platinumHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/platinumHub.fxml"));
            this.platinumHubPane = platinumHubLoader.load();
            this.platinumHubController = platinumHubLoader.getController();
            this.platinumHubController.setGameController(this);
            this.platinumHubController.setPlayer(player);

            FXMLLoader hangmanLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/hangman.fxml"));
            this.hangmanHubPane = hangmanLoader.load();
            this.hangmanController = hangmanLoader.getController();
            this.hangmanController.setGameController(this);
            this.hangmanController.setPlayer(player);

            FXMLLoader karaokeLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/karaoke.fxml"));
            this.karaokeHubPane = karaokeLoader.load();
            this.karaokeController = karaokeLoader.getController();
            this.karaokeController.setGameController(this);
            this.karaokeController.setPlayer(player);

            FXMLLoader questionsLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/questions.fxml"));
            this.questionsHubPane = questionsLoader.load();
            this.questionsController = questionsLoader.getController();
            this.questionsController.setGameController(this);
            this.questionsController.setPlayer(player);

            FXMLLoader endingLoader = new FXMLLoader(getClass().getResource("../view/place/ending.fxml"));
            this.endingPane = endingLoader.load();
            this.endingController = endingLoader.getController();
            this.endingController.setGameController(this);
            this.endingController.setPlayer(player);

            this.changePlace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.keyShopController.setPlayerInfoController(playerInfoController);
        this.foodShopController.setPlayerInfoController(playerInfoController);
        this.copperHubController.setPlayerInfoController(playerInfoController);
        this.goldHubController.setPlayerInfoController(playerInfoController);
        this.platinumHubController.setPlayerInfoController(playerInfoController);
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
        switch (this.player.getPlace().getName()) {
            case "Carnival":
                this.carnivalController.reset();
                this.gameTab.setContent(this.carnivalPane);
                break;
            case "Key shop":
                this.keyShopController.generateLabel();
                this.gameTab.setContent(this.keyShopPane);
                break;
            case "Food shop":
                this.foodShopController.generateLabel();
                this.gameTab.setContent(this.foodShopPane);
                break;
            case "Copper hub":
                this.copperHubController.generatePadlocks();
                this.gameTab.setContent(this.copperHubPane);
                break;
            case "Find Number":
                this.findNumberController.reset();
                this.gameTab.setContent(this.findNumberPane);
                break;
            case "QTE":
                this.qteController.reset();
                this.gameTab.setContent(this.qtePane);
                break;
            case "Rock paper scissors":
                this.rockPaperScissorsController.reset();
                this.gameTab.setContent(this.rockPaperScissorsPane);
                break;
            case "Gold hub":
                this.goldHubController.generatePadlocks();
                this.gameTab.setContent(this.goldHubPane);
                break;
            case "Hanoi tower":
                this.hanoiTowerController.reset();
                this.gameTab.setContent(this.hanoiTowerPane);
                break;
            case "Riddle":
                this.riddleController.reset();
                this.gameTab.setContent(this.riddlePane);
                break;
            case "Tic Tac Toe":
                this.gameTab.setContent(this.ticTacToePane);
                break;
            case "Platinum hub":
                this.platinumHubController.generatePadlocks();
                this.gameTab.setContent(this.platinumHubPane);
                break;
            case "Hangman":
                this.hangmanController.reset();
                this.gameTab.setContent(this.hangmanHubPane);
                break;
            case "Karaoke":
                this.karaokeController.reset();
                this.gameTab.setContent(this.karaokeHubPane);
                break;
            case "Questions":
                this.questionsController.reset();
                this.gameTab.setContent(this.questionsHubPane);
                break;
            case "Sparkling caravan":
                this.gameTab.setContent(this.endingPane);
        }
    }
}
