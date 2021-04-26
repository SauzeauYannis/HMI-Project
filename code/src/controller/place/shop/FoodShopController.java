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
import view.ClickableImage;

public class FoodShopController {

    private PlayerInfoController playerInfoController;
    private PlaceController placeController;
    private Player player;

    @FXML
    private ClickableImage appleCandyIcon;

    @FXML
    private ClickableImage cottonCandyIcon;

    @FXML
    private Label appleCandyPrice;

    @FXML
    private Label cottonCandyPrice;

    @FXML
    private Label chocolateEclairPrice;

    @FXML
    public void buyFood(MouseEvent mouseEvent) {
        int oldInventorySize = this.player.getItems().size();

        if (mouseEvent.getTarget().equals(this.appleCandyIcon))
            Interpreter.interpretCommand(this.player, "take apple");
        else if (mouseEvent.getTarget().equals(this.cottonCandyIcon))
            Interpreter.interpretCommand(this.player, "take cotton");
        else
            Interpreter.interpretCommand(this.player, "take chocolate");

        if (this.player.getItems().size() > oldInventorySize)
            this.playerInfoController.addItem(((ImageView) mouseEvent.getTarget()).getId());
    }

    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    public void setFoodShop(Shop foodShop) {
        for (Item item : foodShop.getItemList())
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
