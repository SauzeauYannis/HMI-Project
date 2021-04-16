package controller.place.hub;

import controller.GameController;
import controller.UtilsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;
import model.place.exit.Exit;

import java.net.URL;
import java.util.ResourceBundle;

public class PlatinumHubController implements Initializable {

    private final ButtonType playButton = new ButtonType("play", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType hubButton = new ButtonType("return to platinum hub", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final Alert alertPlay = new Alert(Alert.AlertType.CONFIRMATION, "", this.playButton, this.hubButton);
    private final Stage dialogStage = (Stage) this.alertPlay.getDialogPane().getScene().getWindow();
    private final Image hangmanImage = new Image("view/design/image/hangman.gif");
    private final Image karaokeImage = new Image("view/design/image/karaoke.gif");
    private final Image questionsImage = new Image("view/design/image/questions.gif");

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView carnivalIcon;

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

        if (mouseEvent.getTarget().equals(this.hangmanIcon)) {
            this.alertPlay.setTitle("Hangman");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.hangmanImage);
            Interpreter.interpretCommand(this.player, "go hangman");
        } else if (mouseEvent.getTarget().equals(this.karaokeIcon)) {
            this.alertPlay.setTitle("Karaoke");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.karaokeImage);
            Interpreter.interpretCommand(this.player, "go karaoke");
        } else {
            this.alertPlay.setTitle("Questions");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.questionsImage);
            Interpreter.interpretCommand(this.player, "go questions");
        }

        if (!oldPlace.equals(this.player.getPlace())) {
            if (this.alertPlay.showAndWait().orElse(null) == this.playButton)
                this.gameController.changePlace();
            else {
                Interpreter.interpretCommand(this.player, "go platinum");
                this.alertPlay.close();
            }
        }
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
        Tooltip.install(this.hangmanIcon, new Tooltip("Go to hangman game"));
        Tooltip.install(this.karaokeIcon, new Tooltip("Go to karaoke game"));
        Tooltip.install(this.questionsIcon, new Tooltip("Go to questions game"));
        Tooltip.install(this.carnivalIcon, new Tooltip("Go to carnival"));
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
