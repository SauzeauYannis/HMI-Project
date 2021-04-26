package controller.place.shop;

import controller.PlaceController;
import controller.PlayerInfoController;
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
    private PlaceController placeController;
    private Player player;

    @FXML
    private ImageView copperKeyIcon;

    @FXML
    private ImageView goldKeyIcon;

    @FXML
    private Label copperKeyPrice;

    @FXML
    private Label goldKeyPrice;

    @FXML
    private Label platinumKeyPrice;

    @FXML
    public void buyKey(MouseEvent mouseEvent) {
        int oldInventorySize = this.player.getItems().size();

        if (mouseEvent.getTarget().equals(this.copperKeyIcon))
            Interpreter.interpretCommand(this.player, "take copper");
        else if (mouseEvent.getTarget().equals(this.goldKeyIcon))
            Interpreter.interpretCommand(this.player, "take gold");
        else
            Interpreter.interpretCommand(this.player, "take platinum");

        if (this.player.getItems().size() > oldInventorySize)
            this.playerInfoController.addItem(((ImageView) mouseEvent.getTarget()).getId());
    }

    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    public void setKeyShop(Shop keyShop) {
        for (Item item : keyShop.getItemList())
            switch (item.getName()) {
                case "Copper key":
                    this.copperKeyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Gold key":
                    this.goldKeyPrice.setText(item.getPrice() + " coins");
                    break;
                case "Platinum key":
                    this.platinumKeyPrice.setText(item.getPrice() + " coins");
            }
    }

    public void setGameController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
