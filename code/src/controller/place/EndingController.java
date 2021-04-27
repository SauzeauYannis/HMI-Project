package controller.place;

import controller.PlaceController;
import javafx.fxml.FXML;
import model.character.Player;
import model.command.Interpreter;

/**
 * The type Ending controller.
 */
public class EndingController {

    /*--------------------- Private members -------------------------*/

    private PlaceController placeController;
    private Player player;

    /*--------------------- Public methods -------------------------*/

    /**
     * Go carnival.
     */
    @FXML
    private void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets place controller.
     *
     * @param placeController the place controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
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
