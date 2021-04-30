package gypsysCarnival.controller.place.hub;

import gypsysCarnival.controller.PlaceController;
import gypsysCarnival.controller.PlayerInfoController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import gypsysCarnival.model.Level;
import gypsysCarnival.model.character.Player;
import gypsysCarnival.model.command.Interpreter;
import gypsysCarnival.model.place.Place;
import gypsysCarnival.model.place.exit.Exit;
import gypsysCarnival.view.ClickableImage;
import gypsysCarnival.view.CustomAlert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The Copper hub gypsysCarnival.controller.
 */
public class CopperHubController implements Initializable {

    /*--------------------- Private members -------------------------*/

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

    /*--------------------- Public methods -------------------------*/

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
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

    /**
     * Icon mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    public void iconMouseClicked(MouseEvent mouseEvent) {
        Place oldPlace = this.player.getPlace();
        CustomAlert alert;

        if (mouseEvent.getTarget().equals(this.findNumberIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(1).getPlace().getName(),
                    oldPlace.getExitList().get(1).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "gypsysCarnival/view/design/image/find_number.gif",
                    "play",
                    "return to copper hub"
            );
            Interpreter.interpretCommand(this.player, "go find");
        } else if (mouseEvent.getTarget().equals(this.qteIcon)) {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(2).getPlace().getName(),
                    oldPlace.getExitList().get(2).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "gypsysCarnival/view/design/image/qte.gif",
                    "play",
                    "return to copper hub"
            );
            Interpreter.interpretCommand(this.player, "go qte");
        } else {
            alert = new CustomAlert(Alert.AlertType.CONFIRMATION,
                    oldPlace.getExitList().get(3).getPlace().getName(),
                    oldPlace.getExitList().get(3).getPlace().getDescription(),
                    "Once in the game you have to finish it (lose or win) to get out",
                    "gypsysCarnival/view/design/image/rock_paper_scissors.gif",
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

    /**
     * Go carnival.
     */
    @FXML
    public void goCarnival() {
        Interpreter.interpretCommand(this.player, "go carnival");
        this.placeController.changePlace();
    }

    /**
     * Padlock clicked.
     *
     * @param mouseEvent the mouse event
     */
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

    /*----------------------- Setters --------------------------------*/

    /**
     * Sets copper hub exit list.
     *
     * @param copperHubExitList the copper hub exit list
     */
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

    /**
     * Sets player info gypsysCarnival.controller.
     *
     * @param playerInfoController the player info gypsysCarnival.controller
     */
    public void setPlayerInfoController(PlayerInfoController playerInfoController) {
        this.playerInfoController = playerInfoController;
    }

    /**
     * Sets place gypsysCarnival.controller.
     *
     * @param placeController the place gypsysCarnival.controller
     */
    public void setPlaceController(PlaceController placeController) {
        this.placeController = placeController;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
