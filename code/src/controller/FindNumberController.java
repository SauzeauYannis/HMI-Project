package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import model.character.Player;
import model.command.Interpreter;
import model.place.game.FindNumber;

import java.net.URL;
import java.util.ResourceBundle;

public class FindNumberController implements Initializable {

    private FindNumber findNumber;

    private GameController gameController;
    private Player player;

    @FXML
    private TextField numberField;

    @FXML
    void goCopper() {
        Interpreter.interpretCommand(this.player, "go copper");
        this.gameController.changePlace();
    }

    @FXML
    void submitMouseClicked() {
        this.findNumber.test(this.numberField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.numberField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reset() {
        this.findNumber = (FindNumber) this.player.getPlace();
    }
}
