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

public class KeyShopController implements Initializable {

    private PlayerInfoController playerInfoController;
    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView carnivalIcon;

    @FXML
    private ImageView copperKeyIcon;

    @FXML
    private ImageView goldKeyIcon;

    @FXML
    private ImageView platinumKeyIcon;

    @FXML
    private Label copperKeyPrice;

    @FXML
    private Label goldKeyPrice;

    @FXML
    private Label platinumKeyPrice;

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void buyKey(MouseEvent mouseEvent) {
        int oldInventorySize = this.player.getItems().size();

        if (mouseEvent.getTarget().equals(this.copperKeyIcon))
            Interpreter.interpretCommand(this.player, "take copper");
        else if (mouseEvent.getTarget().equals(this.goldKeyIcon))
            Interpreter.interpretCommand(this.player, "take gold");
        else
            Interpreter.interpretCommand(this.player, "take platinum");

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
        Tooltip tooltipBuyKey = new Tooltip("Left click to buy a key!");
        Tooltip.install(this.copperKeyIcon, tooltipBuyKey);
        Tooltip.install(this.goldKeyIcon, tooltipBuyKey);
        Tooltip.install(this.platinumKeyIcon, tooltipBuyKey);
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
                case "Copper key":
                    this.copperKeyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Gold key":
                    this.goldKeyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Platinum key":
                    this.platinumKeyPrice.setText(item.getPrice() + " coins");
                    break;
            }
        }
    }
}
