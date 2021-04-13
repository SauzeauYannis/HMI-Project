package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.item.Item;
import model.place.Shop;

public class KeyShopController {

    private PlayerInfoController playerInfoController;

    private GameController gameController;

    private Player player;

    @FXML
    private ImageView copperKeyIcon;

    @FXML
    private ImageView goldKeyIcon;

    @FXML
    private ImageView platinumKeyIcon;

    @FXML
    private Label copperKeyLabel;

    @FXML
    private Label goldKeyLabel;

    @FXML
    private Label platinumKeyLabel;

    @FXML
    void buyKey(MouseEvent mouseEvent) {
        int oldInventorySize = this.player.getItems().size();

        if (mouseEvent.getTarget().equals(copperKeyIcon))
            Interpreter.interpretCommand(this.player, "take copper");
        else if (mouseEvent.getTarget().equals(goldKeyIcon))
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

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void generateLabel() {
        for (Item item: ((Shop) this.player.getPlace()).getItemList()) {
            switch (item.getName()) {
                case "Copper key":
                    copperKeyLabel.setText(item.getPrice() + " coins");
                    break;
                case "Gold key":
                    goldKeyLabel.setText(item.getPrice() + " coins");
                    break;
                case "Platinum key":
                    platinumKeyLabel.setText(item.getPrice() + " coins");
                    break;
            }
        }
    }
}
