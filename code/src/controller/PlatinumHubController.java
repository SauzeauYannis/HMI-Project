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

public class PlatinumHubController implements Initializable {

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView hangmanIcon;

    @FXML
    private ImageView karaokeIcon;

    @FXML
    private ImageView questionsIcon;

    @FXML
    private ImageView padlockHangmanTowerIcon;

    @FXML
    private ImageView padlockQuestionsIcon;

    @FXML
    private ImageView padlockKaraokeIcon;

    @FXML
    void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();

        if (mouseEvent.getTarget().equals(this.hangmanIcon))
            Interpreter.interpretCommand(this.player, "go hangman");
        else if (mouseEvent.getTarget().equals(this.karaokeIcon))
            Interpreter.interpretCommand(this.player, "go karaoke");
        else
            Interpreter.interpretCommand(this.player, "go questions");

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
        Tooltip.install(this.padlockHangmanTowerIcon, tooltipPadlock);
        Tooltip.install(this.padlockKaraokeIcon, tooltipPadlock);
        Tooltip.install(this.padlockQuestionsIcon, tooltipPadlock);
        Tooltip.install(this.hangmanIcon, new Tooltip("Go to hangman game!"));
        Tooltip.install(this.karaokeIcon, new Tooltip("Go to karaoke game!"));
        Tooltip.install(this.questionsIcon, new Tooltip("Go to questions game!"));
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
                case "Hangman":
                    this.padlockHangmanTowerIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Karaoke":
                    this.padlockKaraokeIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Questions":
                    this.padlockQuestionsIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
            }
        }
    }
}
