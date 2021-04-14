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

public class CopperHubController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView findNumberIcon;

    @FXML
    private ImageView rockPaperScissorsIcon;

    @FXML
    private ImageView qteIcon;

    @FXML
    private ImageView padlockFindNumberIcon;

    @FXML
    private ImageView padlockRockPaperScissorsIcon;

    @FXML
    private ImageView padlockQteIcon;

    @FXML
    void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();

        if (mouseEvent.getTarget().equals(this.findNumberIcon))
            Interpreter.interpretCommand(this.player, "go find");
        else if (mouseEvent.getTarget().equals(this.qteIcon))
            Interpreter.interpretCommand(this.player, "go qte");
        else
            Interpreter.interpretCommand(this.player, "go rock");

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
        Tooltip.install(this.padlockFindNumberIcon, tooltipPadlock);
        Tooltip.install(this.padlockQteIcon, tooltipPadlock);
        Tooltip.install(this.padlockRockPaperScissorsIcon, tooltipPadlock);
        Tooltip.install(this.findNumberIcon, new Tooltip("Go to find number game!"));
        Tooltip.install(this.qteIcon, new Tooltip("Go to qte game!"));
        Tooltip.install(this.rockPaperScissorsIcon, new Tooltip("Go to rock paper scissors game!"));
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
                case "Find Number":
                    this.padlockFindNumberIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "QTE":
                    this.padlockQteIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Rock paper scissors":
                    this.padlockRockPaperScissorsIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
            }
        }
    }
}
