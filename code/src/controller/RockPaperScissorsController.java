package controller;

import javafx.fxml.FXML;
import model.character.Player;
import model.command.Interpreter;

public class RockPaperScissorsController {

    private GameController gameController;
    private Player player;

    @FXML
    void goCopper() {
        Interpreter.interpretCommand(this.player, "go copper");
        this.gameController.changePlace();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
