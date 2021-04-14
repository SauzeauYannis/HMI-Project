package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;
import model.place.exit.Exit;

import java.net.URL;
import java.util.ResourceBundle;

public class GoldHubController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView hanoiTowerIcon;

    @FXML
    private ImageView riddleIcon;

    @FXML
    private ImageView ticTacToeIcon;

    @FXML
    private ImageView padlockHanoiTowerIcon;

    @FXML
    private ImageView padlockTicTacToeIcon;

    @FXML
    private ImageView padlockRiddleIcon;

    @FXML
    void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();

        if (mouseEvent.getTarget().equals(this.hanoiTowerIcon))
            Interpreter.interpretCommand(this.player, "go hanoi");
        else if (mouseEvent.getTarget().equals(this.riddleIcon))
            Interpreter.interpretCommand(this.player, "go riddle");
        else
            Interpreter.interpretCommand(this.player, "go tic");

        if (!oldPlace.equals(this.player.getPlace()))
            this.gameController.changePlace();
    }

    @FXML
    void iconMouseEntered(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1.2);
    }

    @FXML
    void iconMouseExited(MouseEvent mouseEvent) {
        UtilsController.rescaleNode(this.scene, (ImageView) mouseEvent.getTarget(), 1);
    }

    @FXML
    void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.gameController.changePlace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipPadlock = new Tooltip("This game is lock!\nUse a copper key to unlock a game!");
        Tooltip.install(this.padlockHanoiTowerIcon, tooltipPadlock);
        Tooltip.install(this.padlockRiddleIcon, tooltipPadlock);
        Tooltip.install(this.padlockTicTacToeIcon, tooltipPadlock);
        Tooltip.install(this.hanoiTowerIcon, new Tooltip("Go to hanoi tower game!"));
        Tooltip.install(this.riddleIcon, new Tooltip("Go to riddle game!"));
        Tooltip.install(this.ticTacToeIcon, new Tooltip("Go to tic tac toe game!"));
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void generatePadlocks() {
        for (Exit exit: this.player.getPlace().getExitList()) {
            switch (exit.getPlace().getName()) {
                case "Hanoi tower":
                    this.padlockHanoiTowerIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Riddle":
                    this.padlockRiddleIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Tic Tac Toe":
                    this.padlockTicTacToeIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
            }
        }
    }
}
