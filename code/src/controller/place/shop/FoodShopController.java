package controller.place.shop;

import controller.GameController;
import controller.PlayerInfoController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.item.Item;
import model.place.Shop;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodShopController implements Initializable {

    private PlayerInfoController playerInfoController;
    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView carnivalIcon;

    @FXML
    private ImageView appleCandyIcon;

    @FXML
    private ImageView cottonCandyIcon;

    @FXML
    private ImageView chocolateEclairIcon;

    @FXML
    private Label appleCandyPrice;

    @FXML
    private Label cottonCandyPrice;

    @FXML
    private Label chocolateEclairPrice;

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void buyFood(MouseEvent mouseEvent) {
        int oldInventorySize = this.player.getItems().size();

        if (mouseEvent.getTarget().equals(this.appleCandyIcon))
            Interpreter.interpretCommand(this.player, "take apple");
        else if (mouseEvent.getTarget().equals(this.cottonCandyIcon))
            Interpreter.interpretCommand(this.player, "take cotton");
        else
            Interpreter.interpretCommand(this.player, "take chocolate");

        if (this.player.getItems().size() > oldInventorySize)
            playerInfoController.addItem(((ImageView) mouseEvent.getTarget()).getId());
    }

    @FXML
    void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipBuyKey = new Tooltip("Left click to buy a food item!");
        Tooltip.install(this.appleCandyIcon, tooltipBuyKey);
        Tooltip.install(this.cottonCandyIcon, tooltipBuyKey);
        Tooltip.install(this.chocolateEclairIcon, tooltipBuyKey);
        Tooltip.install(this.carnivalIcon, new Tooltip("Go to carnival"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void generateLabel() {
        for (Item item: ((Shop) this.player.getPlace()).getItemList()) {
            switch (item.getName()) {
                case "Apple candy":
                    this.appleCandyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Cotton candy":
                    this.cottonCandyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Chocolate eclair":
                    this.chocolateEclairPrice.setText(item.getPrice() + " coins");
                    break;
            }
        }
    }
}
