package controller;

import javafx.fxml.FXML;
import model.character.Player;
import model.command.Interpreter;

public class TicTacToeController {

    private GameController gameController;
    private Player player;

    @FXML
    void goGold() {
        Interpreter.interpretCommand(this.player, "go gold");
        this.gameController.changePlace();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
