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

public class CopperHubController implements Initializable {

    private final ButtonType playButton = new ButtonType("play", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType hubButton = new ButtonType("return to copper hub", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final Alert alertPlay = new Alert(Alert.AlertType.CONFIRMATION, "", this.playButton, this.hubButton);
    private final Stage dialogStage = (Stage) this.alertPlay.getDialogPane().getScene().getWindow();
    private final Image findNumberImage = new Image("view/design/image/find_number.gif");
    private final Image qteImage = new Image("view/design/image/qte.gif");
    private final Image rockPaperScissorsImage = new Image("view/design/image/rock_paper_scissors.gif");

    private GameController gameController;
    private Player player;
    private Scene scene;

    @FXML
    private ImageView carnivalIcon;

    @FXML
    private ImageView findNumberIcon;

    @FXML
    private ImageView qteIcon;

    @FXML
    private ImageView rockPaperScissorsIcon;

    @FXML
    private ImageView padlockFindNumberIcon;

    @FXML
    private ImageView padlockQteIcon;

    @FXML
    private ImageView padlockRockPaperScissorsIcon;

    @FXML
    void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();

        if (mouseEvent.getTarget().equals(this.findNumberIcon)) {
            this.alertPlay.setTitle("Find number");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.findNumberImage);
            Interpreter.interpretCommand(this.player, "go find");
        } else if (mouseEvent.getTarget().equals(this.qteIcon)) {
            this.alertPlay.setTitle("QTE");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.qteImage);
            Interpreter.interpretCommand(this.player, "go qte");
        } else {
            this.alertPlay.setTitle("Rock paper scissors");
            this.alertPlay.setContentText("TODO: How to play");
            this.dialogStage.getIcons().clear();
            this.dialogStage.getIcons().add(this.rockPaperScissorsImage);
            Interpreter.interpretCommand(this.player, "go rock");
        }

        if (!oldPlace.equals(this.player.getPlace())) {
            if (this.alertPlay.showAndWait().orElse(null) == this.playButton)
                this.gameController.changePlace();
            else {
                Interpreter.interpretCommand(this.player, "go copper");
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
        Tooltip.install(this.padlockFindNumberIcon, tooltipPadlock);
        Tooltip.install(this.padlockQteIcon, tooltipPadlock);
        Tooltip.install(this.padlockRockPaperScissorsIcon, tooltipPadlock);
        Tooltip.install(this.findNumberIcon, new Tooltip("Go to find number game"));
        Tooltip.install(this.qteIcon, new Tooltip("Go to qte game"));
        Tooltip.install(this.rockPaperScissorsIcon, new Tooltip("Go to rock paper scissors game"));
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
