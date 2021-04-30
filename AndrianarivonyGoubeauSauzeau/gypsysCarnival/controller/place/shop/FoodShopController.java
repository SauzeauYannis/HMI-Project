package gypsysCarnival.controller.place.shop;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.controller.PlayerInfoController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.item.Item;
import gypsysCarnival.model.place.Shop;
import gypsysCarnival.view.ClickableImage;

/**
 * The type Food shop gypsysCarnival.controller.
 */
public class FoodShopController {

    /*--------------------- Private members -------------------------*/

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

    /*--------------------- Public methods -------------------------*/

    /**
     * Buy food.
     *
     * @param mouseEvent the mouse event
     */
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

    /**
     * Go carnival.
     */
    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets food shop.
     *
     * @param foodShop the food shop
     */
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

    /**
     * Sets place gypsysCarnival.controller.
     *
     * @param placeController the place gypsysCarnival.controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    /**
     * Sets player info gypsysCarnival.controller.
     *
     * @param playerInfoController the player info gypsysCarnival.controller
     */
    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
