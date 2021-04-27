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
import model.place.Shop;
import model.place.exit.Exit;
import model.place.game.copper.FindNumber;
import model.place.game.copper.QTE;
import model.place.game.copper.RockPaperScissors;
import model.place.game.gold.HanoiTower;
import model.place.game.gold.Riddle;
import model.place.game.platinum.Hangman;
import model.place.game.platinum.Questions;

import java.io.IOException;
import java.util.List;

public class PlaceController {

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

    public PlaceController(Tab gameTab, Player player)  {
        this.player = player;
        this.gameTab = gameTab;

        try {
            FXMLLoader carnivalLoader = new FXMLLoader(getClass().getResource("../view/place/carnival.fxml"));
            this.carnivalPane = carnivalLoader.load();
            this.carnivalController = carnivalLoader.getController();
            this.carnivalController.setPlaceController(this);
            this.carnivalController.setPlayer(this.player);

            this.generateShop(this.player);
            this.generateCopperPlaces(this.player);
            this.generateGoldPlaces(this.player);
            this.generatePlatinumPlaces(this.player);

            FXMLLoader endingLoader = new FXMLLoader(getClass().getResource("../view/place/ending.fxml"));
            this.endingPane = endingLoader.load();
            EndingController endingController = endingLoader.getController();
            endingController.setPlaceController(this);
            endingController.setPlayer(this.player);

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
        this.ticTacToeController.setScene(scene);
        this.karaokeController.setScene(scene);
    }

    public void changePlace() {
        switch (this.player.getPlace().getName()) {
            case "Carnival":
                this.carnivalController.reset();
                this.gameTab.setContent(this.carnivalPane);
                break;
            case "Key shop":
                this.gameTab.setContent(this.keyShopPane);
                break;
            case "Food shop":
                this.gameTab.setContent(this.foodShopPane);
                break;
            case "Copper hub":
                this.gameTab.setContent(this.copperHubPane);
                break;
            case "Find number":
                this.gameTab.setContent(this.findNumberPane);
                break;
            case "QTE":
                this.gameTab.setContent(this.qtePane);
                break;
            case "Rock paper scissors":
                this.gameTab.setContent(this.rockPaperScissorsPane);
                break;
            case "Gold hub":
                this.gameTab.setContent(this.goldHubPane);
                break;
            case "Hanoi tower":
                this.gameTab.setContent(this.hanoiTowerPane);
                break;
            case "Riddle":
                this.gameTab.setContent(this.riddlePane);
                break;
            case "Tic tac toe":
                this.gameTab.setContent(this.ticTacToePane);
                break;
            case "Platinum hub":
                this.gameTab.setContent(this.platinumHubPane);
                break;
            case "Hangman":
                this.gameTab.setContent(this.hangmanHubPane);
                break;
            case "Karaoke":
                this.gameTab.setContent(this.karaokeHubPane);
                break;
            case "Questions":
                this.gameTab.setContent(this.questionsHubPane);
                break;
            case "Sparkling caravan":
                this.gameTab.setContent(this.endingPane);
        }
    }

    public void play() {
        switch (this.player.getPlace().getName()) {
            case "Find number":
                this.findNumberController.reset();
                break;
            case "QTE":
                this.qteController.reset();
                break;
            case "Rock paper scissors":
                this.rockPaperScissorsController.reset();
                break;
            case "Hanoi tower":
                this.hanoiTowerController.reset();
                break;
            case "Riddle":
                this.riddleController.reset();
                break;
            case "Tic tac toe":
                break;
            case "Hangman":
                this.hangmanController.reset();
                break;
            case "Karaoke":
                this.karaokeController.reset();
                break;
            case "Questions":
                this.questionsController.reset();
                break;
            default:
                System.err.println("Error: Player is not in a game");
        }
    }

    private void generateShop(Player player) throws IOException {
        FXMLLoader keyShopLoader = new FXMLLoader(getClass().getResource("../view/place/shop/keyShop.fxml"));
        this.keyShopPane = keyShopLoader.load();
        this.keyShopController = keyShopLoader.getController();
        this.keyShopController.setKeyShop((Shop) player.getPlace().getExitList().get(3).getPlace());
        this.keyShopController.setPlaceController(this);
        this.keyShopController.setPlayer(player);

        FXMLLoader foodShopLoader = new FXMLLoader(getClass().getResource("../view/place/shop/foodShop.fxml"));
        this.foodShopPane = foodShopLoader.load();
        this.foodShopController = foodShopLoader.getController();
        this.foodShopController.setFoodShop((Shop) player.getPlace().getExitList().get(4).getPlace());
        this.foodShopController.setPlaceController(this);
        this.foodShopController.setPlayer(player);
    }

    private void generateCopperPlaces(Player player) throws IOException {
        List<Exit> copperHubExitList = player.getPlace().getExitList().get(0).getPlace().getExitList();

        FXMLLoader copperHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/copperHub.fxml"));
        this.copperHubPane = copperHubLoader.load();
        this.copperHubController = copperHubLoader.getController();
        this.copperHubController.setCopperHubExitList(copperHubExitList);
        this.copperHubController.setPlaceController(this);
        this.copperHubController.setPlayer(player);

        FXMLLoader findNumberLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/findNumber.fxml"));
        this.findNumberPane = findNumberLoader.load();
        this.findNumberController = findNumberLoader.getController();
        this.findNumberController.setFindNumber((FindNumber) copperHubExitList.get(1).getPlace());
        this.findNumberController.setPlaceController(this);
        this.findNumberController.setPlayer(player);

        FXMLLoader qteLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/qte.fxml"));
        this.qtePane = qteLoader.load();
        this.qteController = qteLoader.getController();
        this.qteController.setQte((QTE) copperHubExitList.get(2).getPlace());
        this.qteController.setPlaceController(this);
        this.qteController.setPlayer(player);

        FXMLLoader rockPaperScissorsLoader = new FXMLLoader(getClass().getResource("../view/place/game/copper/rockPaperScissors.fxml"));
        this.rockPaperScissorsPane = rockPaperScissorsLoader.load();
        this.rockPaperScissorsController = rockPaperScissorsLoader.getController();
        this.rockPaperScissorsController.setPlayer(player);
        this.rockPaperScissorsController.setRockPaperScissors((RockPaperScissors) copperHubExitList.get(3).getPlace());
        this.rockPaperScissorsController.setPlaceController(this);
    }

    private void generateGoldPlaces(Player player) throws IOException {
        List<Exit> goldHubExitList = player.getPlace().getExitList().get(1).getPlace().getExitList();

        FXMLLoader goldHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/goldHub.fxml"));
        this.goldHubPane = goldHubLoader.load();
        this.goldHubController = goldHubLoader.getController();
        this.goldHubController.setGoldHubExitList(goldHubExitList);
        this.goldHubController.setPlaceController(this);
        this.goldHubController.setPlayer(player);

        FXMLLoader hanoiTowerLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/hanoiTower.fxml"));
        this.hanoiTowerPane = hanoiTowerLoader.load();
        this.hanoiTowerController = hanoiTowerLoader.getController();
        this.hanoiTowerController.setHanoiTower((HanoiTower) goldHubExitList.get(1).getPlace());
        this.hanoiTowerController.setPlaceController(this);
        this.hanoiTowerController.setPlayer(player);

        FXMLLoader riddleLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/riddle.fxml"));
        this.riddlePane = riddleLoader.load();
        this.riddleController = riddleLoader.getController();
        this.riddleController.setRiddle((Riddle) goldHubExitList.get(2).getPlace());
        this.riddleController.setPlaceController(this);
        this.riddleController.setPlayer(player);

        FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("../view/place/game/gold/ticTacToe.fxml"));
        this.ticTacToePane = ticTacToeLoader.load();
        this.ticTacToeController = ticTacToeLoader.getController();
        this.ticTacToeController.setPlaceController(this);
        this.ticTacToeController.setPlayer(player);
    }

    private void generatePlatinumPlaces(Player player) throws IOException {
        List<Exit> platinumHubExitList = player.getPlace().getExitList().get(2).getPlace().getExitList();

        FXMLLoader platinumHubLoader = new FXMLLoader(getClass().getResource("../view/place/hub/platinumHub.fxml"));
        this.platinumHubPane = platinumHubLoader.load();
        this.platinumHubController = platinumHubLoader.getController();
        this.platinumHubController.setPlatinumHubExitList(platinumHubExitList);
        this.platinumHubController.setPlaceController(this);
        this.platinumHubController.setPlayer(player);

        FXMLLoader hangmanLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/hangman.fxml"));
        this.hangmanHubPane = hangmanLoader.load();
        this.hangmanController = hangmanLoader.getController();
        this.hangmanController.setHangman((Hangman) platinumHubExitList.get(1).getPlace());
        this.hangmanController.setPlaceController(this);
        this.hangmanController.setPlayer(player);

        FXMLLoader karaokeLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/karaoke.fxml"));
        this.karaokeHubPane = karaokeLoader.load();
        this.karaokeController = karaokeLoader.getController();
        this.karaokeController.setPlaceController(this);
        this.karaokeController.setPlayer(player);

        FXMLLoader questionsLoader = new FXMLLoader(getClass().getResource("../view/place/game/platinum/questions.fxml"));
        this.questionsHubPane = questionsLoader.load();
        this.questionsController = questionsLoader.getController();
        this.questionsController.setQuestions((Questions) platinumHubExitList.get(3).getPlace());
        this.questionsController.setPlaceController(this);
        this.questionsController.setPlayer(player);
    }
}
