package controller;

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

public class GoldHubController implements Initializable {

    private final ButtonType playButton = new ButtonType("play", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType hubButton = new ButtonType("return to gold hub", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final Alert alertPlay = new Alert(Alert.AlertType.CONFIRMATION, "", this.playButton, this.hubButton);
    private final Stage dialogStage = (Stage) this.alertPlay.getDialogPane().getScene().getWindow();
    private final Image hanoiTowerImage = new Image("view/image/hanoi_tower.gif");
    private final Image riddleImage = new Image("view/image/riddle.gif");
    private final Image ticTacToeImage = new Image("view/image/tic_tac_toe.gif");

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

        if (mouseEvent.getTarget().equals(this.hanoiTowerIcon)) {
            this.alertPlay.setTitle("Hanoi tower");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.hanoiTowerImage);
            Interpreter.interpretCommand(this.player, "go hanoi");
        } else if (mouseEvent.getTarget().equals(this.riddleIcon)) {
            this.alertPlay.setTitle("Riddle");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.riddleImage);
            Interpreter.interpretCommand(this.player, "go riddle");
        } else {
            this.alertPlay.setTitle("Tic tac toe");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.ticTacToeImage);
            Interpreter.interpretCommand(this.player, "go tic");
        }

        if (!oldPlace.equals(this.player.getPlace())) {
            if (this.alertPlay.showAndWait().orElse(null) == this.playButton)
                this.gameController.changePlace();
            else {
                Interpreter.interpretCommand(this.player, "go gold");
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
