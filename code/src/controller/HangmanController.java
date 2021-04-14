package controller;

import javafx.fxml.FXML;
import model.character.Player;
import model.command.Interpreter;

public class HangmanController {

    private GameController gameController;
    private Player player;

    @FXML
    void goPlatinum() {
        Interpreter.interpretCommand(this.player, "go platinum");
        this.gameController.changePlace();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
