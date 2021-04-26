package controller.place;

import controller.PlaceController;
import javafx.fxml.FXML;
import model.character.Player;
import model.command.Interpreter;

public class EndingController {

    private PlaceController placeController;
    private Player player;

    @FXML
    private void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    public void setGameController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
