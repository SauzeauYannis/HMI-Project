package controller.place.hub;

import controller.PlaceController;
import controller.PlayerInfoController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Level;
import model.character.Player;
import model.command.Interpreter;
import model.place.Place;
import model.place.exit.Exit;
import view.ClickableImage;
import view.CustomAlert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CopperHubController implements Initializable {

    private PlayerInfoController playerInfoController;
    private PlaceController placeController;
    private Player player;

    @FXML
    private ClickableImage findNumberIcon;

    @FXML
    private ClickableImage qteIcon;

    @FXML
    private ImageView padlockFindNumberIcon;

    @FXML
    private ImageView padlockQteIcon;

    @FXML
    private ImageView padlockRockPaperScissorsIcon;

    @FXML
    public void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();
        CustomAlert alert;

        if (mouseEvent.getTarget().equals(this.findNumberIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    "Find number",
                    "Once in the game you have to finish it (lose or win) to get out",
                    "TODO: How to play",
                    "view/design/image/find_number.gif",
                    "play",
                    "return to copper hub"
            );
            Interpreter.interpretCommand(this.player, "go find");
        } else if (mouseEvent.getTarget().equals(this.qteIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    "QTE",
                    "Once in the game you have to finish it (lose or win) to get out",
                    "TODO: How to play",
                    "view/design/image/qte.gif",
                    "play",
                    "return to copper hub"
            );
            Interpreter.interpretCommand(this.player, "go qte");
        } else {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    "Rock paper scissors",
                    "Once in the game you have to finish it (lose or win) to get out",
                    "TODO: How to play",
                    "view/design/image/rock_paper_scissors.gif",
                    "play",
                    "return to copper hub"
            );
            Interpreter.interpretCommand(this.player, "go rock");
        }

        this.placeController.changePlace();

        if (!oldPlace.equals(this.player.getPlace())) {
            if (alert.showAndWait().orElse(null) == alert.getButtonTypes().get(0))
                this.placeController.play();
            else {
                Interpreter.interpretCommand(this.player, "go copper");
                this.placeController.changePlace();
            }
        }
    }

    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    @FXML
    private void padlockClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            if (mouseEvent.getTarget().equals(this.padlockFindNumberIcon))
                this.playerInfoController.unlockGame("find", Level.COPPER);
            else if (mouseEvent.getTarget().equals(this.padlockQteIcon))
                this.playerInfoController.unlockGame("qte", Level.COPPER);
            else
                this.playerInfoController.unlockGame("rock", Level.COPPER);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip tooltipPadlock = new Tooltip("This game is lock!\nUse a copper key to unlock a game or right click on the padlock!");
        Tooltip.install(this.padlockFindNumberIcon, tooltipPadlock);
        Tooltip.install(this.padlockQteIcon, tooltipPadlock);
        Tooltip.install(this.padlockRockPaperScissorsIcon, tooltipPadlock);

        this.padlockFindNumberIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockQteIcon.setCursor(Cursor.CROSSHAIR);
        this.padlockRockPaperScissorsIcon.setCursor(Cursor.CROSSHAIR);
    }

    public void setCopperHubExitList(List<Exit> copperHubExitList) {
        for (Exit exit : copperHubExitList)
            switch (exit.getPlace().getName()) {
                case "Find number":
                    this.padlockFindNumberIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "QTE":
                    this.padlockQteIcon.visibleProperty().bind(exit.isLockProperty());
                    break;
                case "Rock paper scissors":
                    this.padlockRockPaperScissorsIcon.visibleProperty().bind(exit.isLockProperty());
            }
    }

    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    public void setGameController(PlaceController placeController) {
        this.placeController = placeController;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
