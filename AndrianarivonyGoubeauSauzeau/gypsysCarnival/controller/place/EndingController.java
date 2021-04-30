package gypsysCarnival.controller.place;

import gypsysCarnival.controller.PlaceController;
import javafx.fxml.FXML;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;

/**
 * The type Ending gypsysCarnival.controller.
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
     * Sets place gypsysCarnival.controller.
     *
     * @param placeController the place gypsysCarnival.controller
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
